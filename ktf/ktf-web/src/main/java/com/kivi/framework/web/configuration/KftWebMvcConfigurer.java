package com.kivi.framework.web.configuration;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.ResourceRegionHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.alibaba.fastjson2.support.spring.http.converter.FastJsonHttpMessageConverter;
import com.alibaba.fastjson2.support.spring.webservlet.view.FastJsonJsonView;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.kivi.framework.spring.validator.KtfValidatorCollection;
import com.kivi.framework.web.csrf.CookieCsrfTokenRepository;
import com.kivi.framework.web.csrf.CsrfInterceptor;
import com.kivi.framework.web.intercepter.FileUploadTypeInterceptor;
import com.kivi.framework.web.intercepter.JwtAuthInterceptor;
import com.kivi.framework.web.intercepter.SwaggerInterceptor;
import com.kivi.framework.web.properties.KtfJwtProperties;
import com.kivi.framework.web.xss.XssFilter;

@Configuration
@AutoConfigureAfter(KtfJwtProperties.class)
public class KftWebMvcConfigurer extends WebMvcConfigurationSupport {

	@Autowired(required = false)
	FastJsonHttpMessageConverter	fastJsonHttpMessageConverter;

//	@Autowired(required = false)
//	private CsrfInterceptor			csrfInterceptor;

	@Autowired(required = false)
	private JwtAuthInterceptor		jwtAuthInterceptor;

	@Autowired
	private SwaggerInterceptor		swaggerInterceptor;

	@Autowired
	private KtfJwtProperties		ktfJwtProperties;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html", "doc.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//		super.addResourceHandlers(registry);
	}

	@Override
	public Validator getValidator() {
		return new SpringValidatorAdapter(new KtfValidatorCollection());
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.extendMessageConverters(converters);
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

		if (fastJsonHttpMessageConverter != null) {
			converters.add(0, fastJsonHttpMessageConverter);
		} else {
			Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
			builder.serializationInclusion(JsonInclude.Include.NON_NULL);
			ObjectMapper	objectMapper	= builder.build();
			SimpleModule	simpleModule	= new SimpleModule();
			simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
			objectMapper.registerModule(simpleModule);
//			objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);// 忽略 transient 修饰的属性
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			// 设置为中国上海时区
			objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

			converters.add(new MappingJackson2HttpMessageConverter(objectMapper));

			converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));

			converters.add(new ByteArrayHttpMessageConverter());

			converters.add(new ResourceHttpMessageConverter());

			converters.add(new ResourceRegionHttpMessageConverter());
		}

		super.configureMessageConverters(converters);
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		FastJsonJsonView fastJsonJsonView = new FastJsonJsonView();
		// 自定义配置...
		// FastJsonConfig config = new FastJsonConfig();
		// config.set...
		// fastJsonJsonView.setFastJsonConfig(config);
		registry.enableContentNegotiation(fastJsonJsonView);
	}

	/**
	 * 添加拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 注册自定义拦截器，添加拦截路径和排除拦截路径

		// 添加文件上传类型拦截器
		registry.addInterceptor(fileUploadTypeInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(csrfInterceptor()).addPathPatterns("/login");

		registry.addInterceptor(swaggerInterceptor).addPathPatterns("/swagger-ui.html", "/doc.html");

		if (jwtAuthInterceptor != null) {
			registry.addInterceptor(jwtAuthInterceptor).excludePathPatterns(ktfJwtProperties.getExcludePathPatterns())
					.addPathPatterns("/**");
		}

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

	public CookieCsrfTokenRepository cookieCsrfTokenRepository() {
		return new CookieCsrfTokenRepository();
	}

	public CsrfInterceptor csrfInterceptor() {
		return new CsrfInterceptor(cookieCsrfTokenRepository());
	}

	public FileUploadTypeInterceptor fileUploadTypeInterceptor() {
		return new FileUploadTypeInterceptor();
	}

	/**
	 * 用于解决有@RequiresPermissions注解的controller，swagger就读取不到
	 * 
	 * @return
	 */
	@Bean
	public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		/**
		 * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
		 * 在@Controller注解的类的方法中加入@RequiresRole注解，会导致该方法无法映射请求，导致返回404。 加入这项配置能解决这个bug
		 */
		creator.setUsePrefix(true);
		return creator;
	}

}
