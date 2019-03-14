package com.kivi.framework.cache.factory;

import java.util.Set;

/**
 * 通用缓存接口
 */
public interface ICache {

    /**
     * 缓存数据，默认超时：3600000毫秒
     * 
     * @param cacheName
     * @param key
     * @param value
     */
    void put( String cacheName, Object key, Object value );

    /**
     * /** 缓存数据，
     * 
     * @param cacheName
     * @param key
     * @param value
     */
    void put( String cacheName, Object key, Object value, Long expire );

    <T> T get( String cacheName, Object key );

    Set<String> getKeys( String cacheName );

    void remove( String cacheName, Object key );

    void removeAll( String cacheName );

    <T> T get( String cacheName, Object key, ILoader iLoader );

    <T> T get( String cacheName, Object key, Class<? extends ILoader> iLoaderClass );

}
