package com.kivi.framework.crypto.sm4;

import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.ByteStringKit;

public class SM4Kit {

	public static String encryptEcbHex(byte[] data, byte[] key) {
		SM4Ctx	ctx	= new SM4Ctx();
		SM4		sm4	= new SM4();
		try {
			sm4.sm4_setkey_enc(ctx, key);
			byte[] enc = sm4.sm4_crypt_ecb(ctx, data);
			return ByteStringKit.toHex(enc);
		} catch (Exception e) {
			throw new KtfException("SM4加密异常", e);
		}
	}

	public static byte[] decryptEcbHex(String encHex, byte[] key) {
		SM4Ctx	ctx	= new SM4Ctx();
		SM4		sm4	= new SM4();
		try {
			sm4.sm4_setkey_dec(ctx, key);
			byte[] enc = ByteStringKit.fromHex(encHex);
			return sm4.sm4_crypt_ecb(ctx, enc);
		} catch (Exception e) {
			throw new KtfException("SM4解密异常", e);
		}
	}

}
