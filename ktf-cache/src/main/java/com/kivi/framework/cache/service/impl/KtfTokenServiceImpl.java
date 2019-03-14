package com.kivi.framework.cache.service.impl;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kivi.framework.cache.constant.KtfCache;
import com.kivi.framework.cache.service.KtfTokenService;
import com.kivi.framework.util.kit.CollectionKit;
import com.kivi.framework.util.kit.StrKit;

@Service
public class KtfTokenServiceImpl implements KtfTokenService {

    private static final Logger log = LoggerFactory.getLogger(KtfTokenServiceImpl.class);

    @Override
    public String nonce( Object... seeds ) {
        return generate(seeds);
    }

    @Override
    public String token( Object... seeds ) {
        return generate(seeds);
    }

    @CachePut( value = KtfCache.KTF_TOKEN, key = "caches[0].name+'_'+#key" )
    @Override
    public <T> T cache( String key, T value ) {
        log.info("缓存：name={}, key={}_{}，value={}", KtfCache.KTF_TOKEN, KtfCache.KTF_TOKEN, key, value);
        return value;
    }

    @Cacheable( value = KtfCache.KTF_TOKEN, key = "caches[0].name+'_'+#key" )
    @Override
    public <T> T cache( String key ) {
        log.info("缓存：name={}, key={}_{}不存在", KtfCache.KTF_TOKEN, KtfCache.KTF_TOKEN, key);
        return null;
    }

    private String generate( Object[] seeds ) {
        StringBuilder plain = StrKit.builder();

        List<Object> datas = CollectionKit.newArrayList(seeds);
        datas.forEach(data-> plain.append(data));

        return DigestUtils.md5Hex(plain.toString());
    }

    @CacheEvict( value = KtfCache.KTF_TOKEN, key = "caches[0].name+'_'+#key" )
    @Override
    public void evict( String key ) {
        log.info("清除缓存：name={}, key={}_{}", KtfCache.KTF_TOKEN, KtfCache.KTF_TOKEN, key);
    }

}
