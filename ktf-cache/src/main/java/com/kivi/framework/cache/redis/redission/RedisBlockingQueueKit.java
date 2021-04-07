/**
 * 
 */
package com.kivi.framework.cache.redis.redission;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;

import com.kivi.framework.util.kit.StrKit;

import lombok.extern.slf4j.Slf4j;

/**
 * Redis BlockingQueue
 * 
 * @author Lu Xueqiang
 *
 */
@Slf4j
public class RedisBlockingQueueKit {

    private static final String PREFIX = "RBlockingQueue.";

    private final RedissonClient redissonClient;
    private final Codec codec;

    private final ConcurrentHashMap<String, RBlockingQueue<Object>> rBlockingQueueMap = new ConcurrentHashMap<>();

    public RedisBlockingQueueKit(RedissonClient redissonClient) {
        this(redissonClient, null);
    }

    public RedisBlockingQueueKit(RedissonClient redissonClient, Codec codec) {
        this.redissonClient = redissonClient;
        this.codec = codec;
    }

    /**
     * 入队列
     * 
     * @param value
     */
    public <V> void push(String name, V value) {

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
        } catch (Exception e) {
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
    @SuppressWarnings("unchecked")
    public <V> V pop(String name) {
        V resultObject = null;
        long beginInMillions = 0;
        if (log.isTraceEnabled()) {
            beginInMillions = System.currentTimeMillis();
        }

        try {
            RBlockingQueue<Object> blockingQueue = this.getBlockingQueue(name);
            resultObject = (V)blockingQueue.poll();
        } catch (Exception e) {
            log.error("数据出队列失败", e);
        }

        if (log.isTraceEnabled()) {
            long endInMillions = System.currentTimeMillis();
            log.trace("从[{}]队列poll数据用时(毫秒)：{}", name, (endInMillions - beginInMillions));
        }

        return resultObject;
    }

    /**
     * 阻塞模式出队列
     * 
     * @param timeout 毫秒
     * @return
     */
    @SuppressWarnings("unchecked")
    public <V> V pop(String name, int timeout, TimeUnit unit) {

        V resultObject = null;
        long beginInMillions = 0;
        if (log.isTraceEnabled()) {
            beginInMillions = System.currentTimeMillis();
        }

        try {
            RBlockingQueue<Object> blockingQueue = this.getBlockingQueue(name);
            resultObject = (V)blockingQueue.poll(timeout, unit);

        } catch (Exception e) {
            log.error("数据出队列失败", e);
        }

        if (log.isTraceEnabled()) {
            long endInMillions = System.currentTimeMillis();
            log.trace("向[{}]队列push数据用时(毫秒)：{}", name, (endInMillions - beginInMillions));
        }

        return resultObject;

    }

    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public <V> V peek(String name) {
        RBlockingQueue<Object> blockingQueue = this.getBlockingQueue(name);
        return (V)blockingQueue.peek();
    }

    private RBlockingQueue<Object> getBlockingQueue(String name) {
        String rName = StrKit.joinWith(".", PREFIX, name);
        RBlockingQueue<Object> result = rBlockingQueueMap.get(rName);
        if (result == null) {
            result =
                codec == null ? redissonClient.getBlockingQueue(rName) : redissonClient.getBlockingQueue(rName, codec);
            rBlockingQueueMap.put(rName, result);
        }

        return result;
    }

}
