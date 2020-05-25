package com.kivi.framework.cache.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.kivi.framework.component.KtfKit;
import com.kivi.framework.properties.IKtfProperties;

import lombok.Data;

@Data
@Configuration(KtfCacheProperties.BEAN_NAME)
@ConfigurationProperties(prefix = KtfCacheProperties.PREFIX)
public class KtfCacheProperties implements IKtfProperties {
	public static final String	BEAN_NAME	= "ktfCacheProperties";
	public static final String	PREFIX		= "ktf.cache";

	/**
	 * 缓存失效时间（秒），默认：86400
	 */
	private int					ttl			= 86400;

	/**
	 * 缓存key的前缀，默认值：空
	 */
	private String				prefixKey	= "";

	public String cacheType() {
		return KtfKit.me().getEnvProperty("spring.cache.type");
	}

	@Override
	public String prefix() {
		return PREFIX;
	}

	@Override
	public String beanName() {
		return BEAN_NAME;
	}

}
