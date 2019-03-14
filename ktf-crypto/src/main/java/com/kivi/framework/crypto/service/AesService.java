package com.kivi.framework.crypto.service;

import com.kivi.framework.crypto.enums.AlgAES;

/**
 * AES加密/解密接口
 * 
 * @author Eric
 *
 */
public interface AesService extends SymmCryptoService {

    /**
     * 创建AES的ECB方式加密/解密CTX
     * 
     * @param alg
     *            算法标识
     * 
     * @param key
     *            密钥
     * 
     * @return ctx token
     */
    String ctx( AlgAES alg, byte[] key );

    /**
     * 创建AES的CBC方式加密/解密CTX
     * 
     * @param alg
     *            算法标识
     * 
     * @param key
     *            密钥
     * @param iv
     *            初始向量
     * 
     * @return ctx token
     */
    String ctx( AlgAES alg, byte[] key, byte[] iv );

    /**
     * 创建AES的ECB方式加密/解密CTX
     * 
     * @param alg
     *            算法标识
     * @param keyId
     *            密钥标识
     * @return
     */
    String ctx( AlgAES alg, String keyId );

    /**
     * 创建AES的CBC方式加密/解密CTX
     * 
     * @param alg
     *            算法标识
     * 
     * @param keyId
     *            密钥标识
     * @param iv
     *            初始向量
     * 
     * @return ctx token
     */
    String ctx( AlgAES alg, String keyId, byte[] iv );

}
