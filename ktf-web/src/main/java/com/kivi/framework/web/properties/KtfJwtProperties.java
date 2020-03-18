package com.kivi.framework.web.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.kivi.framework.properties.IKtfProperties;
import com.kivi.framework.util.kit.CollectionKit;

import lombok.Data;

//@ConditionalOnProperty(prefix = KtfJwtProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = false)
@Configuration(KtfJwtProperties.BEAN_NAME)
@ConfigurationProperties(prefix = KtfJwtProperties.PREFIX)
@Data
public class KtfJwtProperties implements IKtfProperties {
	public static final String	BEAN_NAME			= "ktfJwtProperties";
	public static final String	PREFIX				= "ktf.web.jwt";

	private Boolean				enabled				= false;
	private String				issuer				= "kivi";
	private String				secretSeed			= "kivi.jwt";
	private List<String>		excludePathPatterns	= CollectionKit.newArrayList("/login", "/logout", "/error");
	/**
	 * JWT token有效时间，单位：小时
	 */
	private Integer				ttl					= 24;

	@Override
	public String prefix() {
		return PREFIX;
	}

	@Override
	public String beanName() {
		return BEAN_NAME;
	}

}
