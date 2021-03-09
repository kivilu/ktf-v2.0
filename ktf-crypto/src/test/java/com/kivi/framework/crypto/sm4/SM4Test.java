package com.kivi.framework.crypto.sm4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.crypto.enums.AlgSM4;
import com.kivi.framework.crypto.sm3.SM3Kit;
import com.kivi.framework.util.kit.ByteStringKit;
import com.kivi.framework.util.kit.StrKit;

class SM4Test {

	@Test
	void testSM4_ECB_PKCS7() {
		byte[]	hash	= SM3Kit.sm3("111");
		byte[]	key		= new byte[16];
		System.arraycopy(hash, 0, key, 0, 16);

		String	plain	= "测试sm4";

		byte[]	enc		= SM4Kit.encrypt(StrKit.toBytes(plain), key);
		String	hexEnc	= ByteStringKit.toHex(enc);
		System.out.println("sm4加密:" + hexEnc);
		assertEquals("CD7EE4EDFA6A01A828E076F713EADAED", hexEnc);

		byte[]	data	= SM4Kit.decrypt(enc, key);
		String	result	= new String(data, KtfConstant.DEFAULT_CHARSET);
		System.out.println("sm4解密:" + result);
		assertEquals(plain, result);
	}

	private final static String	KEY				= "B8EF610100FB45448C6D5DA0992D692F";
	private final static String	ECB_PLAINTEXT	= "207060DAD493F17B8FDB1F71B24EEAF2";
	private final static String	ECB_CIPHERTEXT	= "ABC8F2C124CE7CC69B0F03D1E6C27963";
	private final static String	CBC_IV			= "00000000000000000000000000000000";
	private final static String	CBC_PLAINTEXT	= "BA0DFF0E9C51F91E41D4CFBEF6303D89";
	private final static String	CBC_CIPHERTEXT	= "A2A83F33D4B5F67B04E0AE117E96D0D9";

	@Test
	void testSM4_ECB_NoPadding() {
		byte[] enc = SM4Kit.encrypt(AlgSM4.SM4_ECB_NoPadding, ByteStringKit.fromHex(ECB_PLAINTEXT),
				ByteStringKit.fromHex(KEY));
		System.out.println("CIPHERTEXT:" + ByteStringKit.toHex(enc));
		assertEquals(ECB_CIPHERTEXT, ByteStringKit.toHex(enc));

		byte[] data = SM4Kit.decrypt(AlgSM4.SM4_ECB_NoPadding, ByteStringKit.fromHex(ECB_CIPHERTEXT),
				ByteStringKit.fromHex(KEY));
		System.out.println("PLAINTEXT:" + ByteStringKit.toHex(data));
		assertEquals(ECB_PLAINTEXT, ByteStringKit.toHex(data));
	}

	@Test
	void testSM4_CBC_NoPadding() {
		byte[] enc = SM4Kit.encrypt(AlgSM4.SM4_CBC_NoPadding, ByteStringKit.fromHex(CBC_PLAINTEXT),
				ByteStringKit.fromHex(KEY), ByteStringKit.fromHex(CBC_IV));
		System.out.println("CIPHERTEXT:" + ByteStringKit.toHex(enc));
		assertEquals(CBC_CIPHERTEXT, ByteStringKit.toHex(enc));

		byte[] data = SM4Kit.decrypt(AlgSM4.SM4_CBC_NoPadding, ByteStringKit.fromHex(CBC_CIPHERTEXT),
				ByteStringKit.fromHex(KEY), ByteStringKit.fromHex(CBC_IV));
		System.out.println("PLAINTEXT:" + ByteStringKit.toHex(data));
		assertEquals(CBC_PLAINTEXT, ByteStringKit.toHex(data));
	}

	@Test
	void testSM4_ECB_PKCS7_1() {
		byte[]	enc		= SM4Kit.encrypt(AlgSM4.SM4_ECB_PKCS7, ByteStringKit.fromHex(ECB_PLAINTEXT),
				ByteStringKit.fromHex(KEY));
		String	hexEnc	= ByteStringKit.toHex(enc);
		System.out.println("CIPHERTEXT:" + hexEnc);

		byte[]	data	= SM4Kit.decrypt(AlgSM4.SM4_ECB_PKCS7, enc, ByteStringKit.fromHex(KEY));
		String	result	= ByteStringKit.toHex(data);
		System.out.println("PLAINTEXT:" + result);
		assertEquals(ECB_PLAINTEXT, result);
	}

	@Test
	void testSM4_SM4_CBC_PKCS7() {
		byte[]	enc		= SM4Kit.encrypt(AlgSM4.SM4_CBC_PKCS7, ByteStringKit.fromHex(ECB_PLAINTEXT),
				ByteStringKit.fromHex(KEY), ByteStringKit.fromHex(CBC_IV));
		String	hexEnc	= ByteStringKit.toHex(enc);
		System.out.println("CIPHERTEXT:" + hexEnc);

		byte[]	data	= SM4Kit.decrypt(AlgSM4.SM4_CBC_PKCS7, enc, ByteStringKit.fromHex(KEY),
				ByteStringKit.fromHex(CBC_IV));
		String	result	= ByteStringKit.toHex(data);
		System.out.println("PLAINTEXT:" + result);
		assertEquals(ECB_PLAINTEXT, result);
	}

	@Test
	void testSM4_ECB_PKCS5() {
		byte[]	enc		= SM4Kit.encrypt(AlgSM4.SM4_ECB_PKCS5, ByteStringKit.fromHex(ECB_PLAINTEXT),
				ByteStringKit.fromHex(KEY));
		String	hexEnc	= ByteStringKit.toHex(enc);
		System.out.println("CIPHERTEXT:" + hexEnc);

		byte[]	data	= SM4Kit.decrypt(AlgSM4.SM4_ECB_PKCS5, enc, ByteStringKit.fromHex(KEY));
		String	result	= ByteStringKit.toHex(data);
		System.out.println("PLAINTEXT:" + result);
		assertEquals(ECB_PLAINTEXT, result);
	}

	@Test
	void testSM4_SM4_CBC_PKCS5() {
		byte[]	enc		= SM4Kit.encrypt(AlgSM4.SM4_CBC_PKCS5, ByteStringKit.fromHex(ECB_PLAINTEXT),
				ByteStringKit.fromHex(KEY), ByteStringKit.fromHex(CBC_IV));
		String	hexEnc	= ByteStringKit.toHex(enc);
		System.out.println("CIPHERTEXT:" + hexEnc);

		byte[]	data	= SM4Kit.decrypt(AlgSM4.SM4_CBC_PKCS5, enc, ByteStringKit.fromHex(KEY),
				ByteStringKit.fromHex(CBC_IV));
		String	result	= ByteStringKit.toHex(data);
		System.out.println("PLAINTEXT:" + result);
		assertEquals(ECB_PLAINTEXT, result);
	}

}
