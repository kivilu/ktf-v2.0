package com.kivi.crypto.service.impl;

import javax.crypto.Cipher;

import org.springframework.stereotype.Service;

import com.kivi.crypto.service.SM4Service;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.crypto.ctx.SymCtx;
import com.kivi.framework.crypto.enums.AlgSM4;
import com.kivi.framework.crypto.sm4.SM4Kit;
import com.kivi.framework.exception.KtfException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SM4ServiceImpl extends BaseSymmCryptoService implements SM4Service {

	@Override
	public String ctx(AlgSM4 alg, byte[] key) {
		return ctx(alg, key, null);
	}

	@Override
	public String ctx(AlgSM4 alg, byte[] key, byte[] iv) {
		String uuid = super.uuid(alg.getAlg(), key, iv);
		if (super.getCtx(uuid) != null)
			return uuid;

		return ctx(uuid, alg, key, iv);
	}

	@Override
	public String ctx(String ctxToken, AlgSM4 alg, byte[] key, byte[] iv) {
		SymCtx ctx = SymCtx.builder().uuid(ctxToken).zeroPadding(alg.zeroPadding()).alg(alg.getAlg()).cbc(iv != null)
				.key(key).iv(iv).build();
		super.putCtx(ctx);

		return ctxToken;
	}

	@Override
	public String ctx(AlgSM4 alg, String keyId) {
		return ctx(alg, keyId, null);
	}

	@Override
	public String ctx(AlgSM4 alg, String keyId, byte[] iv) {
		throw new KtfException(KtfError.E_NOT_IMPLEMENT, "函数尚未实现");
	}

	@Override
	public byte[] encrypt(String ctxToken, byte[] data) {
		byte[]	result	= null;
		SymCtx	ctx		= super.getCtx(ctxToken);

		try {
			result = sm4(ctx, Cipher.ENCRYPT_MODE, data);
		} catch (Exception e) {
			log.error("SM4加密异常", e);
			throw new KtfException(KtfError.E_CRYPTO, "SM4加密异常", e);
		}

		return result;
	}

	@Override
	public byte[] decrypt(String ctxToken, byte[] data) {
		byte[]	result	= null;
		SymCtx	ctx		= super.getCtx(ctxToken);

		try {
			result = sm4(ctx, Cipher.DECRYPT_MODE, data);
		} catch (Exception e) {
			log.error("SM4解密异常", e);
			throw new KtfException(KtfError.E_CRYPTO, "SM4解密异常", e);
		}

		return result;
	}

	private byte[] sm4(SymCtx ctx, int mode, byte[] data) throws Exception {
		byte[]	result	= null;

		AlgSM4	alg		= AlgSM4.alg(ctx.getAlg());
		if (mode == Cipher.ENCRYPT_MODE)
			result = SM4Kit.encrypt(alg, data, ctx.getKey());
		else
			result = SM4Kit.decrypt(alg, data, ctx.getKey());

		return result;
	}

}
