package com.kivi.crypto.service;

import com.kivi.crypto.dto.KtfKeyDTO;
import com.kivi.framework.crypto.domain.KeyPairDO;

public interface CtxCacheService {

	String get(String key);

	String put(String key, String ctx);

	KtfKeyDTO<byte[]> getBytes(String key);

	KtfKeyDTO<KeyPairDO> getKeyPair(String key);

	<T> KtfKeyDTO<T> put(String key, KtfKeyDTO<T> keyDTO);

}
