package com.kivi.framework.cache.service;

public interface KtfTokenService {

    /**
     * 参数随机码
     * 
     * @param seeds
     * @return
     */
    String nonce( Object... seeds );

    /**
     * 产生token
     * 
     * @param seeds
     * @return
     */
    String token( Object... seeds );

    /**
     * 缓存对象
     * 
     * @param key
     * @return
     */
    <T> T cache( String key, T value );

    /**
     * 缓存对象
     * 
     * @param key
     * @return
     */
    <T> T cache( String key );

    /**
     * 删除缓存
     * 
     * @param name
     * @param key
     */
    void evict( String key );
}
