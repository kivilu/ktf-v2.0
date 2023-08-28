package com.kivi.crypto.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.kivi.framework.properties.IKtfProperties;

import lombok.Data;

@Data
// @RefreshScope
@Configuration(KtfCryptoServiceProperties.BEAN_NAME)
@ConfigurationProperties(prefix = KtfCryptoServiceProperties.PREFIX)
public class KtfCryptoServiceProperties implements IKtfProperties {
	public static final String	BEAN_NAME		= "ktfCryptoServiceProperties";
	public static final String	PREFIX			= "ktf.crypto";
	public static final String	DUBBO_VERSION	= "${" + PREFIX + ".dubbo-service-version}";

	/**
	 * dubbo service 版本号
	 */
	private String				dubboServiceVersion;

	@Override
	public String prefix() {
		return PREFIX;
	}

	@Override
	public String beanName() {
		return BEAN_NAME;
	}

}
