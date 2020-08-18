package com.kivi.framework.crypto.sm4;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.kivi.framework.constant.KtfError;
import com.kivi.framework.crypto.enums.AlgSM4;
import com.kivi.framework.crypto.util.ProviderInstance;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.ByteStringKit;

public class SM4Kit {

	public static String encryptEcbHex(byte[] data, byte[] key) {
		return ByteStringKit.toHex(encrypt(data, key));
	}

	public static byte[] decryptEcbHex(String encHex, byte[] key) {
		byte[] enc = ByteStringKit.fromHex(encHex);
		return decrypt(enc, key);
	}

	public static byte[] encrypt(byte[] data, byte[] key) {
		return sm4Encrypt(AlgSM4.SM4_ECB_PKCS7, data, key, null);
	}

	public static byte[] decrypt(byte[] data, byte[] key) {
		return sm4Decrypt(AlgSM4.SM4_ECB_PKCS7, data, key, null);
	}

	public static byte[] encrypt(AlgSM4 alg, byte[] data, byte[] key) {
		return sm4Encrypt(alg, data, key, null);
	}

	public static byte[] decrypt(AlgSM4 alg, byte[] data, byte[] key) {
		return sm4Decrypt(alg, data, key, null);
	}

	public static byte[] sm4Encrypt(AlgSM4 alg, byte[] plain, byte[] keyBytes, byte[] ivBytes) {
		if (keyBytes.length != 16)
			throw new KtfException(KtfError.E_CRYPTO, "SM4密钥长度不等于16");

		if (AlgSM4.SM4_ECB_ZeroPadding.ordinal() == alg.ordinal() && plain.length % 16 != 0)
			throw new KtfException(KtfError.E_CRYPTO, "明文数据不是16的整数倍");

		try {
			Key		key	= new SecretKeySpec(keyBytes, "SM4");
			Cipher	out	= Cipher.getInstance(alg.getAlg(), ProviderInstance.getBCProvider());
			if (ivBytes != null) {
				IvParameterSpec iv = new IvParameterSpec(ivBytes);
				out.init(Cipher.ENCRYPT_MODE, key, iv);
			} else
				out.init(Cipher.ENCRYPT_MODE, key);
			return out.doFinal(plain);
		} catch (Exception e) {
			throw new KtfException(KtfError.E_CRYPTO, "SM4加密异常", e);
		}
	}

	public static byte[] sm4Decrypt(AlgSM4 alg, byte[] cipher, byte[] keyBytes, byte[] ivBytes) {
		if (keyBytes.length != 16)
			throw new KtfException(KtfError.E_CRYPTO, "SM4密钥长度不等于16");

		if (cipher.length % 16 != 0)
			throw new KtfException(KtfError.E_CRYPTO, "密文数据不是16的整数倍");

		try {
			Key		key	= new SecretKeySpec(keyBytes, "SM4");
			Cipher	in	= Cipher.getInstance(alg.getAlg(), ProviderInstance.getBCProvider());
			if (ivBytes != null) {
				IvParameterSpec iv = new IvParameterSpec(ivBytes);
				in.init(Cipher.DECRYPT_MODE, key, iv);
			} else
				in.init(Cipher.DECRYPT_MODE, key);
			return in.doFinal(cipher);

		} catch (Exception e) {
			throw new KtfException(KtfError.E_CRYPTO, "SM4解密异常", e);
		}

	}

}
