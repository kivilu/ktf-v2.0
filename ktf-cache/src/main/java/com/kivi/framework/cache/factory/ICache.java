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
	void put(String cacheName, Object key, Object value);

	/**
	 * /** 缓存数据，
	 * 
	 * @param cacheName
	 * @param key
	 * @param value
	 * @param seconds
	 */
	void put(String cacheName, Object key, Object value, Long seconds);

	<T> T get(String cacheName, Object key);

	Set<String> getKeys(String cacheName);

	/**
	 * 实现命令：expire设置过期时间，单位秒
	 *
	 * @param key
	 * @param second
	 * @return
	 */
	boolean expire(String cacheName, String key, int second);

	void remove(String cacheName, Object key);

	void removeAll(String cacheName);

	<T> T get(String cacheName, Object key, ILoader iLoader);

	<T> T get(String cacheName, Object key, Class<? extends ILoader> iLoaderClass);

}
