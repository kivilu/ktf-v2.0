package com.kivi.framework.cache;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Component;

import com.kivi.framework.cache.ehcache.factory.EhcacheFactory;
import com.kivi.framework.cache.factory.ICache;
import com.kivi.framework.cache.factory.ILoader;
import com.kivi.framework.cache.properties.KtfCacheProperties;
import com.kivi.framework.cache.redis.IRedisService;
import com.kivi.framework.cache.redis.factory.RedisFactory;
import com.kivi.framework.component.KtfKit;
import com.kivi.framework.component.SpringContextHolder;

/**
 * 缓存工具类
 */
@ConditionalOnProperty(
		prefix = KtfCacheProperties.PREFIX,
		name = "enabled",
		havingValue = "true",
		matchIfMissing = true)
@Component
@DependsOn(value = { "springContextHolder" })
public class CacheKit {

	private static ICache	cacheFactory;

	@Autowired
	CacheManager			cacheManager;

	@Autowired(required = false)
	IRedisService			iRedisService;

	@PostConstruct
	private void init() {
		CacheType cacheType = getCacheType();
		if (CacheType.EHCACHE.compareTo(cacheType) == 0) {
			EhCacheCacheManager ehCacheCacheManager = (EhCacheCacheManager) cacheManager;
			cacheFactory = new EhcacheFactory(ehCacheCacheManager.getCacheManager());
		} else if (CacheType.REDIS.compareTo(cacheType) == 0) {
			cacheFactory = new RedisFactory(iRedisService);
		}

	}

	public static CacheKit me() {
		return SpringContextHolder.getBean(CacheKit.class);
	}

	public CacheType getCacheType() {
		String cacheType = KtfKit.me().getEnvProperty("spring.cache.type");
		return CacheType.valueOf(cacheType.toUpperCase());
	}

	public EhCacheCacheManager getEhCacheManager() {
		if (CacheType.EHCACHE.compareTo(getCacheType()) == 0) {
			return (EhCacheCacheManager) cacheManager;
		}
		return null;
	}

	public RedisCacheManager getRedisCacheManager() {
		if (CacheType.REDIS.compareTo(getCacheType()) == 0) {
			return (RedisCacheManager) cacheManager;
		}
		return null;
	}

	public void put(String cacheName, Object key, Object value) {
		cacheFactory.put(cacheName, key, value);
	}

	/**
	 * /** 缓存数据，
	 * 
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public void put(String cacheName, Object key, Object value, Long expire) {
		cacheFactory.put(cacheName, key, value, expire);
	}

	public <T> T get(String cacheName, Object key) {
		return cacheFactory.get(cacheName, key);
	}

	/*
	 * @SuppressWarnings("rawtypes") public List getKeys(String cacheName) { return
	 * cacheFactory.getKeys(cacheName); }
	 */

	public <T> T remove(String cacheName, Object key) {
		T result = get(cacheName, key);
		cacheFactory.remove(cacheName, key);
		return result;
	}

	public void removeAll(String cacheName) {
		cacheFactory.removeAll(cacheName);
	}

	public <T> T get(String cacheName, Object key, ILoader iLoader) {
		return cacheFactory.get(cacheName, key, iLoader);
	}

	public <T> T get(String cacheName, Object key, Class<? extends ILoader> iLoaderClass) {
		return cacheFactory.get(cacheName, key, iLoaderClass);
	}

}
