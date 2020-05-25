package com.kivi.framework.crypto.aes;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.kivi.framework.constant.KtfError;
import com.kivi.framework.crypto.ctx.SymCtx;
import com.kivi.framework.crypto.enums.AlgAES;
import com.kivi.framework.crypto.util.PaddingUtil;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.ByteStringKit;

import lombok.extern.slf4j.Slf4j;

/**
 * AES加密工具
 * 
 * @author Eric
 *
 */
@Slf4j
public class AESKit {
	private final static Charset	CHARSET_UTF8	= Charset.forName("UTF-8");
	private final AlgAES			aesAlg;

	private AESKit(AlgAES aesAlg) {
		this.aesAlg = aesAlg;
	}

	public static AESKit newAESKit(AlgAES aesAlg) {
		return new AESKit(aesAlg);
	}

	/**
	 * CBC加密
	 * 
	 * @param key
	 * @param data
	 * @param iv
	 * @return
	 */
	public byte[] encrypt(byte[] key, byte[] data, byte[] iv) {
		if (iv == null) {
			iv = new byte[this.aesAlg.keyLength()];
		}
		SymCtx ctx = ctx(this.aesAlg, key, iv);
		return encrypt(ctx, data);
	}

	/**
	 * CBC解密
	 * 
	 * @param key
	 * @param data
	 * @param iv
	 * @return
	 */
	public byte[] decrypt(byte[] key, byte[] data, byte[] iv) {
		if (iv == null) {
			iv = new byte[this.aesAlg.keyLength()];
		}
		SymCtx ctx = ctx(this.aesAlg, key, iv);
		return decrypt(ctx, data);
	}

	/**
	 * ECB加密
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	public byte[] encrypt(byte[] key, byte[] data) {
		SymCtx ctx = ctx(this.aesAlg, key, null);
		return encrypt(ctx, data);
	}

	/**
	 * ECB解密
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	public byte[] decrypt(byte[] key, byte[] data) {
		SymCtx ctx = ctx(this.aesAlg, key, null);
		return decrypt(ctx, data);
	}

	/**
	 * 解密密钥和密文都是Base64格式的数据
	 * 
	 * @param base64Key
	 * @param base64Content
	 * @param base64Iv
	 * @return UTF-8字符串
	 */
	public String decodeBase64(String base64Key, String base64Content, String base64Iv) {
		byte[]	key		= ByteStringKit.toBytes(base64Key, ByteStringKit.BASE64);
		byte[]	iv		= ByteStringKit.toBytes(base64Iv, ByteStringKit.BASE64);
		byte[]	content	= ByteStringKit.toBytes(base64Content, ByteStringKit.BASE64);

		byte[]	data	= this.decrypt(key, content, iv);
		return new String(data, CHARSET_UTF8);
	}

	/**
	 * 对字符串数据加密并返回返回HEX字符串
	 * 
	 * @param keyHex
	 * @param contentHex
	 * @return 返回HEX字符串
	 */
	public String encodeHex(byte[] key, String content) {
		byte[] encBytes = encrypt(key, content.getBytes(CHARSET_UTF8));

		return ByteStringKit.toString(encBytes, ByteStringKit.HEX);
	}

	/**
	 * 对字符串数据加密并返回返回HEX字符串
	 * 
	 * @param key
	 * @param content
	 * @return
	 */
	public String encodeHex(String key, String content) {
		return encodeHex(key.getBytes(CHARSET_UTF8), content);
	}

	/**
	 * 解密HEX密文字符串
	 * 
	 * @param key
	 * @param contentHex
	 * @return
	 */
	public String decodeHex(byte[] key, String contentHex) {
		byte[]	content		= ByteStringKit.toBytes(contentHex, ByteStringKit.HEX);
		byte[]	plainBytes	= decrypt(key, content);
		return new String(plainBytes, CHARSET_UTF8);
	}

	/**
	 * 解密HEX密文字符串
	 * 
	 * @param key
	 * @param contentHex
	 * @return
	 */
	public String decodeHex(String key, String contentHex) {
		return decodeHex(key.getBytes(CHARSET_UTF8), contentHex);
	}

	/**
	 * 加密文件
	 * 
	 * @param key 密钥
	 * @param plainFilePath 明文文件路径路径
	 * @param secretFilePath 密文文件路径
	 * @throws Exception
	 */
	public void encodeAESFile(byte[] key, String plainFilePath, String secretFilePath) {
		try (FileInputStream fis = new FileInputStream(plainFilePath);
				ByteArrayOutputStream bos = new ByteArrayOutputStream(fis.available());
				FileOutputStream fos = new FileOutputStream(secretFilePath);) {

			byte[]	buffer	= new byte[1024];
			int		count	= 0;
			while ((count = fis.read(buffer)) != -1) {
				bos.write(buffer, 0, count);
			}
			bos.flush();

			byte[] bytes = encrypt(key, bos.toByteArray());

			fos.write(bytes);
			fos.flush();
		} catch (Exception e) {
			log.error("AES文件加密异常", e);
			throw new KtfException(KtfError.E_CRYPTO, "AES文件加密异常", e);
		}
	}

