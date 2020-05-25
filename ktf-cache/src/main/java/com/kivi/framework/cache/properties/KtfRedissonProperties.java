package com.kivi.framework.cache.properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.kivi.framework.properties.IKtfProperties;

import lombok.Data;

@Data
@Configuration( KtfRedissonProperties.BEAN_NAME )
@ConfigurationProperties( prefix = KtfRedissonProperties.PREFIX )
@ConditionalOnProperty( prefix = KtfRedisProperties.PREFIX,
						name = { "client-type" }, 
						havingValue = "redisson", 
						matchIfMissing = false )
public class KtfRedissonProperties implements IKtfProperties {
    public static final String BEAN_NAME  = "ktfRedissonProperties";
    public static final String PREFIX     = "ktf.cache.redis.redisson";
	
	/**
	 * redisson客户端类型，默认值：classpath:redisson.yaml
	 */
	private String config = "classpath:redisson.yaml";

	@Override
	public String prefix() {
		return PREFIX;
	}

	@Override
	public String beanName() {
		return BEAN_NAME;
	}

}
