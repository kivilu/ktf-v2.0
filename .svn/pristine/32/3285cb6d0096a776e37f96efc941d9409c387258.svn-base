package com.kivi.framework.web.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kivi.framework.web.intercepter.JwtAuthInterceptor;
import com.kivi.framework.web.properties.KtfJwtProperties;

@ConditionalOnProperty(prefix = KtfJwtProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = false)
@Configuration
public class JwtConfiguration implements WebMvcConfigurer {

	@Autowired(required = false)
	private KtfJwtProperties ktfJwtProperties;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration registration = registry.addInterceptor(jwtAuthInterceptor());

		// 排除的路径
		for (String excludePathPattern : ktfJwtProperties.getExcludePathPatterns()) {
			registration.excludePathPatterns(excludePathPattern);
		}

		// 拦截全部
		registration.addPathPatterns("/**");

		WebMvcConfigurer.super.addInterceptors(registry);
	}

	private JwtAuthInterceptor jwtAuthInterceptor() {
		return new JwtAuthInterceptor();
	}

}