	/**
	 * 解密文件
	 * 
	 * @param key 密钥
	 * @param plainFilePath 明文文件路径路径
	 * @param secretFilePath 密文文件路径
	 * @throws Exception
	 */
	public void decodeAESFile(byte[] key, String plainFilePath, String secretFilePath) throws Exception {
		try (FileInputStream fis = new FileInputStream(secretFilePath);
				ByteArrayOutputStream bos = new ByteArrayOutputStream(fis.available());
				FileOutputStream fos = new FileOutputStream(plainFilePath);) {

			byte[]	buffer	= new byte[1024];
			int		count	= 0;
			while ((count = fis.read(buffer)) != -1) {
				bos.write(buffer, 0, count);
			}
			bos.flush();

			byte[] bytes = decrypt(key, bos.toByteArray());

			fos.write(bytes);
			fos.flush();
		} catch (Exception e) {
			log.error("AES文件解密异常", e);
			throw new KtfException(KtfError.E_CRYPTO, "AES文件解密异常", e);
		}
	}

	/**
	 * 生成符合AES要求的密钥.
	 */
	public static byte[] generateDesKey(int length) {
		// 实例化
		KeyGenerator kgen = null;
		try {
			kgen = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			log.error("初始化失败", e);
			return null;
		}
		// 设置密钥长度
		kgen.init(length);
		// 生成密钥
		SecretKey skey = kgen.generateKey();
		// 返回密钥的二进制编码
		return skey.getEncoded();
	}

	/**
	 * 创建CTX
	 * 
	 * @param alg
	 * @param key
	 * @param iv
	 * @return
	 */
	public SymCtx ctx(AlgAES alg, byte[] key, byte[] iv) {
		String algName = alg.getAlg();
		if (alg.zeroPadding()) {
			if (iv == null)
				algName = AlgAES.AES_ECB_NO.getAlg();
			else
				algName = AlgAES.AES_CBC_NO.getAlg();
		}

		if (key.length != 16) {
			key = PaddingUtil.zeroPadding(16, key);
		}

		SymCtx ctx = SymCtx.builder().zeroPadding(alg.zeroPadding()).alg(algName).cbc(iv != null).key(key).iv(iv)
				.secretKey(new SecretKeySpec(key, "AES")).build();

		return ctx;
	}

	private byte[] encrypt(SymCtx ctx, byte[] data) {
		byte[] result = null;
		try {
			result = aes(ctx, Cipher.ENCRYPT_MODE, data);
		} catch (Exception e) {
			log.error("AES加密异常", e);
			throw new KtfException(KtfError.E_CRYPTO, "AES加密异常", e);
		}

		return result;
	}

	private byte[] decrypt(SymCtx ctx, byte[] data) {
		byte[] result = null;
		try {
			result = aes(ctx, Cipher.DECRYPT_MODE, data);
		} catch (Exception e) {
			log.error("AES解密异常", e);
			throw new KtfException(KtfError.E_CRYPTO, "AES解密异常", e);
		}

		return result;
	}

	private byte[] aes(SymCtx ctx, int mode, byte[] content) throws Exception {
		byte[]	key		= ctx.getKey();
		// 不是16的倍数的，补足
		int		base	= ctx.getBlockSize();
		if (key.length % base != 0) {
			int		groups	= key.length / base + (key.length % base != 0 ? 1 : 0);
			byte[]	temp	= new byte[groups * base];
			Arrays.fill(temp, (byte) 0);
			System.arraycopy(key, 0, temp, 0, key.length);
			key = temp;
		}

		byte[] input = content;
		if (mode == Cipher.ENCRYPT_MODE && ctx.isZeroPadding())
			input = PaddingUtil.zeroPadding(ctx.getBlockSize(), content);

		SecretKey	secretKey	= (SecretKey) ctx.getSecretKey();
		Cipher		cipher		= Cipher.getInstance(ctx.getAlg());
		if (ctx.isCbc()) {
			IvParameterSpec iv = new IvParameterSpec(ctx.getIv());
			cipher.init(mode, secretKey, iv);
		} else {
			cipher.init(mode, secretKey);
		}

		byte[] tgtBytes = cipher.doFinal(input);
		if (mode == Cipher.DECRYPT_MODE && ctx.isZeroPadding())
			tgtBytes = PaddingUtil.trimZeroPadding(ctx.getBlockSize(), tgtBytes);
		return tgtBytes;
	}
}
