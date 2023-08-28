package com.kivi.framework.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class KtfCorsConfiguration {

	/*
	 * springboot提供了CorsWebFilter进行跨域
	 * 
	 * @return
	 */

	@Bean
	public CorsWebFilter corsWebFilter() {
		UrlBasedCorsConfigurationSource	source				= new UrlBasedCorsConfigurationSource();
		// 配置跨域
		CorsConfiguration				corsConfiguration	= new CorsConfiguration();
		// 允许哪个请求头
		corsConfiguration.addAllowedHeader("*");
		// 允许哪个方法进行跨域, allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
		corsConfiguration.addAllowedMethod("*");
		// 允许哪个请求来源进行跨域
		// corsConfiguration.addAllowedOrigin(“*");
		corsConfiguration.addAllowedOriginPattern("*");
		// 是否允许携带cookie进行跨域corsConfiguration.setAllowCredentials(true);
		source.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsWebFilter(source);
	}
}
