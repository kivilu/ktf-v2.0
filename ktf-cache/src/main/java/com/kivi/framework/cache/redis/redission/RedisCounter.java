/**
 * 
 */
package com.kivi.framework.cache.redis.redission;

import java.math.BigDecimal;

import org.redisson.api.RAtomicDouble;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;

import lombok.extern.slf4j.Slf4j;

/**
 * Redis Counter
 * 
 * @author Lu Xueqiang
 *
 */
@Slf4j
public class RedisCounter {
    private static final String  PREFIX = "RC_";
    private final RedissonClient redissonClient;

    public RedisCounter( RedissonClient redissonClient ) {
        this.redissonClient = redissonClient;
    }

    private RAtomicLong getRAtomicLong( final String name ) {
        return redissonClient.getAtomicLong(PREFIX + name);
    }

    private RAtomicDouble getRAtomicDouble( final String name ) {
        return redissonClient.getAtomicDouble(PREFIX + name);
    }

    public long getValue( String key ) {
        RAtomicLong raLong = getRAtomicLong(key);

        long result = raLong.get();
        log.trace("RedisCounter：key={}，value={}", key, result);

        return result;
    }

    public long getSetValue( String key, long val ) {
        RAtomicLong raLong = getRAtomicLong(key);

        long result = raLong.getAndSet(val);
        return result;
    }

    public long setValue( String key, long val ) {
        RAtomicLong raLong = getRAtomicLong(key);
        raLong.set(val);
        return val;
    }

    public long incr( String key ) {
        RAtomicLong raLong = getRAtomicLong(key);

        long result = raLong.incrementAndGet();
        return result;
    }

    public long decr( String key ) {
        RAtomicLong raLong = getRAtomicLong(key);
        long result = raLong.decrementAndGet();
        return result;
    }

    public long incrBy( String key, long val ) {
        RAtomicLong raLong = getRAtomicLong(key);
        long result = raLong.addAndGet(val);
        return result;
    }

    public long decrBy( String key, long val ) {
        RAtomicLong raLong = getRAtomicLong(key);
        return raLong.addAndGet(0 - val);
    }

    public BigDecimal incrDecimal( String key ) {
        RAtomicDouble raDecimal = getRAtomicDouble(key);

        double result = raDecimal.incrementAndGet();
        return BigDecimal.valueOf(result);
    }

    public BigDecimal decrDecimal( String key ) {
        RAtomicDouble raDecimal = getRAtomicDouble(key);

        double result = raDecimal.decrementAndGet();
        return BigDecimal.valueOf(result);
    }

    public BigDecimal incrDecimal( String key, BigDecimal val ) {
        RAtomicDouble raDecimal = getRAtomicDouble(key);

        double result = raDecimal.addAndGet(val.doubleValue());
        return BigDecimal.valueOf(result);
    }

    public BigDecimal decrDecimal( String key, BigDecimal val ) {
        RAtomicDouble raDecimal = getRAtomicDouble(key);

        double result = raDecimal.addAndGet(val.negate().doubleValue());
        return BigDecimal.valueOf(result);
    }

}
