package com.kivi.framework.crypto.util;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
/**
 * Cipher工具类
 */
import java.security.Security;
import java.security.Signature;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.kivi.framework.constant.KtfError;
import com.kivi.framework.exception.KtfException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CipherUtil {
    static {
        Security.addProvider(ProviderInstance.getBCProvider());
    }

    /**
     * cipher 获取
     * 
     * @param algorithm
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    public static Cipher getCipherInstance( String algorithm ) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(algorithm, BouncyCastleProvider.PROVIDER_NAME);
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException e) {
            log.error("获取Cipher异常", e);
            throw new KtfException(KtfError.E_CRYPTO, "获取Cipher异常", e);
        }
        return cipher;
    }

    /**
     * cipher 获取
     * 
     * @param algorithm
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    public static Signature getSignatureInstance( String algorithm ) {
        Signature signature = null;
        try {
            signature = Signature.getInstance(algorithm, BouncyCastleProvider.PROVIDER_NAME);
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            log.error("获取Signature异常", e);
            throw new KtfException(KtfError.E_CRYPTO, "获取Signature异常", e);
        }
        return signature;
    }
}
