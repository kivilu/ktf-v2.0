package com.kivi.framework.service;

public interface KtfTokenService {

	/**
	 * 参数随机码
	 * 
	 * @param seeds
	 * @return
	 */
	String nonce(Object... seeds);

	/**
	 * 产生token
	 * 
	 * @param seeds
	 * @return
	 */
	String token(Object... seeds);

	/**
	 * 缓存对象
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	<T> T cache(String key, T value);

	/**
	 * 缓存对象
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	<T> T cache(String key, T value, long seconds);

	/**
	 * 缓存对象
	 * 
	 * @param key
	 * @return
	 */
	<T> T cache(String key);

	/**
	 * 删除缓存
	 * 
	 * @param name
	 * @param key
	 */
	<T> T evict(String key);

	/**
	 * 缓存JWT token
	 * 
	 * @param key
	 * @param token
	 * @param jwtToken
	 */
	void cacheJwt(String key, String token, String jwtToken, long seconds);

	/**
	 * 删除缓存
	 * 
	 * @param name
	 * @param key
	 */
	void evictJwt(String key);
}
