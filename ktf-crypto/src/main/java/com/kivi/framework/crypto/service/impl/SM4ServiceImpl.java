package com.kivi.framework.crypto.service.impl;

import javax.crypto.Cipher;

import org.springframework.stereotype.Service;

import com.kivi.framework.constant.KtfError;
import com.kivi.framework.crypto.ctx.SymCtx;
import com.kivi.framework.crypto.enums.AlgSM4;
import com.kivi.framework.crypto.service.SM4Service;
import com.kivi.framework.crypto.sm4.SM4;
import com.kivi.framework.crypto.sm4.SM4Ctx;
import com.kivi.framework.exception.KtfException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SM4ServiceImpl extends BaseSymmCryptoService implements SM4Service {

    private static ThreadLocal<SM4> sm4Inst = new ThreadLocal<SM4>() {

        @Override
        protected SM4 initialValue() {
            return new SM4();
        }

    };

    @Override
    public String ctx( AlgSM4 alg, byte[] key ) {
        return ctx(alg, key, null);
    }

    @Override
    public String ctx( AlgSM4 alg, byte[] key, byte[] iv ) {
        String uuid = super.uuid(alg.getAlg(), key, iv);
        if (super.getCtx(uuid) != null)
            return uuid;

        SM4Ctx sm4Ctx = new SM4Ctx(alg.padding());

        SymCtx ctx = SymCtx.builder().uuid(uuid)
                .blockSize(sm4Ctx.blockSize)
                .zeroPadding(alg.zeroPadding())
                .alg(alg.getAlg())
                .cbc(iv != null)
                .key(key)
                .iv(iv)
                .secretKey(sm4Ctx)
                .build();
        super.putCtx(ctx);

        return uuid;

    }

    @Override
    public String ctx( AlgSM4 alg, String keyId ) {
        return ctx(alg, keyId, null);
    }

    @Override
    public String ctx( AlgSM4 alg, String keyId, byte[] iv ) {
        throw new KtfException(KtfError.E_NOT_IMPLEMENT, "函数尚未实现");
    }

    @Override
    public byte[] encrypt( String ctxToken, byte[] data ) {
        byte[] result = null;
        SymCtx ctx = super.getCtx(ctxToken);

        try {
            result = sm4(ctx, Cipher.ENCRYPT_MODE, data);
        }
        catch (Exception e) {
            log.error("SM4加密异常", e);
            throw new KtfException(KtfError.E_CRYPTO, "SM4加密异常", e);
        }

        return result;
    }

    @Override
    public byte[] decrypt( String ctxToken, byte[] data ) {
        byte[] result = null;
        SymCtx ctx = super.getCtx(ctxToken);

        try {
            result = sm4(ctx, Cipher.DECRYPT_MODE, data);
        }
        catch (Exception e) {
            log.error("SM4加密异常", e);
            throw new KtfException(KtfError.E_CRYPTO, "SM4加密异常", e);
        }

        return result;
    }

    private byte[] sm4( SymCtx ctx, int mode, byte[] data ) throws Exception {
        byte[] result = null;
        SM4Ctx sm4Ctx = (SM4Ctx) ctx.getSecretKey();

        SM4 sm4 = sm4Inst.get();

        if (mode == Cipher.ENCRYPT_MODE)
            sm4.sm4_setkey_enc(sm4Ctx, ctx.getKey());
        else
            sm4.sm4_setkey_dec(sm4Ctx, ctx.getKey());

        if (ctx.isCbc())
            result = sm4.sm4_crypt_cbc(sm4Ctx, ctx.getIv(), data);
        else
            result = sm4.sm4_crypt_ecb(sm4Ctx, data);

        return result;
    }

}
