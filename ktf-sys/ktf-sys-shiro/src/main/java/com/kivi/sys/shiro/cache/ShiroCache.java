package com.kivi.sys.shiro.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @param <K>
 * @param <V>
 * @Descriptin ShiroCache共享
 */

public class ShiroCache<K, V> implements Cache<K, V> {
	private String				cacheKey;
	private RedisTemplate<K, V>	redisTemplate;
	private long				globExpire	= 30;

	public ShiroCache(String redis_shiro_cache, String name, RedisTemplate<K, V> client) {
		this.cacheKey		= StringUtils.join(redis_shiro_cache, name, ":");
		this.redisTemplate	= client;
	}

	@Override
	public V get(K key) throws CacheException {
		redisTemplate.boundValueOps(getCacheKey(key)).expire(globExpire, TimeUnit.MINUTES);
		return redisTemplate.boundValueOps(getCacheKey(key)).get();
	}

	@Override
	public V put(K key, V value) throws CacheException {
		V old = get(key);
		redisTemplate.boundValueOps(getCacheKey(key)).set(value);
		redisTemplate.boundValueOps(getCacheKey(key)).expire(globExpire, TimeUnit.MINUTES);
		return old;
	}

	@Override
	public V remove(K key) throws CacheException {
		V old = get(key);
		redisTemplate.delete(getCacheKey(key));
		return old;
	}

	@Override
	public void clear() throws CacheException {
		redisTemplate.delete(keys());
	}

	@Override
	public int size() {
		return keys().size();
	}

	@Override
	public Set<K> keys() {
		return redisTemplate.keys(getCacheKey("*"));
	}

	@Override
	public Collection<V> values() {
		Set<K>	set		= keys();
		List<V>	list	= new ArrayList<>();
		for (K s : set) {
			list.add(get(s));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private K getCacheKey(Object k) {
		return (K) (this.cacheKey + k);
	}
}
