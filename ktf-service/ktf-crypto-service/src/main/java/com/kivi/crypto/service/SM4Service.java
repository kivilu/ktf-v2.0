package com.kivi.crypto.service;

import com.kivi.framework.crypto.enums.AlgSM4;

/**
 * SM4加密/解密接口
 * 
 * @author Eric
 *
 */
public interface SM4Service extends SymmCryptoService {

	/**
	 * 创建SM4的ECB方式加密/解密CTX
	 * 
	 * @param alg 算法标识
	 * 
	 * @param key 密钥
	 * 
	 * @return
	 */
	String ctx(AlgSM4 alg, byte[] key);

	/**
	 * 创建SM4的CBC方式加密/解密CTX
	 * 
	 * @param alg 算法标识
	 * 
	 * @param key 密钥
	 * @param iv 初始向量
	 * 
	 * @return
	 */
	String ctx(AlgSM4 alg, byte[] key, byte[] iv);

	/**
	 * 创建SM4的ECB方式加密/解密CTX
	 * 
	 * @param alg 算法标识
	 * @param keyId 密钥标识
	 * @return
	 */
	String ctx(AlgSM4 alg, String keyId);

	/**
	 * 创建SM4的CBC方式加密/解密CTX
	 * 
	 * @param alg 算法标识
	 * 
	 * @param keyId 密钥标识
	 * @param iv 初始向量
	 * 
	 * @return ctx token
	 */
	String ctx(AlgSM4 alg, String keyId, byte[] iv);

	/**
	 * 创建SM4的ECB方式加密/解密CTX
	 * 
	 * @param alg 算法标识
	 * 
	 * @param key 密钥
	 * 
	 * @return
	 */
	String ctx(String ctxToken, AlgSM4 alg, byte[] key, byte[] iv);

}
