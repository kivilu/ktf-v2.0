package com.kivi.crypto.dubbo.service.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import java.nio.ByteBuffer;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.kivi.crypto.dubbo.service.KtfCryptoService;
import com.kivi.crypto.enums.KtfCryptoAlg;
import com.kivi.crypto.service.impl.AesServiceImpl;
import com.kivi.crypto.service.impl.CtxCacheServiceImpl;
import com.kivi.crypto.service.impl.RsaServiceImpl;
import com.kivi.crypto.service.impl.SM2ServiceImpl;
import com.kivi.crypto.service.impl.SM4ServiceImpl;
import com.kivi.framework.crypto.domain.KeyPairDO;
import com.kivi.framework.crypto.enums.AlgSign;
import com.kivi.framework.dto.warapper.WarpReqDTO;
import com.kivi.framework.util.kit.ByteStringKit;
import com.kivi.framework.util.kit.DateTimeKit;
import com.kivi.framework.util.kit.StrKit;

@RunWith(SpringRunner.class)
@Import(
		value = { KtfCryptoServiceImpl.class, SM4ServiceImpl.class, SM2ServiceImpl.class, RsaServiceImpl.class,
				AesServiceImpl.class, CtxCacheServiceImpl.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class KtfCryptoServiceImplTest {

	private static final byte[]	DEFAULT_LOCK_SE_KEY	= { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
			(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
			(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, };

	private static final String	sm2PriKey			= "-----BEGIN EC PRIVATE KEY-----\r\n"
			+ "MHcCAQEEIP3noZgpsGgRpkVHucf+/aUwY88wZol3GVnTB6qoyktQoAoGCCqBHM9V\r\n"
			+ "AYItoUQDQgAEV8XR/SxOBtGvzaTR6gi5tMQ6vFml26/y6AJeD7FQaoVnyBXUPLpk\r\n"
			+ "IGFqEHX6qvh3qJ/L95d1Om+DzDVegKwy+g==\r\n" + "-----END EC PRIVATE KEY-----";

	@Autowired
	private KtfCryptoService	ktfCryptoService;

	@Test
	public void testInitSeKey() {
		for (int i = 0; i < 1; i++) {
			// String lockSn = String.format("1e201904291c8%03d", 10 + i);
			String				lockSn		= "1e201904291c6007";
			byte[]				encodeKey	= DigestUtils.md5(lockSn);
			String				ctx			= ktfCryptoService.ctx(new WarpReqDTO<>(-1L, encodeKey),
					KtfCryptoAlg.SM4_NoPadding);

			WarpReqDTO<String>	ctxTokenDTO	= new WarpReqDTO<>(-1L, ctx);
			byte[]				encSeKey	= ktfCryptoService.encrypt(ctxTokenDTO, DEFAULT_LOCK_SE_KEY);

			System.out.println(lockSn + "的默认厂商密钥：" + ByteStringKit.toString(encSeKey, ByteStringKit.BASE64));
		}
	}

	@Test
	public void testCtxWarpReqDTOOfbyteInsCryptoAlg() {
		fail("Not yet implemented");
	}

	@Test
	public void testCtxWarpReqDTOOfKeyPairDOAlgSign() {
		fail("Not yet implemented");
	}

	@Test
	public void testEvictCtx() {
		fail("Not yet implemented");
	}

	@Test
	public void testEncrypt() {
		String		hexData		= "FF08D0000104DD";
		String		ctx			= ktfCryptoService.ctx(new WarpReqDTO<>(-1L, DEFAULT_LOCK_SE_KEY), KtfCryptoAlg.SM4);

		ByteBuffer	dataBuffer	= ByteBuffer.allocate(11);
		dataBuffer.put(ByteStringKit.toBytes(hexData, ByteStringKit.HEX));
		dataBuffer.putInt((int) DateTimeKit.currentUnix());

		System.out.println("明文：" + ByteStringKit.toHex(dataBuffer.array()));

		WarpReqDTO<String>	ctxTokenDTO	= new WarpReqDTO<>(-1L, ctx);
		byte[]				sm4Data		= ktfCryptoService.encrypt(ctxTokenDTO, dataBuffer.array());

		System.out.println("SM4密文：" + ByteStringKit.toHex(sm4Data));
	}

	@Test
	public void testDecrypt() {
		WarpReqDTO<String>	ctxTokenDTO	= new WarpReqDTO<>(-1L, StrKit.join(StrKit.DOT, "KtfCryptoServiceImpl",
				"AlgSM4.SM4_ECB_NoPadding", "01e9e97b12784107ff6c02a6ac6c6b74"));
		byte[]				encData		= ByteStringKit.toBytes("EqWiyOOHdRZ8vTHszKpuJg==", ByteStringKit.BASE64);
		byte[]				data		= ktfCryptoService.decrypt(ctxTokenDTO, encData);

		assertArrayEquals(DEFAULT_LOCK_SE_KEY, data);
	}

	@Test
	public void testCrcWarpReqDTOOfStringByteArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testCrcWarpReqDTOOfStringShortByteArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testSignWarpReqDTOOfStringByteArray() {
		KeyPairDO keyPair = new KeyPairDO();
		keyPair.setPri_pem(sm2PriKey);
		String		ctx			= ktfCryptoService.ctx(new WarpReqDTO<>(-1L, keyPair), AlgSign.SM2_SM3);

		String		hexData		= "FF08D0000104DD5DA7C1DC";
		ByteBuffer	dataBuffer	= ByteBuffer.allocate(11);
		dataBuffer.put(ByteStringKit.toBytes(hexData, ByteStringKit.HEX));

		System.out.println("明文：" + ByteStringKit.toHex(dataBuffer.array()));

		byte[] sign = ktfCryptoService.sign(new WarpReqDTO<>(-1L, ctx), "12345678", dataBuffer.array());
		System.out.println("签名：" + ByteStringKit.toHex(sign));
	}

	@Test
	public void testSignWarpReqDTOOfStringStringByteArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testVerifyWarpReqDTOOfStringByteArrayByteArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testVerifyWarpReqDTOOfStringStringByteArrayByteArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testGenKeypair() {
		fail("Not yet implemented");
	}

	@Test
	public void testConvertPem2Bin() {
		fail("Not yet implemented");
	}

}
