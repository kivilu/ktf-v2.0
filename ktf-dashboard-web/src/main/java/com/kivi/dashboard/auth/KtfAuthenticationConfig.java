package com.kivi.dashboard.auth;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kivi.framework.properties.KtfDashboardProperties;

@Configuration
public class KtfAuthenticationConfig {

	@ConditionalOnProperty(
			prefix = KtfDashboardProperties.PREFIX,
			value = "login-type",
			havingValue = "USERNAME",
			matchIfMissing = false)
	@Bean
	KtfAuthentication ktfAuthentication() {
		return new KtfSimpleAuthentication();
	}

}
