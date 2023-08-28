package com.kivi.framework.crypto.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.CertException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import com.kivi.framework.constant.KtfError;
import com.kivi.framework.crypto.enums.KeyStoreType;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.StrKit;
import com.vip.vjtools.vjkit.io.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CertUtil {
	public static final String	KEYSTORE_TYPE_P12	= "PKCS12";
	public static final String	KEYSTORE_TYPE_JKS	= "JKS";
	static {
		Security.addProvider(ProviderInstance.getBCProvider());
	}

	/**
	 * 根据文件名称判断秘钥类型
	 * 
	 * @param filePath
	 * @return
	 */
	public static KeyStoreType guessKeystoreType(String filePath) {
		String suffix = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
		if (suffix.equals("jks") || suffix.equals("keystore")) {
			return KeyStoreType.JKS;
		} else if (suffix.equals("p12")) {
			return KeyStoreType.PKCS12;
		}
		return KeyStoreType.PKCS12;
	}

	public static KeyStore readKeyStore(File pfxFile, String keyPassword) throws CertException {
		try (FileInputStream fin = new FileInputStream(pfxFile)) {
			return readKeyStore(fin, keyPassword, guessKeystoreType(pfxFile.getName()));
		} catch (Exception e) {
			throw new CertException("读取key store异常", e);
		}
	}

	public static KeyStore readKeyStore(InputStream in, String keyPassword, KeyStoreType type) throws CertException {
		try {
			KeyStore keyStore = KeyStore.getInstance(type.name(), BouncyCastleProvider.PROVIDER_NAME);
			keyStore.load(in, keyPassword.toCharArray());
			return keyStore;
		} catch (Exception e) {
			throw new CertException("读取key store异常", e);
		}
	}

	/**
	 * 读取x509 证书，支持文件类型：cer,pem,p12,pfx,JKS
	 * 
	 * @param certFilePath 证书文件类型
	 * @param keyPassword  类型为pfx,JKS时使用
	 * @return
	 * @throws CertException
	 */
	public static X509Certificate readX509Cert(String certFilePath, String... keyPassword) throws CertException {
		String fileExt = FileUtil.getFileExtension(certFilePath);
		if (StrKit.isBlank(fileExt)) {
			throw new CertException("读取证书文件后缀失败");
		}
		// x509
		X509Certificate cert = null;
		try (InputStream in = FileUtil.asInputStream(certFilePath)) {
			if (StrKit.equals("cer", fileExt)) {
				// 创建x.509工厂类
				CertificateFactory cf = CertificateFactory.getInstance("X.509");
				// 创建证书实例
				cert = (X509Certificate) cf.generateCertificate(in);
			} else if (StrKit.equals("pem", fileExt)) {
				cert = readX509Cert(in);
			} else if (StringUtils.containsAny(StringUtils.lowerCase(fileExt), "p12", "pfx", "jks")) {
				if (keyPassword == null || StrKit.isBlank(keyPassword[0]))
					throw new CertException("私钥文件密码为空");
				KeyStore ks = readKeyStore(new File(certFilePath), keyPassword[0]);
				cert = getCertificate(ks);
			}

		} catch (IOException | CertificateException e) {
			throw new CertException("读取x509证书失败", e);
		}

		return cert;
	}

	/**
	 * 读取x509 证书
	 * 
	 * @param pemPath
	 * @return
	 */
	public static X509Certificate readX509Cert(InputStream is) throws CertException {

		try (PEMParser pemParser = new PEMParser(new InputStreamReader(is))) {
			Object readObject = pemParser.readObject();
			if (readObject instanceof X509CertificateHolder) {
				X509CertificateHolder holder = (X509CertificateHolder) readObject;
				return new JcaX509CertificateConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME)
						.getCertificate(holder);
			}
			throw new CertException("读取x509证书失败");
		} catch (Exception e) {
			throw new CertException("读取x509证书失败", e);
		}
	}

	public static X509Certificate getCertificate(KeyStore keyStore) throws CertException {
		X509Certificate result = null;
		try {
			Enumeration<String> aliases = keyStore.aliases();
			while (aliases.hasMoreElements()) {
				String alias = aliases.nextElement();
				log.trace("keyStore's alias:{}", alias);
				Certificate certificate = keyStore.getCertificate(alias);
				if (certificate != null && certificate.getType().equals("X.509")) {
					result = (X509Certificate) certificate;
					break;
				}
			}

		} catch (Exception e) {
			throw new CertException("读取证书异常", e);
		}

		return result;
	}

	public static PublicKey getPublicKey(KeyStore keyStore) throws CertException {
		PublicKey result = null;
		try {
			Enumeration<String> aliases = keyStore.aliases();
			while (aliases.hasMoreElements()) {
				String alias = aliases.nextElement();
				log.trace("keyStore's alias:{}", alias);
				Certificate certificate = keyStore.getCertificate(alias);
				if (certificate != null) {
					result = certificate.getPublicKey();
					break;
				}
			}
		} catch (Exception e) {
			throw new CertException("读取公钥异常", e);
		}

		return result;
	}

	public static PublicKey getPublicKey(KeyStore keyStore, String alias) throws CertException {
		try {
			Certificate certificate = keyStore.getCertificate(alias);
			if (certificate == null) {
				throw new CertException(alias + " alias not found");
			}
			return certificate.getPublicKey();
		} catch (Exception e) {
			throw new CertException("读取公钥异常", e);
		}
	}

	public static PrivateKey getPrivateKey(KeyStore keyStore, String keyPassword) throws KeyStoreException {
		Enumeration<String> aliases = keyStore.aliases();
		while (aliases.hasMoreElements()) {
			String alias = aliases.nextElement();
			try {
				PrivateKey privateKey = getPrivateKey(keyStore, keyPassword, alias);
				if (privateKey != null)
					return privateKey;
			} catch (CertException e) {
			}
		}
		return null;
	}

	public static PrivateKey getPrivateKey(KeyStore keyStore, String keyPassword, String alias) throws CertException {
		try {
			if (!keyStore.isKeyEntry(alias)) {
				throw new CertException(alias + " alias not found");
			}
			return (PrivateKey) keyStore.getKey(alias, keyPassword.toCharArray());
		} catch (Exception e) {
			throw new CertException("analyze KeyStore failed", e);
		}
	}

	public final static String convertPublicKeyToPemString(PublicKey pub) {
		try {
			if (null == pub) {
				return null;
			}
			ByteArrayOutputStream	out				= new ByteArrayOutputStream(1024);
			OutputStreamWriter		outWriter		= new OutputStreamWriter(out);
			JcaPEMWriter			jcaPEMWriter	= new JcaPEMWriter(outWriter);
			jcaPEMWriter.writeObject(pub);
			jcaPEMWriter.close();
			return new String(out.toByteArray());
		} catch (Exception e) {
			return null;
		}
	}

	public final static PrivateKey convertPemToPrivateKey(InputStream in) {
		PrivateKey privateKey = null;
		try (PEMParser pemParser = new PEMParser(new InputStreamReader(in))) {
			Object readObject = pemParser.readObject();
			if (readObject instanceof PEMKeyPair) {
				PEMKeyPair key = (PEMKeyPair) readObject;
				privateKey = new JcaPEMKeyConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME).getKeyPair(key)
						.getPrivate();
			}

		} catch (Exception e) {
			log.error("pem格式私钥转化为PrivateKey失败");
			throw new KtfException(KtfError.E_CRYPTO, "pem格式私钥转化为PrivateKey失败", e);
		}

		return privateKey;
	}

	public final static PublicKey convertPemToPublicKey(InputStream in) {
		PublicKey publicKey = null;

		try (PEMParser pemParser = new PEMParser(new InputStreamReader(in))) {
			Object readObject = pemParser.readObject();
			if (readObject instanceof SubjectPublicKeyInfo) {
				SubjectPublicKeyInfo subjectPublicKeyInfo = (SubjectPublicKeyInfo) readObject;
				publicKey = new JcaPEMKeyConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME)
						.getPublicKey(subjectPublicKeyInfo);
			}

		} catch (Exception e) {
			log.error("PEM格式公钥转换为PublicKey失败");
			throw new KtfException(KtfError.E_CRYPTO, "PEM格式公钥转换为PublicKey失败", e);
		}

		return publicKey;
	}

	public final static String convertPrivateKeyToPemString(PrivateKey pri) {
		try {
			if (null == pri) {
				return null;
			}
			ByteArrayOutputStream	out				= new ByteArrayOutputStream(1024);
			OutputStreamWriter		outWriter		= new OutputStreamWriter(out);
			JcaPEMWriter			jcaPEMWriter	= new JcaPEMWriter(outWriter);
			jcaPEMWriter.writeObject(pri);
			jcaPEMWriter.close();
			return new String(out.toByteArray());
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] convertPemToDerEcData(String pemString) {
		ByteArrayInputStream bIn = new ByteArrayInputStream(pemString.getBytes());
		try (PemReader pRdr = new PemReader(new InputStreamReader(bIn))) {
			PemObject pemObject = pRdr.readPemObject();
			return pemObject.getContent();
		} catch (IOException e) {
			log.error("PEM格式转换为DER异常", e);
		}
		return null;
	}

}
