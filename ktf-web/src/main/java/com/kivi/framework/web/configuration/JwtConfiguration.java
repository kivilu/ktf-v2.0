package com.kivi.framework.web.configuration;

import org.springframework.context.annotation.Bean;

import com.kivi.framework.web.intercepter.JwtAuthInterceptor;

//@ConditionalOnProperty(prefix = KtfJwtProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = false)
//@Configuration
public class JwtConfiguration {

	@Bean
	public JwtAuthInterceptor jwtAuthInterceptor() {
		return new JwtAuthInterceptor();
	}

}
