package com.kivi.sys.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import com.kivi.framework.properties.KtfSysProperties;

/**
 * @Descriptin 实现shiro的CacheManager
 */
public class ShiroRedisCacheManager implements CacheManager {

	private final KtfSysProperties		ktfDashboardProperties;

	private final RedisTemplate<String, Object>	redisTemplate;

	public ShiroRedisCacheManager(KtfSysProperties ktfDashboardProperties,
			RedisTemplate<String, Object> redisTemplate) {
		this.ktfDashboardProperties	= ktfDashboardProperties;
		this.redisTemplate			= redisTemplate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return new ShiroCache<K, V>(ktfDashboardProperties.getShiro().getCache(), name,
				(RedisTemplate<K, V>) redisTemplate);
	}

}