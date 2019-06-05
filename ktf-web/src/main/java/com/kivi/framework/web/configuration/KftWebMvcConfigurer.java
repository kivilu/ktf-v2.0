package com.kivi.framework.web.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.kivi.framework.web.util.xss.XssFilter;

@Configuration
@EnableWebMvc
public class KftWebMvcConfigurer implements WebMvcConfigurer {

	@Autowired(required = false)
	FastJsonHttpMessageConverter fastJsonHttpMessageConverter;

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.extendMessageConverters(converters);
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

		if (fastJsonHttpMessageConverter != null) {
			converters.add(fastJsonHttpMessageConverter);
		} else {

			/*
			 * MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new
			 * MappingJackson2HttpMessageConverter(); //
			 * ObjectMapper是Jackson库的主要类。它提供一些功能将转换成Java对象匹配JSON结构,反之亦然 ObjectMapper
			 * objectMapper = new ObjectMapper(); SimpleModule simpleModule = new
			 * SimpleModule(); // 序列化将Long转String类型 simpleModule.addSerializer(Long.class,
			 * ToStringSerializer.instance); simpleModule.addSerializer(Long.TYPE,
			 * ToStringSerializer.instance); SimpleModule bigIntegerModule = new
			 * SimpleModule(); // 序列化将BigInteger转String类型
			 * bigIntegerModule.addSerializer(BigInteger.class,
			 * ToStringSerializer.instance); SimpleModule bigDecimalModule = new
			 * SimpleModule(); // 序列化将BigDecimal转String类型
			 * bigDecimalModule.addSerializer(BigDecimal.class,
			 * ToStringSerializer.instance); objectMapper.registerModule(simpleModule);
			 * objectMapper.registerModule(bigDecimalModule);
			 * objectMapper.registerModule(bigIntegerModule);
			 * jackson2HttpMessageConverter.setObjectMapper(objectMapper);
			 * converters.add(jackson2HttpMessageConverter);
			 */
		}

		WebMvcConfigurer.super.configureMessageConverters(converters);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

		if (!registry.hasMappingForPattern("/static/**")) {
			registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		}

		WebMvcConfigurer.super.addResourceHandlers(registry);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		WebMvcConfigurer.super.addViewControllers(registry);
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/view");
		registry.viewResolver(viewResolver);
		WebMvcConfigurer.super.configureViewResolvers(registry);
	}

	/**
	 * 添加拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(commonIntercepter).addPathPatterns("/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}

	/**
	 * xssFilter注册
	 */
	@Bean
	public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
		FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>(new XssFilter());
		registration.addUrlPatterns("/*");
		return registration;
	}

	/**
	 * RequestContextListener注册
	 */
	@Bean
	public ServletListenerRegistrationBean<RequestContextListener> requestContextListenerRegistration() {
		return new ServletListenerRegistrationBean<>(new RequestContextListener());
	}

}
