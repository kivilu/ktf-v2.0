package com.kivi.crypto.service.impl;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.kivi.crypto.dto.KtfKeyDTO;
import com.kivi.crypto.service.CtxCacheService;
import com.kivi.framework.crypto.domain.KeyPairDO;

@Service
public class CtxCacheServiceImpl implements CtxCacheService {

	private final static String CTX_CACHE = "KTF.CTX";

	@Cacheable(value = { CTX_CACHE },
			key = "caches[0].name+'_'+#key")
	@Override
	public String get(String key) {
		return null;
	}

	@CachePut(value = { CTX_CACHE },
			key = "caches[0].name+'_'+#key")
	@Override
	public String put(String key, String ctx) {
		return ctx;
	}

	@Cacheable(value = { CTX_CACHE },
			key = "caches[0].name+'_'+#key")
	@Override
	public KtfKeyDTO<byte[]> getBytes(String key) {
		return null;
	}

	@Cacheable(value = { CTX_CACHE },
			key = "caches[0].name+'_'+#key")
	@Override
	public KtfKeyDTO<KeyPairDO> getKeyPair(String key) {
		return null;
	}

	@CachePut(value = { CTX_CACHE },
			key = "caches[0].name+'_'+#key")
	@Override
	public <T> KtfKeyDTO<T> put(String key, KtfKeyDTO<T> keyDTO) {
		return keyDTO;
	}

}
