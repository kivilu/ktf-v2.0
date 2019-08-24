/**
 * 
 */
package com.kivi.framework.cache.redis.redission;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RAtomicDouble;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;

import com.kivi.framework.util.kit.StrKit;

import lombok.extern.slf4j.Slf4j;

/**
 * Redis Counter
 * 
 * @author Lu Xueqiang
 *
 */
@Slf4j
public class RedisCounterKit {
	private final RedissonClient redissonClient;

	public RedisCounterKit(RedissonClient redissonClient) {
		this.redissonClient = redissonClient;
	}

	private RAtomicLong getRAtomicLong(final String name) {
		String rName = StrKit.join(".", "RAtomicLong", "Long", name);
		return redissonClient.getAtomicLong(rName);
	}

	private RAtomicDouble getRAtomicDouble(final String name) {
		String rName = StrKit.join(".", "RAtomicDouble", "Double", name);
		return redissonClient.getAtomicDouble(rName);
	}

	/**
	 * 
	 * @param timeToLive - timeout before object will be deletedtimeUnit
	 * @param timeUnit   - time unit
	 * @return Returns:true if the timeout was set and false if not
	 */
	public boolean expire(String key, long timeToLive, TimeUnit timeUnit) {
		RAtomicLong raLong = getRAtomicLong(key);
		if (raLong != null) {
			return raLong.expire(timeToLive, timeUnit);
		}

		RAtomicDouble raDecimal = getRAtomicDouble(key);
		return raDecimal.expire(timeToLive, timeUnit);
	}

	public long getValue(String key) {
		RAtomicLong	raLong	= getRAtomicLong(key);
		long		result	= raLong.get();

		return result;
	}

	public long getSetValue(String key, long val) {
		RAtomicLong	raLong	= getRAtomicLong(key);

		long		result	= raLong.getAndSet(val);
		return result;
	}

	public long setValue(String key, long val) {
		RAtomicLong raLong = getRAtomicLong(key);
		raLong.set(val);
		return val;
	}

	/**
	 * 每次调用加一
	 * 
	 * @param key
	 * @return
	 */
	public long incr(String key) {
		RAtomicLong	raLong	= getRAtomicLong(key);

		long		result	= raLong.incrementAndGet();
		return result;
	}

	public int incrCyclic(String key, int max) {
		RAtomicLong	raLong	= getRAtomicLong(key);
		Long		value	= raLong.incrementAndGet();
		int			result	= value.intValue();
		if (result == max)
			raLong.setAsync(0L);

		return result;
	}

	public long decr(String key) {
		RAtomicLong	raLong	= getRAtomicLong(key);
		long		result	= raLong.decrementAndGet();
		return result;
	}

	public long incrBy(String key, long val) {
		RAtomicLong	raLong	= getRAtomicLong(key);
		long		result	= raLong.addAndGet(val);
		return result;
	}

	public long decrBy(String key, long val) {
		RAtomicLong raLong = getRAtomicLong(key);
		return raLong.addAndGet(0 - val);
	}

	public BigDecimal incrDecimal(String key) {
		RAtomicDouble	raDecimal	= getRAtomicDouble(key);

		double			result		= raDecimal.incrementAndGet();
		return BigDecimal.valueOf(result);
	}

	public BigDecimal decrDecimal(String key) {
		RAtomicDouble	raDecimal	= getRAtomicDouble(key);

		double			result		= raDecimal.decrementAndGet();
		return BigDecimal.valueOf(result);
	}

	public BigDecimal incrDecimal(String key, BigDecimal val) {
		RAtomicDouble	raDecimal	= getRAtomicDouble(key);

		double			result		= raDecimal.addAndGet(val.doubleValue());
		return BigDecimal.valueOf(result);
	}

	public BigDecimal decrDecimal(String key, BigDecimal val) {
		RAtomicDouble	raDecimal	= getRAtomicDouble(key);

		double			result		= raDecimal.addAndGet(val.negate().doubleValue());
		return BigDecimal.valueOf(result);
	}

}
