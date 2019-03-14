package com.kivi.framework.cache;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.kivi.framework.cache.ehcache.factory.EhcacheFactory;
import com.kivi.framework.cache.factory.ICache;
import com.kivi.framework.cache.factory.ILoader;
import com.kivi.framework.cache.redis.factory.RedisFactory;
import com.kivi.framework.component.SpringContextHolder;

/**
 * 缓存工具类
 */
@ConditionalOnProperty( name = { "ktf.cache.enabled" }, havingValue = "true", matchIfMissing = false )
@Component
@DependsOn( "springContextHolder" )
public class CacheKit {

    private static ICache cacheFactory;

    @Autowired
    CacheProperties       cacheProperties;

    @Autowired
    CacheManager          cacheManager;
    
    @Autowired( required = false )
    RedisTemplate<String, ?> redisTemplate;

    @PostConstruct
    private void init() {
        if (CacheType.EHCACHE.compareTo(cacheProperties.getType()) == 0) {
            EhCacheCacheManager ehCacheCacheManager = (EhCacheCacheManager) cacheManager;
            cacheFactory = new EhcacheFactory(ehCacheCacheManager.getCacheManager());
        }
        else if (CacheType.REDIS.compareTo(cacheProperties.getType()) == 0) {
            cacheFactory = new RedisFactory((RedisCacheManager)cacheManager, redisTemplate);
        }

    }

    public static CacheKit me() {
        return SpringContextHolder.getBean(CacheKit.class);
    }

    public CacheType getCacheType() {
        return cacheProperties.getType();
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

    public void put( String cacheName, Object key, Object value ) {
        cacheFactory.put(cacheName, key, value);
    }

    public <T> T get( String cacheName, Object key ) {
        return cacheFactory.get(cacheName, key);
    }

    /*
     * @SuppressWarnings("rawtypes") public List getKeys(String cacheName) {
     * return cacheFactory.getKeys(cacheName); }
     */

    public void remove( String cacheName, Object key ) {
        cacheFactory.remove(cacheName, key);
    }

    public void removeAll( String cacheName ) {
        cacheFactory.removeAll(cacheName);
    }

    public <T> T get( String cacheName, Object key, ILoader iLoader ) {
        return cacheFactory.get(cacheName, key, iLoader);
    }

    public <T> T get( String cacheName, Object key, Class<? extends ILoader> iLoaderClass ) {
        return cacheFactory.get(cacheName, key, iLoaderClass);
    }

}
