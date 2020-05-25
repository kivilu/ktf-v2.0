package com.kivi.framework.cache.properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.kivi.framework.cache.enums.RedisClientType;
import com.kivi.framework.properties.IKtfProperties;

import lombok.Data;

@Data
@Configuration( KtfRedisProperties.BEAN_NAME )
@ConfigurationProperties( prefix = KtfRedisProperties.PREFIX )
@ConditionalOnProperty( name = { "spring.cache.type" }, 
						havingValue = "redis", 
						matchIfMissing = false )
public class KtfRedisProperties implements IKtfProperties {
    public static final String BEAN_NAME  = "ktfRedisProperties";
    public static final String PREFIX     = "ktf.cache.redis";
	
	/**
	 * redis客户端类型，默认值：lettuce
	 */
	private RedisClientType clientType = RedisClientType.lettuce;
	
	@Override
	public String prefix() {
		return PREFIX;
	}

	@Override
	public String beanName() {
		return BEAN_NAME;
	}

}
