package com.kivi.framework.cache.redis.factory;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.kivi.framework.cache.factory.BaseCacheFactory;
import com.kivi.framework.cache.redis.IRedisService;
import com.kivi.framework.util.kit.StrKit;
import com.vip.vjtools.vjkit.collection.SetUtil;

/**
 * Redis缓存工厂
 */
public class RedisFactory extends BaseCacheFactory {

	private final IRedisService iRedisService;

	public RedisFactory(IRedisService iRedisService) {
		this.iRedisService = iRedisService;
	}

	@Override
	public void put(String cacheName, Object key, Object value) {
		iRedisService.set(key(cacheName, key), value);
	}

	@Override
	public void put(String cacheName, Object key, Object value, Long expire) {
		iRedisService.set(key(cacheName, key), value, expire.intValue());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(String cacheName, Object key) {
		return (T) iRedisService.get(key(cacheName, key));
	}

	@Override
	public void remove(String cacheName, Object... keys) {
		if (keys == null || keys.length == 0)
			this.removeAll(cacheName);
		if (keys.length == 1)
			iRedisService.del(key(cacheName, keys[0]));
		else {
			Set<String> keyArray = Arrays.asList(keys).stream().map(key -> key(cacheName, key))
					.collect(Collectors.toSet());
			iRedisService.delBatch(keyArray);
		}
	}

	@Override
	public void removeAll(String cacheName) {
		iRedisService.delBatch(SetUtil.newHashSet(getKeys(cacheName)));
	}

	@Override
	public Set<String> getKeys(String cacheName) {
		return iRedisService.keys(cacheName);
	}

	@Override
	public boolean expire(String cacheName, String key, int second) {
		return iRedisService.expire(key(cacheName, key), second);
	}

	private String key(String cacheName, Object key) {
		return StrKit.join(StrKit.DOT, cacheName, key);
	}

}
