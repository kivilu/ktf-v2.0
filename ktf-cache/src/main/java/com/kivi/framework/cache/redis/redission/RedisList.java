/**
 * 
 */
package com.kivi.framework.cache.redis.redission;

import java.io.Serializable;
import java.util.Iterator;

import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;

import lombok.extern.slf4j.Slf4j;

/**
 * Redis List
 * 
 * @author Lu Xueqiang
 *
 */
@Slf4j
public class RedisList {

    private static final String  PREFIX = "RLIST_";

    private final RedissonClient redissonClient;
    private final Codec          codec;

    public RedisList( RedissonClient redissonClient ) {
        this(redissonClient, null);
    }

    public RedisList( RedissonClient redissonClient, Codec codec ) {
        this.redissonClient = redissonClient;
        this.codec = codec;
    }

    private <T> RList<T> getRList( String name ) {
        RList<T> rList = codec == null ? redissonClient.getList(PREFIX + name)
                : redissonClient.getList(PREFIX + name, codec);

        return rList;
    }

    /**
     * Appends the specified element to the end of this list (optional
     * operation). Lists that support this operation may place limitations on
     * what elements may be added to this list. In particular, some lists will
     * refuse to add null elements, and others will impose restrictions on the
     * type of elements that may be added. List classes should clearly specify
     * in their documentation any restrictions on what elements may be added.
     * 
     * @param key
     * @param value
     * @return true (as specified by Collection.add)
     */
    public <T> boolean add( String name, T value ) {
        if (value == null) {
            log.warn("尝试将null值放入MAP" + name + "。不入队列，直接返回。");
            return false;
        }

        if (!(value instanceof Serializable)) {
            log.error(
                    "queue-push value must implements Serializable,now queue is unuse please check immediately!!!!!!!!!!!");
            return false;
        }
        return getRList(name).add(value);
    }

    /**
     * Returns the element at the specified position in this list.
     * 
     * @param index
     * @param key
     * @return the value to which the specified key is mapped, or null if this
     *         map contains no mapping for the key
     */
    public <T> T get( String name, int index ) {
        RList<T> rList = getRList(name);

        return rList.get(0);
    }

    /**
     * Remove the element at the specified position in this list.
     * 
     * @param index
     * @param key
     * @return
     */
    public void remove( String name, int index ) {
        getRList(name).fastRemove(index);
    }

    /**
     * Returns the number of elements in this list. If this list contains more
     * than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
     * 
     * @param name
     * @return
     */
    public int size( String name ) {
        return getRList(name).size();
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     * 
     * @param name
     * @return
     */
    public <T> Iterator<T> iterator( String name ) {
        RList<T> rList = getRList(name);

        return rList.iterator();
    }

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence (from first to last element). The returned array will be "safe"
     * in that no references to it are maintained by this list. (In other words,
     * this method must allocate a new array even if this list is backed by an
     * array). The caller is thus free to modify the returned array. This method
     * acts as bridge between array-based and collection-based APIs.
     * 
     * @param name
     * @return
     */
    @SuppressWarnings( "unchecked" )
    public <T> T[] toArray( String name ) {
        RList<T> rList = getRList(name);
        return (T[]) rList.toArray();
    }

    /**
     * Returns true if this list contains the specified element. More formally,
     * returns true if and only if this list contains at least one element e
     * such that (o==null ? e==null : o.equals(e)).
     * 
     * @param name
     * @param value
     * @return
     */
    public <T> boolean contains( String name, T value ) {
        return getRList(name).contains(value);
    }
}
