/**
 * 
 */
package com.kivi.framework.cache.redis.redission;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;

import lombok.extern.slf4j.Slf4j;

/**
 * Redis BlockingQueue
 * 
 * @author Lu Xueqiang
 *
 */
@Slf4j
public class RedisBlockingQueue {

    private static final String  PREFIX = "RBQ_";

    private final RedissonClient redissonClient;
    private final Codec          codec;

    public RedisBlockingQueue( RedissonClient redissonClient ) {
        this(redissonClient, null);
    }

    public RedisBlockingQueue( RedissonClient redissonClient, Codec codec ) {
        this.redissonClient = redissonClient;
        this.codec = codec;
    }

    /**
     * 入队列
     * 
     * @param value
     */
    public <V> void push( String name, V value ) {

        if (value == null) {
            log.warn("尝试将null值放入队列{},不入队列，直接返回。", name);
            return;
        }

        if (!(value instanceof Serializable)) {
            log.error(
                    "queue-push value must implements Serializable,now queue is unuse please check immediately!!!!!!!!!!!");
            return;
        }

        long beginInMillions = 0;
        try {
            if (log.isTraceEnabled()) {
                beginInMillions = System.currentTimeMillis();
                log.trace("入队列：{}{}，value：{}", PREFIX, name, value);
            }
            RBlockingQueue<Object> blockingQueue = this.getBlockingQueue(name);
            blockingQueue.offer(value);
        }
        catch (Exception e) {
            log.error("数据入redis队列失败", e);
        }
        if (log.isTraceEnabled()) {
            long endInMillions = System.currentTimeMillis();
            log.trace("向[{}]队列push数据用时(毫秒)：{}", name, (endInMillions - beginInMillions));
        }
    }

    /**
     * 出队列
     * 
     * @return
     */
    public <V> V pop( String name ) {
        V resultObject = null;
        long beginInMillions = 0;
        if (log.isTraceEnabled()) {
            beginInMillions = System.currentTimeMillis();
        }

        try {
            RBlockingQueue<V> blockingQueue = this.getBlockingQueue(name);
            resultObject = blockingQueue.poll();
        }
        catch (Exception e) {
            log.error("数据出队列失败", e);
        }

        if (log.isTraceEnabled()) {
            long endInMillions = System.currentTimeMillis();
            log.trace("向[{}]队列push数据用时(毫秒)：{}", name, (endInMillions - beginInMillions));
        }

        return resultObject;
    }

    /**
     * 阻塞模式出队列
     * 
     * @param timeout
     *            毫秒
     * @return
     */
    public <V> V pop( String name, int timeout, TimeUnit unit ) {

        V resultObject = null;
        long beginInMillions = 0;
        if (log.isTraceEnabled()) {
            beginInMillions = System.currentTimeMillis();
        }

        try {
            RBlockingQueue<V> blockingQueue = this.getBlockingQueue(name);
            resultObject = blockingQueue.poll(timeout, unit);

        }
        catch (Exception e) {
            log.error("数据出队列失败", e);
        }

        if (log.isTraceEnabled()) {
            long endInMillions = System.currentTimeMillis();
            log.trace("向[{}]队列push数据用时(毫秒)：{}", name, (endInMillions - beginInMillions));
        }

        return resultObject;

    }

    /**
     * Retrieves, but does not remove, the head of this queue, or returns null
     * if this queue is empty.
     * 
     * @return
     */
    public <V> V peek( String name ) {
        RBlockingQueue<V> blockingQueue = this.getBlockingQueue(name);
        return blockingQueue.peek();
    }

    private <V> RBlockingQueue<V> getBlockingQueue( String name ) {

        return codec == null ? redissonClient.getBlockingQueue(PREFIX + name)
                : redissonClient.getBlockingQueue(PREFIX + name, codec);
    }

}
