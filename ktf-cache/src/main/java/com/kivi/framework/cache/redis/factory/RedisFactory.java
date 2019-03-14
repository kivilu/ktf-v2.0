package com.kivi.framework.cache.redis.factory;

import java.time.Duration;
import java.util.Set;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import com.kivi.framework.cache.factory.BaseCacheFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * Redis缓存工厂
 */
@Slf4j
public class RedisFactory extends BaseCacheFactory {

	private final RedisCacheManager        cacheManager;
    private final RedisTemplate<String, ?> redisTemplate;

    public RedisFactory( RedisCacheManager cacheManager, RedisTemplate<String, ?> redisTemplate ) {
        this.cacheManager = cacheManager;
        this.redisTemplate = redisTemplate;
    }

    /*
     * private ValueOperations<String, Object> getOrAddCache() { //CacheManager
     * cacheManager = getCacheManager(); ValueOperations<String, Object> ops =
     * redisTemplate.opsForValue(); return ops; }
     */

    private Cache getOrAddCache( String cacheName, Long expire ) {
    	 Cache cache = cacheManager.getCache(cacheName);
         cacheManager.getCacheConfigurations().get(cacheName).entryTtl(Duration.ofSeconds(expire));
         return cache;
    }

    private Cache getOrAddCache( String cacheName ) {
        return this.getOrAddCache(cacheName, 3600000L);
    }

    @Override
    public void put( String cacheName, Object key, Object value ) {
        getOrAddCache(cacheName).put(key, value);
    }

    @Override
    public void put( String cacheName, Object key, Object value, Long expire ) {
        getOrAddCache(cacheName, expire).put(key, value);
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public <T> T get( String cacheName, Object key ) {
        ValueWrapper element = getOrAddCache(cacheName).get(key);
        return element != null ? (T) element.get() : null;
    }

    @Override
    public void remove( String cacheName, Object key ) {
        getOrAddCache(cacheName).evict(key);
    }

    @Override
    public void removeAll( String cacheName ) {
        getOrAddCache(cacheName).clear();
    }

    @Override
    public Set<String> getKeys( String cacheName ) {
    	return redisTemplate.keys(cacheName);
    }

}
