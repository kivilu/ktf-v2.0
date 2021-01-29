package com.kivi.framework.crypto.sm4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.crypto.sm3.SM3Kit;
import com.kivi.framework.util.kit.ByteStringKit;

class SM4Test {

	@Test
	void testSm4_crypt_ecb() {
		byte[]	hash	= SM3Kit.sm3("111");
		String	hexHash	= ByteStringKit.toHex(hash);
		System.out.println("sm3:" + hexHash);
		assertEquals("6DF72957D3B4D3C585B4F3FF3E04565FBE4750915F79954106A2B3789E676FC0", hexHash);

		String	plain	= "测试sm4";

		SM4Ctx	ctx		= new SM4Ctx();
		SM4		sm4		= new SM4();

		try {
			byte[] key = new byte[16];
			System.arraycopy(hash, 0, key, 0, 16);
			sm4.sm4_setkey_enc(ctx, key);
			byte[]	enc		= sm4.sm4_crypt_ecb(ctx, plain.getBytes(KtfConstant.DEFAULT_CHARSET));
			String	hexEnc	= ByteStringKit.toHex(enc);
			System.out.println("sm4加密:" + hexEnc);
			assertEquals("CD7EE4EDFA6A01A828E076F713EADAED", hexEnc);

			sm4.sm4_setkey_dec(ctx, key);
			byte[] data = sm4.sm4_crypt_ecb(ctx, enc);
			System.out.println("sm4解密:" + new String(data, "UTF-8"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	void testSm4_crypt_cbc() {
		fail("Not yet implemented");
	}

}
