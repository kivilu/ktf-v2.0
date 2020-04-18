package com.kivi.framework.crypto.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.cert.CertException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;

import com.kivi.framework.crypto.enums.KeyStoreType;
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
	public static String guessKeystoreType(String filePath) {
		String suffix = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
		if (suffix.equals("jks") || suffix.equals("keystore")) {
			return KEYSTORE_TYPE_JKS;
		} else if (suffix.equals("p12")) {
			return KEYSTORE_TYPE_P12;
		}
		return KEYSTORE_TYPE_P12;
	}

	public static KeyStore readKeyStore(String filePath, String keyPassword) throws CertException {
		try (FileInputStream file = new FileInputStream(new File(filePath))) {
			KeyStore keyStore = KeyStore.getInstance(guessKeystoreType(filePath), BouncyCastleProvider.PROVIDER_NAME);
			keyStore.load(file, keyPassword.toCharArray());
			return keyStore;
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
				KeyStore ks = readKeyStore(certFilePath, keyPassword[0]);
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

}
