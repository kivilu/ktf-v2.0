package com.kivi.framework.crypto.service;

import java.util.Date;

/**
 * 非对称加密算法接口
 * 
 * @author Eric
 *
 */
public interface AsymCryptoService {
    /**
     * 1.0 公钥加密
     * 
     * @param ctxToken
     *            rsa context token
     * @param data
     *            明文数据
     * @return
     */
    byte[] encrypt( String ctxToken, byte[] data );

    /**
     * 1.1 公钥加密
     * 
     * @param ctxToken
     *            rsa context token
     * @param data
     *            明文数据
     * @return 返回base64字符串
     */
    String encryptToBase64( String ctxToken, byte[] data );

    /**
     * 1.2 公钥加密
     * 
     * @param ctxToken
     *            rsa context token
     * @param data
     *            明文数据
     * @return 返回HEX字符串
     */
    String encryptToHex( String ctxToken, byte[] data );

    /**
     * 2.0私钥解密
     * 
     * @param ctxToken
     *            rsa context token
     * @param data
     *            密文数据
     * @return
     */
    byte[] decrypt( String ctxToken, byte[] data );

    /**
     * 2.1 私钥解密
     * 
     * @param ctxToken
     *            rsa context token
     * @param data
     *            base64格式密文数据
     * @return
     */
    byte[] decryptFromBase64( String ctxToken, String dataBase64 );

    /**
     * 2.2 私钥解密
     * 
     * @param ctxToken
     *            rsa context token
     * @param data
     *            hex格式密文数据
     * @return
     */
    byte[] decryptFromHex( String ctxToken, String dataHex );

    /**
     * 3.0 签名
     * 
     * @param ctxToken
     *            rsa context token
     * @param data
     *            明文数据
     * @return ASN.1编码格式前面
     */
    byte[] sign( String ctxToken, byte[] data );

    /**
     * 3.0 签名
     * 
     * @param ctxToken
     *            rsa context token
     * @param data
     *            明文数据
     * @return 签名的原始数据(r,s)
     */
    byte[] signWithNoHash( String ctxToken, byte[] data );

    /**
     * 3.0 签名
     * 
     * @param ctxToken
     *            rsa context token
     * @param data
     *            明文数据
     * @return 签名的原始数据(r,s)
     */
    byte[] signWithId( String ctxToken, String withId, byte[] data );

    /**
     * 3.1 签名
     * 
     * @param ctxToken
     *            rsa context token
     * @param data
     *            明文数据
     * @return base64格式签名结果
     */
    String signToBase64( String ctxToken, byte[] data );

    /**
     * 3.2 签名
     * 
     * @param ctxToken
     *            rsa context token
     * @param data
     *            明文数据
     * @return Hex格式签名结果
     */
    String signToHex( String ctxToken, byte[] data );

    /**
     * 4.0 验证签名
     * 
     * @param ctxToken
     *            rsa context token
     * @param data
     *            明文数据
     * @param sign
     *            签名数据
     * 
     * @return
     */
    boolean verify( String ctxToken, byte[] data, byte[] sign );

    /**
     * 4.0 带ID验证签名
     * 
     * @param ctxToken
     * @param withId
     * @param data
     * @param sign
     * @return
     */
    boolean verifyWithId( String ctxToken, String withId, byte[] data, byte[] sign );

    /**
     * 不带摘要验签
     * 
     * @param ctxToken
     * @param data
     * @param sign
     * @return
     */
    boolean verifyWithNoHash( String ctxToken, byte[] data, byte[] sign );

    /**
     * 4.1 验证签名
     * 
     * @param ctxToken
     *            rsa context token
     * @param data
     *            明文数据
     * @param sign
     *            base64格式签名数据
     * 
     * @return
     */
    boolean verifyFromBase64( String ctxToken, byte[] data, String signBase64 );

    /**
     * 4.2 验证签名
     * 
     * @param ctxToken
     *            rsa context token
     * @param data
     *            明文数据
     * @param sign
     *            HEX格式签名数据
     * 
     * @return
     */
    boolean verifyFromHex( String ctxToken, byte[] data, String signHex );

    /**
     * X509证书的失效日期
     * 
     * @param certSeqNo
     * @return
     */
    Date getCertExpireDate( String certSeqNo );
}
