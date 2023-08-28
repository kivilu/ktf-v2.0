package com.kivi.crypto.dubbo.service;

import com.kivi.framework.crypto.enums.AlgDigest;

public interface KtfDigestService {
	public static final String MOCK = "com.kivi.crypto.dubbo.mock.KtfDigestServiceMock";

	/**
	 * 摘要
	 * 
	 * @param alg
	 * @param data
	 * @return
	 */
	byte[] digest(AlgDigest alg, byte[] data);

	/**
	 * 摘要并返回Hex结果
	 * 
	 * @param alg
	 * @param data
	 * @return
	 */
	String digest2Hex(AlgDigest alg, byte[] data);
}
