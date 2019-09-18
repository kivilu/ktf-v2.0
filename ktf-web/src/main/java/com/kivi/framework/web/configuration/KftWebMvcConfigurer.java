package com.kivi.framework.web.configuration;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.codec.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.ResourceRegionHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.kivi.framework.spring.validator.KtfValidatorCollection;
import com.kivi.framework.web.csrf.CookieCsrfTokenRepository;
import com.kivi.framework.web.csrf.CsrfInterceptor;
import com.kivi.framework.web.intercepter.FileUploadTypeInterceptor;
import com.kivi.framework.web.properties.KtfWebProperties;
import com.kivi.framework.web.undertow.UndertowServerFactoryCustomizer;
import com.kivi.framework.web.xss.XssFilter;

import io.undertow.Undertow;

@Configuration
@EnableAspectJAutoProxy
public class KftWebMvcConfigurer extends WebMvcConfigurationSupport {

	@Autowired(required = false)
	FastJsonHttpMessageConverter		fastJsonHttpMessageConverter;

	@Autowired
	private FileUploadTypeInterceptor	fileUploadTypeInterceptor;

	@Autowired(required = false)
	private CsrfInterceptor				csrfInterceptor;

	@Override
	public Validator getValidator() {
		return new SpringValidatorAdapter(new KtfValidatorCollection());
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowCredentials(true).allowedHeaders("*").allowedOrigins("*")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").maxAge(3600);
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		// TODO Auto-generated method stub
		super.extendMessageConverters(converters);
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

		if (fastJsonHttpMessageConverter != null) {
			converters.add(fastJsonHttpMessageConverter);
		} else {
			Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
			builder.serializationInclusion(JsonInclude.Include.NON_NULL);
			ObjectMapper	objectMapper	= builder.build();
			SimpleModule	simpleModule	= new SimpleModule();
			simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
			objectMapper.registerModule(simpleModule);
			objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);// 忽略 transient 修饰的属性
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			// 设置为中国上海时区
			objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

			converters.add(new MappingJackson2HttpMessageConverter(objectMapper));

			converters.add(new StringHttpMessageConverter(Charsets.UTF_8));

			converters.add(new ByteArrayHttpMessageConverter());

			converters.add(new ResourceHttpMessageConverter());

			converters.add(new ResourceRegionHttpMessageConverter());
		}

		super.configureMessageConverters(converters);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html", "doc.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("static/**").addResourceLocations("classpath:/static/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		super.addViewControllers(registry);
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/view");
		registry.viewResolver(viewResolver);
		super.configureViewResolvers(registry);
	}

	/**
	 * 添加拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 注册自定义拦截器，添加拦截路径和排除拦截路径
		// 添加文件上传类型拦截器
		registry.addInterceptor(fileUploadTypeInterceptor).addPathPatterns("/**");
		if (csrfInterceptor != null)
			registry.addInterceptor(csrfInterceptor).addPathPatterns("/login");

		super.addInterceptors(registry);
	}

	/**
	 * Jackson全局转化long类型为String，解决jackson序列化时long类型缺失精度问题
	 * 
	 * @return Jackson2ObjectMapperBuilderCustomizer 注入的对象
	 */
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
		Jackson2ObjectMapperBuilderCustomizer cunstomizer = new Jackson2ObjectMapperBuilderCustomizer() {

			@Override
			public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {

				jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);
			}
		};

		return cunstomizer;
	}

	/**
	 * xssFilter注册
	 */
	@Bean
	public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
		FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>(new XssFilter());
		registration.addUrlPatterns("/*");
		registration.setName("xssFilter");
		registration.setOrder(Integer.MAX_VALUE);
		return registration;
	}

	/**
	 * RequestContextListener注册
	 */
	@Bean
	public ServletListenerRegistrationBean<RequestContextListener> requestContextListenerRegistration() {
		return new ServletListenerRegistrationBean<>(new RequestContextListener());
	}

	@Bean
	@ConditionalOnClass(Undertow.class)
	public UndertowServerFactoryCustomizer undertowServerFactoryCustomizer() {
		return new UndertowServerFactoryCustomizer();
	}

	@Bean
	@ConditionalOnProperty(
			prefix = KtfWebProperties.PREFIX,
			name = { "enable-csrf" },
			havingValue = "true",
			matchIfMissing = false)
	public CookieCsrfTokenRepository cookieCsrfTokenRepository() {
		return new CookieCsrfTokenRepository();
	}

	@Bean
	@ConditionalOnProperty(
			prefix = KtfWebProperties.PREFIX,
			name = { "enable-csrf" },
			havingValue = "true",
			matchIfMissing = false)
	public CsrfInterceptor CsrfInterceptor(@Autowired CookieCsrfTokenRepository cookieCsrfTokenRepository) {
		return new CsrfInterceptor(cookieCsrfTokenRepository);
	}

	@Bean
	public FileUploadTypeInterceptor fileUploadTypeInterceptor() {
		return new FileUploadTypeInterceptor();
	}

}
