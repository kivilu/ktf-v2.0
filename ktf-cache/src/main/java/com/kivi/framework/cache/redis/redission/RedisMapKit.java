/**
 * 
 */
package com.kivi.framework.cache.redis.redission;

import java.io.Serializable;

import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;

import com.kivi.framework.util.kit.StrKit;

import lombok.extern.slf4j.Slf4j;

/**
 * Redis Map
 * 
 * @author Lu Xueqiang
 *
 */
@Slf4j
public class RedisMapKit {

	private static final String	PREFIX	= "RMap";

	private RedissonClient		redissonClient;
	private Codec				codec;

	public RedisMapKit(RedissonClient redissonClient) {
		this(redissonClient, null);
	}

	public RedisMapKit(RedissonClient redissonClient, Codec codec) {
		this.redissonClient	= redissonClient;
		this.codec			= codec;
	}

	public <K, V> RMapCache<K, V> getRMap(String name) {
		String rName = StrKit.join(".", PREFIX, name);
		return codec == null ? redissonClient.getMapCache(rName) : redissonClient.getMapCache(rName, codec);

	}

	/**
	 * Associates the specified value with the specified key. Works faster than
	 * RedisMap.put but not returning the previous value associated with key
	 * 
	 * @param key
	 * @param value
	 * @return true if key is a new key in the hash and value was set. false if key
	 *         already exists in the hash and the value was updated.
	 */
	public <K, V> boolean fastPut(String name, K key, V value) {
		if (value == null) {
			log.warn("尝试将null值放入MAP" + name + "。不入队列，直接返回。");
			return false;
		}

		if (!(value instanceof Serializable)) {
			log.error(
					"queue-push value must implements Serializable,now queue is unuse please check immediately!!!!!!!!!!!");
			return false;
		}
		return getRMap(name).fastPut(key, value);
	}

	/**
	 * Associates the specified value with the specified key in this map (optional
	 * operation). If the map previously contained a mapping for the key, the old
	 * value is replaced by the specified value. (A map m is said to contain a
	 * mapping for a key k if and only if m.containsKey(k) would return true.)
	 * 
	 * @param key
	 * @param value
	 * @return the previous value associated with key, or null if there was no
	 *         mapping for key. (A null return can also indicate that the map
	 *         previously associated null with key, if the implementation supports
	 *         null values.)
	 */
	public <K, V> V put(String name, K key, V value) {
		if (value == null) {
			log.warn("尝试将null值放入MAP" + name + "。不入队列，直接返回。");
			return null;
		}

		if (!(value instanceof Serializable)) {
			log.error(
					"queue-push value must implements Serializable,now queue is unuse please check immediately!!!!!!!!!!!");
			return null;
		}

		RMap<K, V> rmap = getRMap(name);

		return rmap.putIfAbsent(key, value);
	}

	/**
	 * Returns the value to which the specified key is mapped, or null if this map
	 * contains no mapping for the key.
	 * 
	 * More formally, if this map contains a mapping from a key k to a value v such
	 * that (key==null ? k==null : key.equals(k)), then this method returns v;
	 * otherwise it returns null. (There can be at most one such mapping.)
	 * 
	 * If this map permits null values, then a return value of null does not
	 * necessarily indicate that the map contains no mapping for the key; it's also
	 * possible that the map explicitly maps the key to null. The containsKey
	 * operation may be used to distinguish these two cases.
	 * 
	 * @param key
	 * @return the value to which the specified key is mapped, or null if this map
	 *         contains no mapping for the key
	 */
	public <K, V> V get(String name, K key) {
		RMap<K, V> rmap = getRMap(name);
		return rmap.get(key);
	}

	/**
	 * Removes the mapping for a key from this map if it is present (optional
	 * operation). More formally, if this map contains a mapping from key k to value
	 * v such that (key==null ? k==null : key.equals(k)), that mapping is removed.
	 * (The map can contain at most one such mapping.) Returns the value to which
	 * this map previously associated the key, or null if the map contained no
	 * mapping for the key.
	 * 
	 * If this map permits null values, then a return value of null does not
	 * necessarily indicate that the map contained no mapping for the key; it's also
	 * possible that the map explicitly mapped the key to null.
	 * 
	 * The map will not contain a mapping for the specified key once the call
	 * returns.
	 * 
	 * @param key
	 * @return the previous value associated with key, or null if there was no
	 *         mapping for key.
	 */
	public <K, V> V remove(String name, K key) {
		RMap<K, V> rmap = getRMap(name);

		return rmap.remove(key);
	}

	/**
	 * Removes keys from map by one operation Works faster than RMap.remove but not
	 * returning the value associated with key
	 * 
	 * @param keys
	 * @return
	 */
	public long fastRemove(String name, Object... keys) {
		return getRMap(name).fastRemove(keys);
	}

	/**
	 * Returns true if this map contains a mapping for the specified key. More
	 * formally, returns true if and only if this map contains a mapping for a key k
	 * such that (key==null ? k==null : key.equals(k)). (There can be at most one
	 * such mapping.)
	 * 
	 * @param name
	 * @param key
	 * @return
	 */
	public boolean containsKey(String name, Object key) {
		return getRMap(name).containsKey(key);
	}
}
