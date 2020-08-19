package com.kivi.framework.crypto.sm4;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.kivi.framework.constant.KtfError;
import com.kivi.framework.crypto.enums.AlgSM4;
import com.kivi.framework.crypto.util.ProviderInstance;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.ByteStringKit;

public class SM4Kit {
	static {
		if (Security.getProvider("BC") == null) {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		}
	}

	public static String encryptHex(byte[] data, byte[] key) {
		return ByteStringKit.toHex(encrypt(data, key));
	}

	public static byte[] decryptHex(String encHex, byte[] key) {
		byte[] enc = ByteStringKit.fromHex(encHex);
		return decrypt(enc, key);
	}

	public static byte[] encrypt(byte[] data, byte[] key) {
		return sm4Encrypt(AlgSM4.SM4_ECB_PKCS7, data, key, null);
	}

	public static byte[] decrypt(byte[] data, byte[] key) {
		return sm4Decrypt(AlgSM4.SM4_ECB_PKCS7, data, key, null);
	}

	public static byte[] encrypt(byte[] data, byte[] key, byte[] iv) {
		return sm4Encrypt(AlgSM4.SM4_CBC_PKCS7, data, key, iv);
	}

	public static byte[] decrypt(byte[] data, byte[] key, byte[] iv) {
		return sm4Decrypt(AlgSM4.SM4_CBC_PKCS7, data, key, iv);
	}

	public static byte[] encrypt(AlgSM4 alg, byte[] data, byte[] key) {
		if (alg.isCbc())
			throw new KtfException(KtfError.E_CRYPTO, "不支持CBC模式");
		return sm4Encrypt(alg, data, key, null);
	}

	public static byte[] decrypt(AlgSM4 alg, byte[] data, byte[] key) {
		if (alg.isCbc())
			throw new KtfException(KtfError.E_CRYPTO, "不支持CBC模式");
		return sm4Decrypt(alg, data, key, null);
	}

	public static byte[] encrypt(AlgSM4 alg, byte[] data, byte[] key, byte[] ivBytes) {
		if (!alg.isCbc())
			throw new KtfException(KtfError.E_CRYPTO, "仅支持CBC模式");

		if (ivBytes == null)
			throw new KtfException(KtfError.E_CRYPTO, "CBC加密初始向量不能为空");

		return sm4Encrypt(alg, data, key, ivBytes);
	}

	public static byte[] decrypt(AlgSM4 alg, byte[] data, byte[] key, byte[] ivBytes) {
		if (!alg.isCbc())
			throw new KtfException(KtfError.E_CRYPTO, "仅支持CBC模式");

		if (ivBytes == null)
			throw new KtfException(KtfError.E_CRYPTO, "CBC解密初始向量不能为空");

		return sm4Decrypt(alg, data, key, ivBytes);
	}

	private static byte[] sm4Encrypt(AlgSM4 alg, byte[] plain, byte[] keyBytes, byte[] ivBytes) {
		if (keyBytes.length != 16)
			throw new KtfException(KtfError.E_CRYPTO, "SM4密钥长度不等于16");

		if (alg.isNoPadding() && plain.length % 16 != 0)
			throw new KtfException(KtfError.E_CRYPTO, "明文数据不是16的整数倍");

		try {
			Key		key	= new SecretKeySpec(keyBytes, "SM4");
			Cipher	out	= Cipher.getInstance(alg.getAlg(), ProviderInstance.getBCProvider());
			if (ivBytes != null) {
				if (ivBytes.length != 16)
					throw new KtfException(KtfError.E_CRYPTO, "IV初始向量长度不等16");

				IvParameterSpec iv = new IvParameterSpec(ivBytes);
				out.init(Cipher.ENCRYPT_MODE, key, iv);
			} else
				out.init(Cipher.ENCRYPT_MODE, key);
			return out.doFinal(plain);
		} catch (Exception e) {
			throw new KtfException(KtfError.E_CRYPTO, "SM4加密异常", e);
		}
	}

	private static byte[] sm4Decrypt(AlgSM4 alg, byte[] cipher, byte[] keyBytes, byte[] ivBytes) {
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
