package com.kivi.framework.crypto.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.stereotype.Service;

import com.kivi.framework.constant.KtfError;
import com.kivi.framework.crypto.ctx.AsymCtx;
import com.kivi.framework.crypto.enums.AlgRSA;
import com.kivi.framework.crypto.enums.AlgSign;
import com.kivi.framework.crypto.enums.KeyStoreType;
import com.kivi.framework.crypto.service.RsaService;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.ByteStringKit;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RsaServiceImpl extends BaseAsymCryptoService<AsymCtx> implements RsaService {

    @Override
    public String ctx( AlgRSA cipherAlg, AlgSign signAlg, String pubKeyBase64, String priKeyBase64 ) {
        return ctx(cipherAlg, signAlg, new ByteArrayInputStream(pubKeyBase64.getBytes()),
                new ByteArrayInputStream(priKeyBase64.getBytes()));
    }

    @Override
    public String ctx( AlgRSA cipherAlg, AlgSign signAlg, InputStream pubStream, InputStream priStream ) {

        PrivateKey privateKey = getPrivateKey(priStream);
        PublicKey publicKey = getPublicKey(pubStream);

        String uuid = super.uuid(cipherAlg.getAlg() + signAlg.getAlg(),
                ((RSAPrivateKey) privateKey).getModulus().toByteArray(),
                ((RSAPublicKey) publicKey).getModulus().toByteArray());
        if (super.getCtx(uuid) != null)
            return uuid;

        AsymCtx ctx = AsymCtx.builder().uuid(uuid).signAlg(signAlg.getAlg()).cipherAlg(cipherAlg.getAlg())
                .keybits(((RSAPrivateKey) privateKey).getModulus().toString(2).length())
                .clientPublicKey(publicKey).serverPrivateKey(privateKey).build();

        super.putCtx(ctx.getUuid(), ctx);

        return ctx.getUuid();
    }

    @Override
    public String ctx( String clientCertBase64, String serverPfxHex, String pfxPass, KeyStoreType keyStoreType,
            String rootCertBase64, String crlBase64 ) {
        return ctx(new ByteArrayInputStream(clientCertBase64.getBytes()),
                new ByteArrayInputStream(ByteStringKit.toBytes(serverPfxHex, ByteStringKit.HEX)),
                pfxPass, keyStoreType,
                rootCertBase64 == null ? null : new ByteArrayInputStream(rootCertBase64.getBytes()),
                crlBase64 == null ? null : new ByteArrayInputStream(crlBase64.getBytes()));
    }

    @Override
    public String ctx( InputStream clientCertStream, InputStream serverPfxStream, String pfxPass,
            KeyStoreType keyStoreType, InputStream rootCertStream, InputStream crlStream ) {
        KeyStore ks = super.getKeyStore(serverPfxStream, pfxPass, keyStoreType);
        X509Certificate serverCert = super.getX509Cert(ks);
        PrivateKey privateKey = super.getPrivateKey(ks, pfxPass);

        X509Certificate clientCert = super.getX509Cert(clientCertStream);
        PublicKey publicKey = super.getPublicKey(clientCert);

        X509Certificate rootCert = super.getX509Cert(rootCertStream);
        X509CRL crl = super.getX509CRL(crlStream);

        String srvCertSeqno = super.saveCertExpireDate(serverCert);
        String cltCertSeqno = super.saveCertExpireDate(clientCert);

        String uuid = super.uuid(srvCertSeqno, cltCertSeqno.getBytes(), null);
        if (super.getCtx(uuid) != null)
            return uuid;

        AsymCtx ctx = AsymCtx.builder().uuid(uuid).signAlg(serverCert.getSigAlgName())
                .cipherAlg(AlgRSA.RSA_ECB_PKCS1.getAlg())
                .keybits(((RSAPrivateKey) privateKey).getModulus().toString(2).length())
                .clientPublicKey(publicKey).serverPrivateKey(privateKey).clientCert(clientCert).serverCert(serverCert)
                .rootCert(rootCert).crl(crl).build();

        super.putCtx(uuid, ctx);

        return ctx.getUuid();
    }

    @Override
    public String ctx( String certsId ) {
        throw new KtfException(KtfError.E_NOT_IMPLEMENT, "函数尚未实现");
    }

    @Override
    public byte[] encrypt( String ctxToken, byte[] data ) {
        byte[] result = null;

        try {
            AsymCtx ctx = super.getCtx(ctxToken);
            result = rsa_crypto(ctx, Cipher.ENCRYPT_MODE, data);
        }
        catch (Exception e) {
            log.error("RSA公钥加密异常", e);
            throw new KtfException(KtfError.E_CRYPTO, e);
        }
        return result;
    }

    @Override
    public byte[] decrypt( String ctxToken, byte[] data ) {
        byte[] result = null;

        try {
            AsymCtx ctx = super.getCtx(ctxToken);
            result = rsa_crypto(ctx, Cipher.DECRYPT_MODE, data);
        }
        catch (Exception e) {
            log.error("RSA私钥解密异常", e);
            throw new KtfException(KtfError.E_CRYPTO, e);
        }
        return result;
    }

    @Override
    public byte[] sign( String ctxToken, byte[] data ) {
        byte[] result = null;
        AsymCtx ctx = super.getCtx(ctxToken);
        try {
            // 校验签名证书
            super.checkCertValidity(ctx.getServerCert(), ctx.getRootCert(), ctx.getCrl());
            Signature signature = super.signature(ctx.getSignAlg());
            signature.initSign(ctx.getServerPrivateKey());
            signature.update(data);
            result = signature.sign();
        }
        catch (InvalidKeyException | SignatureException e) {
            log.error("RSA签名异常", e);
            throw new KtfException(KtfError.E_CRYPTO, e);
        }

        return result;
    }

    @Override
    public boolean verify( String ctxToken, byte[] data, byte[] sign ) {
        boolean result = false;
        AsymCtx ctx = super.getCtx(ctxToken);
        try {
            // 校验验签证书
            super.checkCertValidity(ctx.getClientCert(), ctx.getRootCert(), ctx.getCrl());

            Signature signature = super.signature(ctx.getSignAlg());
            if (ctx.getClientCert() != null)
                signature.initVerify(ctx.getClientCert());
            else
                signature.initVerify(ctx.getClientPublicKey());
            signature.update(data);
            result = signature.verify(sign);
        }
        catch (InvalidKeyException | SignatureException e) {
            log.error("RSA签名异常", e);
            throw new KtfException(KtfError.E_CRYPTO, e);
        }
        return result;
    }

    /**
     * RSA加密/解密
     * 
     * @param ctx
     * @param mode
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     * @throws NoSuchProviderException
     */
    private byte[] rsa_crypto( AsymCtx ctx, int mode, byte[] data )
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, IOException, NoSuchProviderException {

        int keyLegth = ctx.getKeybits() / 8; // 256 bytes
        int blockSize = keyLegth;
        if (Cipher.ENCRYPT_MODE == mode)
            blockSize -= ctx.getReserveBytes(); // 245 bytes
        // 计算分段加密的block数 (向上取整)
        int nBlock = (data.length / blockSize);
        if ((data.length % blockSize) != 0) { // 余数非0，block数再加1
            nBlock += 1;
        }

        try (ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * keyLegth)) {
            Cipher cipher = super.cipher(ctx.getCipherAlg());
            cipher.init(mode, Cipher.ENCRYPT_MODE == mode ? ctx.getClientPublicKey() : ctx.getServerPrivateKey());
            // 分段加密
            for (int offset = 0; offset < data.length; offset += blockSize) {
                // block大小: encryptBlock 或 剩余字节数
                int inputLen = (data.length - offset);
                if (inputLen > blockSize) {
                    inputLen = blockSize;
                }
                // 得到分段加密结果
                byte[] encryptedBlock = cipher.doFinal(data, offset, inputLen);
                // 追加结果到输出buffer中
                outbuf.write(encryptedBlock);
            }

            return outbuf.toByteArray(); // ciphertext
        }
        finally {

        }

    }

    /**
     * RSA公钥获取
     * 
     * @param in
     * @return
     */
    private PublicKey getPublicKey( InputStream in ) {
        PublicKey publicKey = null;
        String[] rsaKey = readKeyFile(in);
        byte[] rsaKeyData = ByteStringKit.toBytes(rsaKey[1], ByteStringKit.BASE64);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(rsaKeyData);
            publicKey = keyFactory.generatePublic(keySpec);
        }
        catch (Exception e) {
            log.error("读取公钥对象异常", e);
            throw new KtfException(KtfError.E_CRYPTO, e);
        }

        return publicKey;
    }

    /**
     * PrivateKey获取
     * 
     * @param base64PrivateKey
     * @return
     */
    private PrivateKey getPrivateKey( InputStream in ) {
        PrivateKey privateKey = null;
        String[] rsaKey = readKeyFile(in);
        byte[] rsaKeyData = ByteStringKit.toBytes(rsaKey[1], ByteStringKit.BASE64);

        try {
            if ("PKCS8".equals(rsaKey[0])) {
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(rsaKeyData);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                privateKey = keyFactory.generatePrivate(keySpec);
            }
            else {
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(rsaKeyData);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA",
                        new org.bouncycastle.jce.provider.BouncyCastleProvider());
                privateKey = keyFactory.generatePrivate(keySpec);
                return privateKey;
            }
        }
        catch (Exception e) {
            log.error("读取RSA私钥对象异常", e);
            throw new KtfException(KtfError.E_CRYPTO, "读取RSA私钥失败", e);
        }
        return privateKey;
    }

    /**
     * read RSA key file
     * 
     * @param in
     * @return
     */
    private String[] readKeyFile( InputStream in ) {
        String[] result = new String[2];

        String firstLine = null, readLine = null;
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    if (firstLine == null)
                        firstLine = readLine;
                    continue;
                }
                else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
        }
        catch (IOException e) {
            log.error("读取文件异常", e);
            throw new KtfException(KtfError.E_CRYPTO, e);
        }

        if (firstLine == null)
            result[0] = "PKCS8";
        else {
            result[0] = firstLine.indexOf("BEGIN RSA") != -1 ? "PKCS1" : "PKCS8";
        }

        result[1] = sb.toString();

        return result;
    }

    @Override
    public byte[] signWithNoHash( String ctxToken, byte[] data ) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] signWithId( String ctxToken, String withId, byte[] data ) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean verifyWithId( String ctxToken, String withId, byte[] data, byte[] sign ) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean verifyWithNoHash( String ctxToken, byte[] data, byte[] sign ) {
        // TODO Auto-generated method stub
        return false;
    }

}
