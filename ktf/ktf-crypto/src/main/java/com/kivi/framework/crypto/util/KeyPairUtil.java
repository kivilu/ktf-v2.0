package com.kivi.framework.crypto.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.kivi.framework.crypto.domain.KeyPairResult;
import com.kivi.framework.crypto.enums.KeyType;
import com.kivi.framework.crypto.sm2.Sm2Util;
import com.kivi.framework.util.kit.ByteStringKit;

/**
 * 生成所有支持的秘钥对类型
 */
public class KeyPairUtil {
	static {
		Security.addProvider(ProviderInstance.getBCProvider());
	}

	public static String random() {
		SecureRandom	random	= new SecureRandom();
		byte[]			rnd		= random.generateSeed(16);

		return ByteStringKit.toHex(rnd);
	}

	public static final KeyPairResult gen(KeyType type, Integer keySize) throws Exception {
		switch (type) {
		case RSA:
		case DSA:
		case ECDSA: {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance(type.name, BouncyCastleProvider.PROVIDER_NAME);
			if (null != keySize) {
				kpg.initialize(keySize);
			}
			KeyPair			keyPair			= kpg.generateKeyPair();

			KeyPairResult	keyPairResult	= new KeyPairResult();
			keyPairResult.setPri(keyPair.getPrivate());
			keyPairResult.setPub(keyPair.getPublic());
			return keyPairResult;
		}
		case SM2: {
			return Sm2Util.generateKeyPair();
		}
		}
		return null;
	}

}
