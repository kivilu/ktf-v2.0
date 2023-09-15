package com.kivi.framework.cache.service.impl;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.kivi.framework.cache.CacheKit;
import com.kivi.framework.constant.KtfCache;
import com.kivi.framework.service.KtfTokenService;
import com.kivi.framework.util.kit.CollectionKit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KtfTokenServiceImpl implements KtfTokenService {

    @Override
    public String nonce(Object... seeds) {
        return generate(seeds);
    }

    @Override
    public String token(Object... seeds) {
        return generate(seeds);
    }

    @Override
    public <T> T cache(String key, T value) {
        log.trace("缓存：name={}, key={}_{}，value={}", KtfCache.KTF_TOKEN, KtfCache.KTF_TOKEN, key, value);
        CacheKit.me().put(KtfCache.KTF_TOKEN, key, value);
        return value;
    }

    @Override
    public <T> T cache(String key, T value, long seconds) {
        log.trace("缓存：name={}, key={}_{}，value={}", KtfCache.KTF_TOKEN, KtfCache.KTF_TOKEN, key, value);
        CacheKit.me().put(KtfCache.KTF_TOKEN, key, value, seconds);
        return value;
    }

    @Override
    public <T> T cache(String key) {
        log.trace("获取缓存：name={}, key={}_{}", KtfCache.KTF_TOKEN, KtfCache.KTF_TOKEN, key);
        return CacheKit.me().get(KtfCache.KTF_TOKEN, key);
    }

    private String generate(Object[] seeds) {
        StringBuilder plain = new StringBuilder(512);

        List<Object> datas = CollectionKit.newArrayList(seeds);
        datas.forEach(data -> plain.append(data));

        return DigestUtils.md5Hex(plain.toString());
    }

    @Override
    public <T> T evict(String key) {
        log.trace("清除缓存：name={}, key={}_{}", KtfCache.KTF_TOKEN, KtfCache.KTF_TOKEN, key);
        return CacheKit.me().remove(KtfCache.KTF_TOKEN, key);
    }

    @Override
    public void cacheJwt(String key, String token, String jwtToken, long seconds) {
        CacheKit.me().put(KtfCache.KTF_TOKEN, key, token, seconds);
        CacheKit.me().put(KtfCache.KTF_TOKEN, "jwt-" + key, jwtToken, seconds);
    }

    @Override
    public void evictJwt(String key) {
        log.trace("清除缓存：name={}, key={}_{}", KtfCache.KTF_TOKEN, KtfCache.KTF_TOKEN, key);
        CacheKit.me().remove(KtfCache.KTF_TOKEN, key, "jwt-" + key);
    }

}
