package com.kivi.crypto.dubbo.mock;

import com.kivi.crypto.dubbo.service.KtfDigestService;
import com.kivi.framework.crypto.enums.AlgDigest;
import com.kivi.framework.exception.KtfMockException;

public class KtfDigestServiceMock implements KtfDigestService {

	@Override
	public byte[] digest(AlgDigest alg, byte[] data) {
		throw new KtfMockException();
	}

	@Override
	public String digest2Hex(AlgDigest alg, byte[] data) {
		throw new KtfMockException();
	}

}
