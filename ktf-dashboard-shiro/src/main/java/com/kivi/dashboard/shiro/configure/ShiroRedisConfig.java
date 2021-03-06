package com.kivi.dashboard.shiro.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.kivi.dashboard.shiro.cache.ShiroRedisCacheManager;
import com.kivi.dashboard.shiro.cache.ShiroRedisSessionDAO;
import com.kivi.framework.cache.configuration.RedisConfiguration;
import com.kivi.framework.properties.KtfDashboardProperties;

/**
 * @author TuMinglong
 * @Description Apache Shiro配置类
 */

@ConditionalOnProperty(name = { "spring.cache.type" }, havingValue = "redis", matchIfMissing = false)
@Configuration
@AutoConfigureAfter({ RedisConfiguration.class, KtfDashboardProperties.class })
public class ShiroRedisConfig {

	@Autowired
	private KtfDashboardProperties			ktfDashboardProperties;

	@Autowired
	private RedisTemplate<String, Object>	redisTemplate;

	@Bean("shiroRedisCacheManager")
	ShiroRedisCacheManager shiroRedisCacheManager() {
		return new ShiroRedisCacheManager(ktfDashboardProperties, redisTemplate);
	}

	@Bean("shiroRedisSessionDAO")
	ShiroRedisSessionDAO shiroRedisSessionDAO() {
		return new ShiroRedisSessionDAO(ktfDashboardProperties, redisTemplate);
	}

}
