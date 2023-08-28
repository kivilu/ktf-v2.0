/**
 * 
 */
package com.kivi.framework.cache.redis.redission;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import com.kivi.framework.util.kit.StrKit;

/**
 * Redisson distributed reentrant Lock for Java implements java.util.concurrent.locks.Lock interface and supports TTL.
 * 
 * @author Eric
 *
 */

public class RedisLockKit {
    private static final String PREFIX = "RLock";

    private final RedissonClient redissonClient;

    private final ConcurrentHashMap<String, RLock> rLockMap = new ConcurrentHashMap<>();

    public RedisLockKit(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    private RLock getLock(String name) {
        String rName = StrKit.joinWith(".", PREFIX, name);
        RLock result = rLockMap.get(rName);
        if (result == null) {
            result = redissonClient.getLock(rName);
            rLockMap.put(rName, result);
        }

        return result;
    }

    public int getHoldCount(String name) {
        return getLock(name).getHoldCount();
    }

    /**
     * Acquires the lock. If the lock is not available then the current thread becomes disabled for thread scheduling
     * purposes and lies dormant until the lock has been acquired.
     */
    public void lock(String name) {

        getLock(name).lock();
    }

    /**
     * Acquires the lock. If the lock is not available then the current thread becomes disabled for thread scheduling
     * purposes and lies dormant until the lock has been acquired. If the lock is acquired, it is held until unlock is
     * invoked, or until leaseTime milliseconds have passed since the lock was granted - whichever comes first.
     * 
     * @param leaseTime the maximum time to hold the lock after granting it, before automatically releasing it if it
     *        hasn't already been released by invoking unlock. If leaseTime is -1, hold the lock until explicitly
     *        unlocked.
     * @param unit the time unit of the leaseTime argument
     */
    public void lock(String name, long leaseTime, TimeUnit unit) {
        getLock(name).lock(leaseTime, unit);
    }

    /**
     * Acquires the lock only if it is free at the time of invocation. Acquires the lock if it is available and returns
     * immediately with the value true. If the lock is not available then this method will return immediately with the
     * value false. A typical usage idiom for this method would be: Lock lock = ...; if (lock.tryLock()) { try { //
     * manipulate protected state } finally { lock.unlock(); } } else { // perform alternative actions } This usage
     * ensures that the lock is unlocked if it was acquired, and doesn't try to unlock if the lock was not acquired.
     * 
     * @return true if the lock was acquired and false otherwise
     */
    public boolean trylock(String name) {
        return getLock(name).tryLock();
    }

    /**
     * Acquires the lock if it is free within the given waiting time and the current thread has not been interrupted. If
     * the lock is available this method returns immediately with the value true. If the lock is not available then the
     * current thread becomes disabled for thread scheduling purposes and lies dormant until one of three things
     * happens:
     * 
     * The lock is acquired by the current thread; or Some other thread interrupts the current thread, and interruption
     * of lock acquisition is supported; or The specified waiting time elapses
     * 
     * If the lock is acquired then the value true is returned.
     * 
     * If the current thread: has its interrupted status set on entry to this method; or is interrupted while acquiring
     * the lock, and interruption of lock acquisition is supported, then InterruptedException is thrown and the current
     * thread's interrupted status is cleared. If the specified waiting time elapses then the value false is returned.
     * If the time is less than or equal to zero, the method will not wait at all.
     * 
     * 公共锁建议在：baseUtil的中redis-component.xml声明spring bean 私有锁建议在：各自应用的spring-redis-redisson.xml中声明spring bean
     * 
     * @param time the maximum time to wait for the lock
     * @param unit the time unit of the time argument
     * @return true if the lock was acquired and false if the waiting time elapsed before the lock was acquired
     * @throws InterruptedException
     */
    public boolean trylock(String name, long time, TimeUnit unit) throws InterruptedException {
        return getLock(name).tryLock(time, unit);
    }

    /**
     * Returns true as soon as the lock is acquired. If the lock is currently held by another thread in this or any
     * other process in the distributed system this method keeps trying to acquire the lock for up to waitTime before
     * giving up and returning false. If the lock is acquired, it is held until unlock is invoked, or until leaseTime
     * have passed since the lock was granted - whichever comes first.
     * 
     * @param waitTime the maximum time to aquire the lock
     * @param leaseTime lease time
     * @param unit time unit
     * @return
     * @throws InterruptedException
     */
    public boolean trylock(String name, long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {
        return getLock(name).tryLock(waitTime, leaseTime, unit);
    }

    /**
     * lease time
     */
    public void unlock(String name) {
        getLock(name).unlock();

    }
}
