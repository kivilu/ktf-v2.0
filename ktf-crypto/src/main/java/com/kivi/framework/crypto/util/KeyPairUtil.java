package com.kivi.framework.crypto.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import com.kivi.framework.constant.KtfError;
import com.kivi.framework.crypto.domain.KeyPairResult;
import com.kivi.framework.crypto.enums.KeyType;
import com.kivi.framework.crypto.sm2.Sm2KeyPair;
import com.kivi.framework.crypto.sm2.Sm2KeyPairImpl;
import com.kivi.framework.exception.KtfException;

import lombok.extern.slf4j.Slf4j;

/**
 * 生成所有支持的秘钥对类型
 */
@Slf4j
public class KeyPairUtil {
    static {
        Security.addProvider(ProviderInstance.getBCProvider());
    }

    public static final String KEYSTORE_TYPE_P12 = "PKCS12";
    public static final String KEYSTORE_TYPE_JKS = "JKS";

    public static final KeyPairResult gen( KeyType type, Integer keySize ) throws Exception {
        KeyPairResult keyPairResult = new KeyPairResult();
        switch (type) {
            case RSA:
            case DSA:
            case ECDSA: {
                KeyPairGenerator kpg = KeyPairGenerator.getInstance(type.name, BouncyCastleProvider.PROVIDER_NAME);
                if (null != keySize) {
                    kpg.initialize(keySize);
                }
                KeyPair keyPair = kpg.generateKeyPair();
                keyPairResult.setPri(keyPair.getPrivate());
                keyPairResult.setPub(keyPair.getPublic());
                return keyPairResult;
            }
            case SM2: {
                Sm2KeyPair sm2KeyPair = new Sm2KeyPairImpl();
                keyPairResult.setPri(sm2KeyPair.getPrivate());
                keyPairResult.setPub(sm2KeyPair.getPublic());
                return keyPairResult;
            }
        }
        return null;
    }

    public final static String convertPublicKeyToPemString( PublicKey pub ) {
        try {
            if (null == pub) {
                return null;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            OutputStreamWriter outWriter = new OutputStreamWriter(out);
            JcaPEMWriter jcaPEMWriter = new JcaPEMWriter(outWriter);
            jcaPEMWriter.writeObject(pub);
            jcaPEMWriter.close();
            return new String(out.toByteArray());
        }
        catch (Exception e) {
            return null;
        }
    }

    public final static PrivateKey convertPemToPrivateKey( InputStream in ) {
        PrivateKey privateKey = null;
        try (PEMParser pemParser = new PEMParser(new InputStreamReader(in))) {
            Object readObject = pemParser.readObject();
            if (readObject instanceof PEMKeyPair) {
                PEMKeyPair key = (PEMKeyPair) readObject;
                privateKey = new JcaPEMKeyConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME).getKeyPair(key)
                        .getPrivate();
            }

        }
        catch (Exception e) {
            log.error("pem格式私钥转化为PrivateKey失败");
            throw new KtfException(KtfError.E_CRYPTO, "pem格式私钥转化为PrivateKey失败", e);
        }

        return privateKey;
    }

    public final static PublicKey convertPemToPublicKey( InputStream in ) {
        PublicKey publicKey = null;

        try (PEMParser pemParser = new PEMParser(new InputStreamReader(in))) {
            Object readObject = pemParser.readObject();
            if (readObject instanceof SubjectPublicKeyInfo) {
                SubjectPublicKeyInfo subjectPublicKeyInfo = (SubjectPublicKeyInfo) readObject;
                publicKey = new JcaPEMKeyConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME)
                        .getPublicKey(subjectPublicKeyInfo);
            }

        }
        catch (Exception e) {
            log.error("PEM格式公钥转换为PublicKey失败");
            throw new KtfException(KtfError.E_CRYPTO, "PEM格式公钥转换为PublicKey失败", e);
        }

        return publicKey;
    }

    public final static String convertPrivateKeyToPemString( PrivateKey pri ) {
        try {
            if (null == pri) {
                return null;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            OutputStreamWriter outWriter = new OutputStreamWriter(out);
            JcaPEMWriter jcaPEMWriter = new JcaPEMWriter(outWriter);
            jcaPEMWriter.writeObject(pri);
            jcaPEMWriter.close();
            return new String(out.toByteArray());
        }
        catch (Exception e) {
            return null;
        }
    }

    public static byte[] convertPemToDerEcData( String pemString ) {
        ByteArrayInputStream bIn = new ByteArrayInputStream(pemString.getBytes());
        try (PemReader pRdr = new PemReader(new InputStreamReader(bIn))) {
            PemObject pemObject = pRdr.readPemObject();
            return pemObject.getContent();
        }
        catch (IOException e) {
            log.error("PEM格式转换为DER异常", e);
        }
        return null;
    }

    /**
     * 根据文件名称判断秘钥类型
     * 
     * @param filePath
     * @return
     */
    public static String guessKeystoreType( String filePath ) {
        String suffix = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
        if (suffix.equals("jks") || suffix.equals("keystore")) {
            return KEYSTORE_TYPE_JKS;
        }
        else if (suffix.equals("p12")) {
            return KEYSTORE_TYPE_P12;
        }
        return KEYSTORE_TYPE_P12;
    }

}
