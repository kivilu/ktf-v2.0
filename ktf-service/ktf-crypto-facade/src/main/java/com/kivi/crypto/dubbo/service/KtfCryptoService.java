package com.kivi.crypto.dubbo.service;

import com.kivi.crypto.enums.KtfCryptoAlg;
import com.kivi.framework.crypto.domain.KeyPairDO;
import com.kivi.framework.crypto.enums.AlgSign;
import com.kivi.framework.crypto.enums.KtfCertType;
import com.kivi.framework.dto.KtfDTO;
import com.kivi.framework.exception.KtfException;

/**
 * KTF统一加密服务
 * 
 * @author Eric.Lu 2019年2月4日
 */
public interface KtfCryptoService {

	public static final String MOCK = "com.kivi.crypto.dubbo.mock.KtfCryptoServiceMock";

	/**
	 * 对称加密ECB方式加密/解密CTX
	 * 
	 * @param key 密钥
	 * @param alg 算法标识
	 * 
	 * @return
	 */
	String ctx(KtfDTO<byte[]> keyDTO, KtfCryptoAlg alg) throws KtfException;

	/**
	 * 签名算法CTX
	 * 
	 * @param keypairDTO
	 * @param signAlg
	 * @return
	 */
	String ctx(KtfDTO<KeyPairDO> keypairDTO, AlgSign signAlg);

	/**
	 * CRC计算
	 * 
	 * @param ctxDTO
	 * @param crc
	 * @param data
	 * @return
	 */
	Short crc(KtfDTO<String> ctxTokenDTO, byte[] data) throws KtfException;

	/**
	 * CRC校验
	 * 
	 * @param ctxDTO
	 * @param crc
	 * @param data
	 * @return
	 */
	Boolean crc(KtfDTO<String> ctxTokenDTO, Short crc, byte[] data) throws KtfException;

	/**
	 * 加密
	 * 
	 * @param ctxDTO
	 * @param data
	 * @return
	 */
	byte[] encrypt(KtfDTO<String> ctxTokenDTO, byte[] data) throws KtfException;

	/**
	 * 解密
	 * 
	 * @param ctxDTO
	 * @param data
	 * @return
	 */
	byte[] decrypt(KtfDTO<String> ctxTokenDTO, byte[] data) throws KtfException;

	/**
	 * 生成公私钥密钥对
	 * 
	 * @param alg
	 * @return
	 */
	KeyPairDO genKeypair(AlgSign alg);

	/**
	 * 签名
	 * 
	 * @param ctxDTO
	 * @param data
	 * @return
	 * @throws KtfException
	 */
	byte[] sign(KtfDTO<String> ctxTokenDTO, byte[] data) throws KtfException;

	/**
	 * 签名
	 * 
	 * @param ctxDTO
	 * @param data
	 * @return
	 * @throws KtfException
	 */
	byte[] sign(KtfDTO<String> ctxTokenDTO, String withId, byte[] data) throws KtfException;

	/**
	 * 验签
	 * 
	 * @param ctxDTO
	 * @param data
	 * @param sign
	 * @return
	 * @throws KtfException
	 */
	Boolean verify(KtfDTO<String> ctxTokenDTO, byte[] data, byte[] sign) throws KtfException;

	/**
	 * 验签
	 * 
	 * @param ctxDTO
	 * @param data
	 * @param sign
	 * @return
	 * @throws KtfException
	 */
	Boolean verify(KtfDTO<String> ctxTokenDTO, String withId, byte[] data, byte[] sign) throws KtfException;

	/**
	 * 将PEM格式密钥转换为原始二进制密钥数据
	 * 
	 * @param base64Key
	 * @return
	 */
	byte[] convertPem2Bin(KtfDTO<String> base64KeyDTO, KtfCertType type);
}
