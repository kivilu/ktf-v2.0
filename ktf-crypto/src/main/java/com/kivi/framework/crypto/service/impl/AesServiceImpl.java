package com.kivi.framework.crypto.service.impl;

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.kivi.framework.constant.KtfError;
import com.kivi.framework.crypto.ctx.SymCtx;
import com.kivi.framework.crypto.enums.AlgAES;
import com.kivi.framework.crypto.service.AesService;
import com.kivi.framework.exception.KtfException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AesServiceImpl extends BaseSymmCryptoService implements AesService {

    @Override
    public String ctx( AlgAES alg, byte[] key ) {
        return ctx(alg, key, null);
    }

    @Override
    public String ctx( AlgAES alg, byte[] key, byte[] iv ) {
        String uuid = super.uuid(alg.getAlg(), key, iv);
        if (super.getCtx(uuid) != null)
            return uuid;

        String algName = alg.getAlg();
        if (alg.zeroPadding()) {
            if (iv == null)
                algName = AlgAES.AES_ECB_NO.getAlg();
            else
                algName = AlgAES.AES_CBC_NO.getAlg();
        }

        if (key.length != 16)
            throw new KtfException(KtfError.E_CRYPTO, "AES密钥长度不正确");

        SymCtx ctx = SymCtx.builder().uuid(uuid).zeroPadding(alg.zeroPadding())
                .alg(algName).cbc(iv != null).key(key).iv(iv)
                .secretKey(new SecretKeySpec(key, "AES"))
                .build();
        super.putCtx(ctx);

        return uuid;
    }

    @Override
    public String ctx( AlgAES alg, String keyId ) {
        throw new KtfException(KtfError.E_CRYPTO, "函数尚未实现");
    }

    @Override
    public String ctx( AlgAES alg, String keyId, byte[] iv ) {
        throw new KtfException(KtfError.E_CRYPTO, "函数尚未实现");
    }

    @Override
    public byte[] encrypt( String ctxToken, byte[] data ) {
        byte[] result = null;
        SymCtx ctx = getCtx(ctxToken);

        try {
            result = aes(ctx, Cipher.ENCRYPT_MODE, data);
        }
        catch (Exception e) {
            log.error("AES加密异常", e);
            throw new KtfException(KtfError.E_CRYPTO, "AES加密异常", e);
        }

        return result;
    }

    @Override
    public byte[] decrypt( String ctxToken, byte[] data ) {
        byte[] result = null;
        SymCtx ctx = getCtx(ctxToken);

        try {
            result = aes(ctx, Cipher.DECRYPT_MODE, data);
        }
        catch (Exception e) {
            log.error("AES解密异常", e);
            throw new KtfException(KtfError.E_CRYPTO, "AES解密异常", e);
        }

        return result;
    }

    private byte[] aes( SymCtx ctx, int mode, byte[] content ) throws Exception {
        byte[] key = ctx.getKey();
        // 不是16的倍数的，补足
        int base = ctx.getBlockSize();
        if (key.length % base != 0) {
            int groups = key.length / base + (key.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(key, 0, temp, 0, key.length);
            key = temp;
        }

        byte[] input = content;
        if (mode == Cipher.ENCRYPT_MODE && ctx.isZeroPadding())
            input = super.zeroPadding(ctx.getBlockSize(), content);

        SecretKey secretKey = (SecretKey) ctx.getSecretKey();
        Cipher cipher = Cipher.getInstance(ctx.getAlg());
        if (ctx.isCbc()) {
            IvParameterSpec iv = new IvParameterSpec(ctx.getIv());
            cipher.init(mode, secretKey, iv);
        }
        else {
            cipher.init(mode, secretKey);
        }

        byte[] tgtBytes = cipher.doFinal(input);
        if (mode == Cipher.DECRYPT_MODE && ctx.isZeroPadding())
            tgtBytes = super.trimZeroPadding(ctx.getBlockSize(), tgtBytes);
        return tgtBytes;
    }

}
