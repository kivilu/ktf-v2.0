package com.kivi.framework.cache.redis.redission;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;

import com.kivi.framework.util.kit.StrKit;

public class RedisBucketKit {
	private static final String									PREFIX		= "RBucket";
	private final RedissonClient								redissonClient;
	private final Codec											codec;
	private final ConcurrentHashMap<String, RBucket<Object>>	rBucketMap	= new ConcurrentHashMap<>();

	public RedisBucketKit(RedissonClient redissonClient) {
		this(redissonClient, null);
	}

	public RedisBucketKit(RedissonClient redissonClient, Codec codec) {
		this.redissonClient	= redissonClient;
		this.codec			= codec;
	}

	@SuppressWarnings("unchecked")
	public <V> V get(String name) {
		return (V) getRBucket(name).get();
	}

	public <V> void set(String name, V value) {
		getRBucket(name).set(value);
	}

	public <V> void set(String name, V value, long timeToLive, TimeUnit timeUnit) {
		getRBucket(name).set(value, timeToLive, timeUnit);
	}

	private RBucket<Object> getRBucket(String name) {
		String			rName	= StrKit.join(".", PREFIX, name);
		RBucket<Object>	result	= rBucketMap.get(rName);
		if (result == null) {
			result = codec == null ? redissonClient.getBucket(rName) : redissonClient.getBucket(rName, codec);
			rBucketMap.put(rName, result);
		}

		return result;
	}

}
