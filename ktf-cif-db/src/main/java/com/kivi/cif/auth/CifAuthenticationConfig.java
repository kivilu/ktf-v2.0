package com.kivi.cif.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CifAuthenticationConfig {
	@Bean
	CifAuthentication CifAuthentication() {
		return new CifSimpleAuthentication();
	}

}
