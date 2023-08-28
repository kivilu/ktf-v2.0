package com.kivi.crypto.service.impl;

import java.nio.ByteBuffer;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.stereotype.Service;

import com.kivi.crypto.service.DesService;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.crypto.ctx.SymCtx;
import com.kivi.framework.crypto.enums.AlgDES;
import com.kivi.framework.exception.KtfException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DesServiceImpl extends BaseSymmCryptoService implements DesService {

	@Override
	public String ctx(AlgDES alg, byte[] key) {
		return ctx(alg, key, null);
	}

	@Override
	public String ctx(AlgDES alg, byte[] key, byte[] iv) {
		String uuid = super.uuid(alg.getAlg(), key, iv);
		if (super.getCtx(uuid) != null)
			return uuid;

		return ctx(uuid, alg, key, iv);
	}

	@Override
	public String ctx(String ctxToken, AlgDES alg, byte[] key, byte[] iv) {
		String algName = alg.getAlg();
		if (alg.zeroPadding()) {
			if (iv != null)
				algName = alg.triple() ? AlgDES.TRIPLE_DES_CBC_NO.getAlg() : AlgDES.DES_CBC_NO.getAlg();
			else
				algName = alg.triple() ? AlgDES.TRIPLE_DES_ECB_NO.getAlg() : AlgDES.DES_ECB_NO.getAlg();
		}

		byte[] desKey = key;
		// triple-des密钥如果是16位则升级成24位
		if (alg.triple() && key.length == 16) {
			ByteBuffer desKeyBuf = ByteBuffer.allocate(24);
			desKeyBuf.put(key);
			desKeyBuf.put(key, 0, 8);
			desKey = desKeyBuf.array();
		}

		SecretKey secretKey = null;
		try {
			if (alg.triple()) {
				SecretKeyFactory	keyFactory		= SecretKeyFactory.getInstance("DESede");
				DESedeKeySpec		desedeKeySpec	= new DESedeKeySpec(desKey);
				secretKey = keyFactory.generateSecret(desedeKeySpec);
			} else {
				SecretKeyFactory	keyFactory	= SecretKeyFactory.getInstance("DES");
				DESKeySpec			desKeySpec	= new DESKeySpec(desKey);
				secretKey = keyFactory.generateSecret(desKeySpec);
			}
		} catch (Exception e) {
			log.error("DES密钥无效", e);
			throw new KtfException(KtfError.E_CRYPTO, "DES密钥无效", e);
		}

		SymCtx ctx = SymCtx.builder().uuid(ctxToken).blockSize(8).zeroPadding(alg.zeroPadding()).alg(algName)
				.triple(alg.triple()).cbc(iv != null).key(desKey).iv(iv).secretKey(secretKey).build();
		super.putCtx(ctx);

		return ctxToken;
	}

	@Override
	public String ctx(AlgDES alg, String keyId) {
		return ctx(alg, keyId, null);
	}

	@Override
	public String ctx(AlgDES alg, String keyId, byte[] iv) {
		throw new KtfException(KtfError.E_NOT_IMPLEMENT, "函数尚未实现");
	}

	@Override
	public byte[] encrypt(String ctxToken, byte[] data) {
		byte[]	result	= null;
		SymCtx	ctx		= getCtx(ctxToken);

		try {
			result = des(ctx, Cipher.ENCRYPT_MODE, data);
		} catch (Exception e) {
			log.error("DES加密异常", e);
			throw new KtfException(KtfError.E_CRYPTO, "DES加密异常", e);
		}

		return result;
	}

	@Override
	public byte[] decrypt(String ctxToken, byte[] data) {
		byte[]	result	= null;
		SymCtx	ctx		= getCtx(ctxToken);

		try {
			result = des(ctx, Cipher.DECRYPT_MODE, data);
		} catch (Exception e) {
			log.error("DES解密异常", e);
			throw new KtfException(KtfError.E_CRYPTO, "DES解密异常", e);
		}

		return result;
	}

	private byte[] des(SymCtx ctx, int mode, byte[] content) throws Exception {
		byte[] input = content;
		if (mode == Cipher.ENCRYPT_MODE && ctx.isZeroPadding())
			input = super.zeroPadding(ctx.getBlockSize(), content);

		SecretKey	secretKey	= (SecretKey) ctx.getSecretKey();
		Cipher		cipher		= Cipher.getInstance(ctx.getAlg());
		if (ctx.isCbc()) {
			IvParameterSpec iv = new IvParameterSpec(ctx.getIv());
			cipher.init(mode, secretKey, iv);
		} else
			cipher.init(mode, secretKey);
		byte[] result = cipher.doFinal(input);
		if (mode == Cipher.DECRYPT_MODE && ctx.isZeroPadding())
			result = super.trimZeroPadding(ctx.getBlockSize(), result);

		return result;
	}

}
