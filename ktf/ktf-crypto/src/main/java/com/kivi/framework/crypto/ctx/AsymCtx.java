package com.kivi.framework.crypto.ctx;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;

/**
 * 非对称算法CTX
 * 
 * @author Eric
 *
 */
@Builder
@Getter
public class AsymCtx implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Default
    private int               keybits          = 2048;

    @Default
    private int               reserveBytes     = 11;

    /**
     * CTX唯一标识
     */
    String                    uuid;

    /**
     * 加解密算法标识
     */
    private String            cipherAlg;

    /**
     * 签名算法标识
     */
    private String            signAlg;

    /**
     * server端私钥对象
     */
    private PrivateKey        serverPrivateKey;

    /**
     * client端公钥对象
     */
    private PublicKey         clientPublicKey;

    /**
     * client端x509证书
     */
    private X509Certificate   clientCert;

    /**
     * server端x509证书
     */
    private X509Certificate   serverCert;

    /**
     * CA根证书
     */
    private X509Certificate   rootCert;

    /**
     * CA发布的证书吊销列表
     */
    private X509CRL           crl;

}
