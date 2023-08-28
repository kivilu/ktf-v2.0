package com.kivi.crypto.service;

public interface SymmCryptoService {

	/**
	 * 1.0 加密
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	byte[] encrypt(String ctxToken, byte[] data);

	/**
	 * 1.1 加密返回HEX字符串
	 * 
	 * @param key
	 * @param data
	 * @param alg
	 * @return base64格式密文
	 */
	String encryptToBase64(String ctxToken, byte[] data);

	/**
	 * 1.2 加密返回HEX字符串
	 * 
	 * @param ctxToken
	 * @param data
	 * @return HEX格式密文
	 */
	String encryptToHex(String ctxToken, byte[] data);

	/**
	 * 2.0 解密
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	byte[] decrypt(String ctxToken, byte[] data);

	/**
	 * 2.1 解密
	 * 
	 * @param key
	 * @param data
	 * @param alg
	 * @return
	 */
	byte[] decryptFromBase64(String ctxToken, String dataBase64);

	/**
	 * 2.2 解密
	 * 
	 * @param key
	 * @param dataHex
	 * @return
	 */
	byte[] decryptFromHex(String ctxToken, String dataHex);

	/**
	 * 根据CTX获取对应的密钥
	 * 
	 * @param ctx
	 * @return
	 */
	byte[] key(String ctx);

	/**
	 * 删除CTX
	 * 
	 * @param ctxToken
	 */
	void evictCtx(String ctxToken);

}
