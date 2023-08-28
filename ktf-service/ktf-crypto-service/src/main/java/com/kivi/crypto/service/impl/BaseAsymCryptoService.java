package com.kivi.crypto.service.impl;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Cipher;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.cert.CertException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.kivi.crypto.service.AsymCryptoService;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.crypto.ctx.AsymCtx;
import com.kivi.framework.crypto.enums.KeyStoreType;
import com.kivi.framework.crypto.util.CertUtil;
import com.kivi.framework.crypto.util.CipherUtil;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.ByteStringKit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseAsymCryptoService<T> implements AsymCryptoService {

	protected static final Charset								CHARSET			= Charset.forName("UTF-8");

	protected ConcurrentHashMap<String, T>						ctxMap			= new ConcurrentHashMap<>();

	protected ConcurrentHashMap<String, Date>					certExpireMap	= new ConcurrentHashMap<>();

	protected ConcurrentHashMap<String, ThreadLocal<Signature>>	signatureMap	= new ConcurrentHashMap<>();
	protected ConcurrentHashMap<String, ThreadLocal<Cipher>>	cipherMap		= new ConcurrentHashMap<>();

	protected Signature signature(String signAlg) {
		Signature signature = CipherUtil.getSignatureInstance(signAlg);
		/*
		 * if (signatureMap.containsKey(signAlg)) { signature =
		 * signatureMap.get(signAlg).get(); } else { ThreadLocal<Signature> theadLocal =
		 * new ThreadLocal<Signature>() {
		 * 
		 * @Override protected Signature initialValue() { return
		 * CipherUtil.getSignatureInstance(signAlg); }
		 * 
		 * }; signatureMap.put(signAlg, theadLocal); signature = theadLocal.get(); }
		 */

		return signature;
	}

	protected Cipher cipher(String cipherAlg) {
		Cipher cipher = null;
		if (cipherMap.containsKey(cipherAlg)) {
			cipher = cipherMap.get(cipherAlg).get();
		} else {
			ThreadLocal<Cipher> theadLocal = new ThreadLocal<Cipher>() {
				@Override
				protected Cipher initialValue() {
					return CipherUtil.getCipherInstance(cipherAlg);
				}

			};
			cipherMap.put(cipherAlg, theadLocal);
			cipher = theadLocal.get();
		}

		return cipher;
	}

	protected String uuid(String algName, byte[] key, byte[] iv) {
		int			size	= 4 + algName.length() + key.length + (iv != null ? iv.length : 0);
		ByteBuffer	buff	= ByteBuffer.allocate(size);
		buff.put(algName.getBytes(Charset.forName("UTF-8"))).put(key);
		if (iv != null)
			buff.put(iv);

		return DigestUtils.md5Hex(buff.array());
	}

	protected void putCtx(String uuid, T ctx) {
		ctxMap.putIfAbsent(uuid, ctx);
	}

	protected T getCtx(String uuid) {
		return ctxMap.get(uuid);
	}

	@Override
	public AsymCtx asymCtx(String ctxToken) {
		return (AsymCtx) ctxMap.get(ctxToken);
	}

	@Override
	public void evictCtx(String ctxToken) {
		ctxMap.remove(ctxToken);
	}

	protected String saveCertExpireDate(X509Certificate cert) {
		String serialNo = this.getSerialNumber(cert);
		certExpireMap.putIfAbsent(serialNo, cert.getNotAfter());

		return serialNo;
	}

	/**
	 * zero padding
	 * 
	 * @param blockSize
	 * @param data
	 * @return
	 */
	protected byte[] zeroPadding(int blockSize, byte[] data) {
		int			padLen	= blockSize - data.length % blockSize;

		ByteBuffer	padData	= ByteBuffer.allocate(data.length + padLen);
		padData.put(data);

		return padData.array();
	}

	protected byte[] trimZeroPadding(int blockSize, byte[] data) {
		int padLen = 0;
		for (padLen = 0; padLen < blockSize; padLen++) {
			if (data[data.length - 1 - padLen] != 0x0)
				break;
		}

		int			size	= data.length - padLen;
		ByteBuffer	padData	= ByteBuffer.allocate(size);
		padData.put(data, 0, size);
		return padData.array();
	}

	/**
	 * RSA公钥获取
	 * 
	 * @param x509Cert
	 * @return
	 */
	protected PublicKey getPublicKey(X509Certificate x509Cert) {
		PublicKey pk = x509Cert.getPublicKey();
		return pk;
	}

	/**
	 * PrivateKey获取
	 * 
	 * @param pfxIn
	 * @param pfxPasswd
	 * @return
	 */
	protected PrivateKey getPrivateKey(KeyStore keyStore, String pfxPasswd) {
		PrivateKey prikey = null;

		try {
			char[]	keypwd	= pfxPasswd.toCharArray();	// 证书密码
			String	alias;
			alias	= keyStore.aliases().nextElement();
			prikey	= (PrivateKey) keyStore.getKey(alias, keypwd);
		} catch (Exception e) {
			log.error("读取RSA私钥对象异常", e);
			throw new KtfException(KtfError.E_CRYPTO, "读取RSA私钥失败", e);
		}

		return prikey;
	}

	/**
	 * x509证书读取
	 * 
	 * @param in x509证书输入流
	 * @return
	 * @throws CertificateException
	 */
	protected X509Certificate getX509Cert(InputStream in) {
		X509Certificate cert = null;
		if (in == null)
			return null;

		try {
			cert = CertUtil.readX509Cert(in);
		} catch (CertException e) {
			log.error("读取文件异常", e);
			throw new KtfException(KtfError.E_CRYPTO, e);
		}

		return cert;
	}

	protected KeyStore getKeyStore(InputStream pfxIn, String pfxPasswd, KeyStoreType keyStoreType) {
		try {
			KeyStore ks = CertUtil.readKeyStore(pfxIn, pfxPasswd, keyStoreType);
			return ks;
		} catch (CertException e) {
			log.error("读取文件异常", e);
			throw new KtfException(KtfError.E_CRYPTO, e);
		}
	}

	/**
	 * x509证书读取
	 * 
	 * @param ks KeyStore证书输入流
	 * @return
	 * @throws KeyStoreException
	 * @throws KtfException
	 */
	protected X509Certificate getX509Cert(KeyStore ks) {
		X509Certificate cert = null;
		try {
			cert = CertUtil.getCertificate(ks);
		} catch (CertException e) {
			log.error("读取文件异常", e);
			throw new KtfException(KtfError.E_CRYPTO, e);
		}

		return cert;

	}

	/**
	 * CRL列表读取
	 * 
	 * @param in CRL输入流
	 * @return
	 * @throws NoSuchProviderException
	 * @throws CertificateException
	 * @throws CRLException
	 * @throws Exception
	 */
	protected X509CRL getX509CRL(InputStream in) {
		X509CRL crl = null;

		if (in == null)
			return null;

		try {
			Security.addProvider(new BouncyCastleProvider()); // adding provider
																// to
			CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
			crl = (X509CRL) cf.generateCRL(in);

		} catch (CRLException | NoSuchProviderException | CertificateException e) {
			log.error("读取文件异常", e);
			throw new KtfException(KtfError.E_CRYPTO, e);
		}
		return crl;
	}

	/**
	 * 验证客户端证书有效性
	 * 
	 * @param x509Cert
	 * @return
	 */
	protected boolean checkCertValidity(X509Certificate cert, X509Certificate caCert, X509CRL crl) {
		boolean result = false;
		if (caCert == null) {
			log.debug("未配置跟证书，默认不验证证书的签名");
			return true;
		}

		try {
			log.info("校验根证书{}的有效期", caCert.getSubjectDN());
			caCert.checkValidity();
			log.info("校验根证书{}的有效期结果：OK", caCert.getSubjectDN());

			log.info("校验证书{}的有效期", cert.getSubjectDN().getName());
			cert.checkValidity();
			log.info("校验证书{}的有效期结果：OK", cert.getSubjectDN());

			log.info("校验证书{}的签名", cert.getSubjectDN().getName());
			cert.verify(caCert.getPublicKey());
			log.info("校验证书{}的签名结果：OK", cert.getSubjectDN());

			log.info("检查CRL列表");
			result = checkIsInCRL(cert, crl);
			log.info("检查CRL列表结果：{}", result);
			if (!result)
				throw new KtfException(KtfError.E_CRYPTO, "证书已被吊销");
		} catch (CertificateExpiredException e) {
			log.error("证书已过期", e);
			throw new KtfException(KtfError.E_CRYPTO, "证书已过期");
		} catch (CertificateNotYetValidException e) {
			log.error("证书未生效", e);
			throw new KtfException(KtfError.E_CRYPTO, "证书未生效");
		} catch (SignatureException e) {
			log.error("证书签名无效", e);
			throw new KtfException(KtfError.E_CRYPTO, "证书签名无效");
		} catch (InvalidKeyException | CertificateException | NoSuchAlgorithmException | NoSuchProviderException e) {
			log.error("证书校验异常", e);
			throw new KtfException(KtfError.E_CRYPTO, "证书校验异常");
		}

		return result;
	}

	private boolean checkIsInCRL(X509Certificate x509Cert, X509CRL crl) {
		if (crl == null) {
			log.warn("CRL列表为null，不校验CRL直接返回true");
			return true;
		}
		// String certSN = getSerialNumber(x509Cert).toUpperCase();
		// log.info("证书序列号={}", certSN);
		// log.info("证书DN={}", x509Cert.getSubjectDN());

		if (crl.isRevoked(x509Cert)) {
			log.info("证书{}被吊销", x509Cert.getSubjectDN());
			return true;
		}

		log.info("证书{}可用", x509Cert.getSubjectDN());
		return false;
	}

	/**
	 * 读取证书序列号
	 * 
	 * @param cert
	 * @return
	 */
	protected String getSerialNumber(X509Certificate cert) {
		if (null == cert)
			return null;
		byte[] serial = cert.getSerialNumber().toByteArray();
		if (serial.length > 0) {
			String serialNumberString = new String();
			for (int i = 0; i < serial.length; i++) {
				String s = Integer.toHexString(Byte.valueOf(serial[i]).intValue());
				if (s.length() == 8)
					s = s.substring(6);
				else if (1 == s.length())
					s = "0" + s;
				serialNumberString += s + " ";
			}
			return serialNumberString;
		}
		return null;
	}

	@Override
	public String encryptToBase64(String ctxToken, byte[] data) {
		byte[] encData = encrypt(ctxToken, data);

		return ByteStringKit.toString(encData, ByteStringKit.BASE64);
	}

	@Override
	public byte[] decryptFromBase64(String ctxToken, String dataBase64) {
		byte[] plain = decrypt(ctxToken, ByteStringKit.toBytes(dataBase64, ByteStringKit.BASE64));

		return plain;
	}

	@Override
	public String encryptToHex(String ctxToken, byte[] data) {
		byte[] encData = encrypt(ctxToken, data);
		return ByteStringKit.toString(encData, ByteStringKit.HEX);
	}

	@Override
	public byte[] decryptFromHex(String ctxToken, String data) {
		byte[] plain = decrypt(ctxToken, ByteStringKit.toBytes(data, ByteStringKit.HEX));
		return plain;
	}

	@Override
	public String signToBase64(String ctxToken, byte[] data) {
		byte[] sign = sign(ctxToken, data);
		return ByteStringKit.toString(sign, ByteStringKit.BASE64);
	}

	@Override
	public String signToHex(String ctxToken, byte[] data) {
		byte[] sign = sign(ctxToken, data);
		return ByteStringKit.toString(sign, ByteStringKit.HEX);
	}

	@Override
	public boolean verifyFromBase64(String ctxToken, byte[] data, String signBase64) {
		byte[] sign = ByteStringKit.toBytes(signBase64, ByteStringKit.BASE64);
		return verify(ctxToken, data, sign);
	}

	@Override
	public boolean verifyFromHex(String ctxToken, byte[] data, String signHex) {
		byte[] sign = ByteStringKit.toBytes(signHex, ByteStringKit.HEX);
		return verify(ctxToken, data, sign);
	}

	@Override
	public Date getCertExpireDate(String certSeqNo) {
		return this.certExpireMap.get(certSeqNo);
	}

	@Override
	public abstract byte[] encrypt(String ctxToken, byte[] data);

	@Override
	public abstract byte[] decrypt(String ctxToken, byte[] data);

	@Override
	public abstract byte[] sign(String ctxToken, byte[] data);

	@Override
	public abstract boolean verify(String ctxToken, byte[] data, byte[] sign);

}
