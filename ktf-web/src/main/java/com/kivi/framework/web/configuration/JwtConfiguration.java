package com.kivi.framework.web.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kivi.framework.web.intercepter.JwtAuthInterceptor;
import com.kivi.framework.web.properties.KtfJwtProperties;

@ConditionalOnProperty(prefix = KtfJwtProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = false)
@Configuration
public class JwtConfiguration {

	@Bean
	public JwtAuthInterceptor jwtAuthInterceptor() {
		return new JwtAuthInterceptor();
	}

}
