package com.kivi.framework.crypto.service;

import java.io.InputStream;

import com.kivi.framework.crypto.enums.AlgRSA;
import com.kivi.framework.crypto.enums.AlgSign;
import com.kivi.framework.crypto.enums.KeyStoreType;

/**
 * RSA加密/签名接口
 * 
 * @author Eric
 *
 */
public interface RsaService extends AsymCryptoService {

    /**
     * create RSA context
     * 
     * @param cipherAlg
     *            RSA加密/解密算法标识
     * @param signAlg
     *            RSA签名/验签算法标识
     * @param clientPubKeyBase64
     *            bas64格式RSA公钥，包含第一行的“-----BEGIN *** KEY-----”和最后一行的“-----END
     *            *** KEY-----的内容
     * @param serverPriKeyBase64
     *            bas64格式RSA私钥 ，包含第一行的“-----BEGIN *** KEY-----”和最后一行的“-----END
     *            *** KEY-----的内容
     * @return ctx token
     */
    String ctx( AlgRSA cipherAlg, AlgSign signAlg, String clientPubKeyBase64, String serverPriKeyBase64 );

    /**
     * create RSA context
     * 
     * @param cipherAlg
     *            RSA加密/解密算法标识
     * @param signAlg
     *            RSA签名/验签算法标识
     * @param clientPubKeyStream
     *            RSA公钥输入流
     * @param serverPriStream
     *            RSA私钥输入流
     * 
     * @return ctx token
     */
    String ctx( AlgRSA cipherAlg, AlgSign signAlg, InputStream clientPubKeyStream, InputStream serverPriStream );

    /**
     * create RSA context
     * 
     * @param clientCertBase64
     *            client端公钥证书base64格式内容
     * 
     * @param serverPfxHex
     *            pfx私钥证书HEX格式内容
     * 
     * @param pfxPass
     *            pfx证书密码
     * 
     * @param rootCertBase64
     *            CA根证书base64格式内容
     * 
     * @param crlBase64
     *            CA的CRL信任列表base64格式内容
     * @return ctx token
     */
    String ctx( String clientCertBase64, String serverPfxHex, String pfxPass, KeyStoreType keyStoreType,
            String rootCertBase64, String crlBase64 );

    /**
     * create RSA context
     * 
     * @param clientCertStream
     *            client端公钥证书输入流
     * @param serverPfxStream
     *            pfx私钥证书输入流
     * @param pfxPass
     *            pfx证书密码
     * @param rootCertStream
     *            CA根证书输入流
     * @param crlStream
     *            CA的CRL信任列表输入流
     * @return ctx token
     */
    String ctx( InputStream clientCertStream, InputStream serverPfxStream, String pfxPass, KeyStoreType keyStoreType,
            InputStream rootCertStream, InputStream crlStream );

    /**
     * create RSA context
     * 
     * @param certsId
     *            用户证书ID
     * @return
     */
    String ctx( String certsId );

}
