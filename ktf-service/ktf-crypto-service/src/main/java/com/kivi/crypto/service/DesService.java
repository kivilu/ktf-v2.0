package com.kivi.crypto.service;

import com.kivi.framework.crypto.enums.AlgDES;

/**
 * DES加密/解密接口
 * 
 * @author Eric
 *
 */
public interface DesService extends SymmCryptoService {

	/**
	 * 创建DES的ECB方式加密/解密CTX
	 * 
	 * @param alg 算法标识
	 * 
	 * @param key 密钥
	 * 
	 * @return
	 */
	String ctx(AlgDES alg, byte[] key);

	/**
	 * 创建DES的CBC方式加密/解密CTX
	 * 
	 * @param alg 算法标识
	 * 
	 * @param key 密钥
	 * @param iv 初始向量
	 * 
	 * @return
	 */
	String ctx(AlgDES alg, byte[] key, byte[] iv);

	/**
	 * 创建DES的CBC方式加密/解密CTX
	 * 
	 * @param ctxToken
	 * @param alg 算法标识
	 * @param key 密钥
	 * @param iv 初始向量
	 * 
	 * @return
	 */
	String ctx(String ctxToken, AlgDES alg, byte[] key, byte[] iv);

	/**
	 * 创建DES的ECB方式加密/解密CTX
	 * 
	 * @param alg 算法标识
	 * @param keyId 密钥标识
	 * @return
	 */
	String ctx(AlgDES alg, String keyId);

	/**
	 * 创建DES的CBC方式加密/解密CTX
	 * 
	 * @param alg 算法标识
	 * 
	 * @param keyId 密钥标识
	 * @param iv 初始向量
	 * 
	 * @return ctx token
	 */
	String ctx(AlgDES alg, String keyId, byte[] iv);

}
