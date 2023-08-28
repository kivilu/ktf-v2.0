package com.kivi.cif.auth;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kivi.framework.properties.KtfSysProperties;

@Configuration
public class CifAuthenticationConfig {

	@ConditionalOnProperty(
			prefix = KtfSysProperties.PREFIX,
			value = "login-type",
			havingValue = "USERNAME",
			matchIfMissing = false)
	@Bean
	CifAuthentication CifAuthentication() {
		return new CifSimpleAuthentication();
	}

}
