package com.kivi.mp.configuration;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.codec.Charsets;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.ResourceRegionHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Configuration
public class KftWebMvcConfigurer extends WebMvcConfigurationSupport {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html", "doc.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("static/**").addResourceLocations("classpath:/static/");
	}

//	@Override
	/*
	 * public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/**").allowCredentials(true).allowedHeaders("*").
	 * allowedOrigins("*") .allowedMethods("GET", "POST", "PUT", "DELETE",
	 * "OPTIONS").maxAge(3600); }
	 */

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.extendMessageConverters(converters);
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
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
		super.configureMessageConverters(converters);
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

}
