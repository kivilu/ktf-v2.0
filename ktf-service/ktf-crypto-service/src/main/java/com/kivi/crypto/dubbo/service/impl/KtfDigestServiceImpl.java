package com.kivi.crypto.dubbo.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.dubbo.config.annotation.DubboService;

import com.kivi.crypto.dubbo.service.KtfDigestService;
import com.kivi.crypto.properties.KtfCryptoServiceProperties;
import com.kivi.framework.crypto.enums.AlgDigest;
import com.kivi.framework.crypto.sm3.SM3Kit;
import com.kivi.framework.util.kit.ByteStringKit;

@DubboService(version = KtfCryptoServiceProperties.DUBBO_VERSION)
public class KtfDigestServiceImpl implements KtfDigestService {

	@Override
	public byte[] digest(AlgDigest alg, byte[] data) {
		byte[] result = null;

		switch (alg) {
		case MD5:
			result = DigestUtils.md5(data);
			break;
		case SHA1:
			result = DigestUtils.sha1(data);
			break;
		case SHA256:
			result = DigestUtils.sha256(data);
			break;
		case SM3:
			result = SM3Kit.sm3(data);
			break;
		default:
			break;

		}

		return result;
	}

	@Override
	public String digest2Hex(AlgDigest alg, byte[] data) {
		return ByteStringKit.toString(digest(alg, data), ByteStringKit.HEX);
	}

}
