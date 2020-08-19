package com.kivi.framework.crypto.sm2;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jcajce.spec.SM2ParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.util.encoders.Hex;

import com.kivi.framework.constant.KtfError;
import com.kivi.framework.crypto.domain.KeyPairResult;
import com.kivi.framework.crypto.enums.AlgSign;
import com.kivi.framework.crypto.util.BCECUtil;
import com.kivi.framework.crypto.util.CertUtil;
import com.kivi.framework.crypto.util.ProviderInstance;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.StrKit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Sm2Util {
	static {
		if (Security.getProvider("BC") == null) {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		}
	}

	/**
	 * 生成SM2密钥对
	 * 
	 * @return
	 */
	public static KeyPairResult generateKeyPair() {
		KeyPairResult	keyPairResult	= new KeyPairResult();
		Sm2KeyPair		sm2KeyPair		= new Sm2KeyPairImpl();
		keyPairResult.setPri(sm2KeyPair.getPrivate());
		keyPairResult.setPub(sm2KeyPair.getPublic());
		return keyPairResult;
	}

	/**
	 * 从pfx、p12文件中读取
	 * 
	 * @return
	 */
	public static KeyPairResult getKeyPair(File pfxFile, String password) {
		try {
			KeyStore		ks				= CertUtil.readKeyStore(pfxFile, password);
			PublicKey		pubKey			= CertUtil.getPublicKey(ks);
			PrivateKey		priKey			= CertUtil.getPrivateKey(ks, password);

			KeyPairResult	keyPairResult	= new KeyPairResult();
			keyPairResult.setPri(priKey);
			keyPairResult.setPub(pubKey);
			return keyPairResult;

		} catch (Exception e) {
			throw new KtfException(KtfError.E_CRYPTO, "SM2 pfx文件读取异常", e);
		}
	}

	public static Sm2PublicKey sm2PublicKeyXY(String pubKeyXY) {
		if (pubKeyXY.length() != 128 && pubKeyXY.length() != 130)
			throw new KtfException(KtfError.E_CRYPTO, "SM2公钥数据长度不正确");
		int			pos		= pubKeyXY.length() == 128 ? 0 : 2;

		String		hexX	= StringUtils.substring(pubKeyXY, pos, 64);
		String		hexY	= StringUtils.substring(pubKeyXY, pos + 64);

		BigInteger	x		= new BigInteger(hexX, 16);
		BigInteger	y		= new BigInteger(hexY, 16);

		return sm2PublicKey(x, y);
	}

	public static Sm2PublicKey sm2PublicKey(BigInteger x, BigInteger y) {
		ECPublicKeySpec	ecPublicKeySpec	= new ECPublicKeySpec(Sm2KeyPair.x9ECParameters.getCurve().createPoint(x, y),
				Sm2KeyPair.ecParameterSpec);
		BCECPublicKey	publicKey		= new BCECPublicKey("EC", ecPublicKeySpec, BouncyCastleProvider.CONFIGURATION);

		return new Sm2PublicKeyImpl(publicKey);
	}

	public static Sm2PublicKey sm2PublicKey(File file) {
		try (FileInputStream in = new FileInputStream(file)) {
			CertificateFactory	cf		= CertificateFactory.getInstance("X.509", ProviderInstance.getBCProvider());

			X509Certificate		x509	= (X509Certificate) cf.generateCertificate(in);

			return new Sm2PublicKeyImpl((BCECPublicKey) x509.getPublicKey());
		} catch (Exception e) {
			log.warn("x509方式读取失败，尝试使用pem格式读完", e);
		}

		//
		try (FileInputStream in = new FileInputStream(file)) {
			return sm2PublicKey(in);
		} catch (Exception e) {
			throw new KtfException(KtfError.E_CRYPTO, "SM2公钥读取异常", e);
		}
	}

	public static Sm2PublicKey sm2PublicKeyPem(String pemPubKey) {
		return sm2PublicKey(new ByteArrayInputStream(StrKit.toBytes(pemPubKey)));
	}

	public static Sm2PublicKey sm2PublicKey(InputStream in) {
		try (PEMParser pemParser = new PEMParser(new InputStreamReader(in))) {
			Object readObject = pemParser.readObject();
			if (readObject instanceof SubjectPublicKeyInfo) {
				SubjectPublicKeyInfo	subjectPublicKeyInfo	= (SubjectPublicKeyInfo) readObject;
				PublicKey				publicKey				= new JcaPEMKeyConverter()
						.setProvider(BouncyCastleProvider.PROVIDER_NAME).getPublicKey(subjectPublicKeyInfo);
				return new Sm2PublicKeyImpl((BCECPublicKey) publicKey);
			}
			throw new KtfException(KtfError.E_CRYPTO, "SM2公钥读取异常");
		} catch (Exception e) {
			throw new KtfException(KtfError.E_CRYPTO, "SM2公钥读取异常", e);
		}
	}

	public static Sm2PrivateKey sm2PrivateKeyD(String hexD) {
		BigInteger d = new BigInteger(hexD, 16);
		return sm2PrivateKey(d);
	}

	public static Sm2PrivateKey sm2PrivateKey(BigInteger d) {
		ECPrivateKeySpec	ecPrivateKeySpec	= new ECPrivateKeySpec(d, Sm2KeyPair.ecParameterSpec);
		BCECPrivateKey		privateKey			= new BCECPrivateKey("EC", ecPrivateKeySpec,
				BouncyCastleProvider.CONFIGURATION);

		return new Sm2PrivateKeyImpl(privateKey);
	}

	public static Sm2PrivateKey sm2PrivateKeyPem(String pemPriKey) {
		return sm2PrivateKey(new ByteArrayInputStream(StrKit.toBytes(pemPriKey)));
	}

	public static Sm2PrivateKey sm2PrivateKey(InputStream in) {
		try (PEMParser pemParser = new PEMParser(new InputStreamReader(in))) {
			Object readObject = pemParser.readObject();
			if (readObject instanceof PEMKeyPair) {
				PEMKeyPair	key			= (PEMKeyPair) readObject;
				PrivateKey	privateKey	= new JcaPEMKeyConverter().setProvider(ProviderInstance.getBCProvider())
						.getKeyPair(key).getPrivate();
				return new Sm2PrivateKeyImpl((BCECPrivateKey) privateKey);
			}
			throw new KtfException(KtfError.E_CRYPTO, "SM2私钥读取异常");
		} catch (Exception e) {
			log.error("SM2私钥读取异常", e);
			throw new KtfException(KtfError.E_CRYPTO, "SM2私钥读取异常", e);
		}

	}

	/**
	 * c1||c3||c2
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	public static byte[] sm2Decrypt(byte[] data, PrivateKey key) {
		byte[] xdata = null;
		if (data.length == 128) {
			xdata		= new byte[129];
			xdata[0]	= 0x04;
			System.arraycopy(data, 0, xdata, 1, data.length);
		} else
			xdata = data;
		return sm2DecryptOld(changeC1C3C2ToC1C2C3(xdata), key);
	}

	/**
	 * c1||c3||c2
	 * 
	 * @param data
	 * @param key
	 * @return
	 */

	public static byte[] sm2Encrypt(byte[] data, PublicKey key) {
		return changeC1C2C3ToC1C3C2(sm2EncryptOld(data, key));
	}

	/**
	 * c1||c2||c3
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	public static byte[] sm2EncryptOld(byte[] data, PublicKey key) {
		BCECPublicKey			localECPublicKey		= (BCECPublicKey) key;
		ECPublicKeyParameters	ecPublicKeyParameters	= new ECPublicKeyParameters(localECPublicKey.getQ(),
				Sm2KeyPair.ecDomainParameters);
		SM2Engine				sm2Engine				= new SM2Engine();
		sm2Engine.init(true, new ParametersWithRandom(ecPublicKeyParameters, new SecureRandom()));
		try {
			return sm2Engine.processBlock(data, 0, data.length);
		} catch (InvalidCipherTextException e) {
			throw new KtfException(KtfError.E_CRYPTO, "SM2加密异常", e);
		}
	}

	/**
	 * c1||c2||c3
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	public static byte[] sm2DecryptOld(byte[] data, PrivateKey key) {
		BCECPrivateKey			localECPrivateKey		= (BCECPrivateKey) key;
		ECPrivateKeyParameters	ecPrivateKeyParameters	= new ECPrivateKeyParameters(localECPrivateKey.getD(),
				Sm2KeyPair.ecDomainParameters);
		SM2Engine				sm2Engine				= new SM2Engine();
		sm2Engine.init(false, ecPrivateKeyParameters);
		try {
			return sm2Engine.processBlock(data, 0, data.length);
		} catch (InvalidCipherTextException e) {
			throw new KtfException(KtfError.E_CRYPTO, "SM2解密异常", e);
		}
	}

	/**
	 * 对原始数据SM3摘要后签名，默认withId：1234567812345678
	 * 
	 * @param msg
	 * @param privateKey
	 * @return r||s，直接拼接byte数组的rs
	 */
	public static byte[] signSm3WithSm2(byte[] msg, Sm2PrivateKey privateKey) {
		return rsAsn1ToPlainByteArray(signSm3WithSm2Asn1Rs(msg, privateKey, AlgSign.SM2_SM3));
	}

	/**
	 * 对原始数据不做摘直接后签名
	 * 
	 * @param msg
	 * @param privateKey
	 * @return r||s，直接拼接byte数组的rs
	 */
	public static byte[] signWithNoHash(byte[] msg, Sm2PrivateKey privateKey) {
		try {
			SM2Signer			signer	= new SM2Signer();
			SM2Signer.Signature	sig		= signer.signNoHash(msg, privateKey.getD());
			return sig.toBytes();
		} catch (Exception e) {
			throw new KtfException(KtfError.E_CRYPTO, "SM2签名异常", e);
		}
	}

	/**
	 * 对原始数据摘要后签名，默认withId：1234567812345678
	 * 
	 * @param msg
	 * @param privateKey
	 * @return rs in <b>asn1 format</b>
	 */
	public static byte[] signSm3WithSm2Asn1Rs(byte[] msg, Sm2PrivateKey privateKey, AlgSign alg) {
		try {
			SM2ParameterSpec parameterSpec = null;
			if (privateKey.getWithId() != null)
				parameterSpec = new SM2ParameterSpec(privateKey.getWithId());

			Signature signer = Signature.getInstance(alg.getAlg(), ProviderInstance.getBCProvider());
			if (parameterSpec != null)
				signer.setParameter(parameterSpec);
			signer.initSign(privateKey, new SecureRandom());
			signer.update(msg, 0, msg.length);
			byte[] sig = signer.sign();
			return sig;
		} catch (Exception e) {
			throw new KtfException(KtfError.E_CRYPTO, "SM2签名异常", e);
		}
	}

	/**
	 *
	 * @param msg
	 * @param userId
	 * @param rs        r||s，直接拼接byte数组的rs
	 * @param publicKey
	 * @return
	 */
	public static boolean verifySm3WithSm2(byte[] msg, byte[] rs, Sm2PublicKey publicKey) {
		return verifySm3WithSm2Asn1Rs(msg, rsPlainByteArrayToAsn1(rs), publicKey, AlgSign.SM2_SM3);
	}

	/**
	 *
	 * @param msg
	 * @param userId
	 * @param rs        in <b>asn1 format</b>
	 * @param publicKey
	 * @return
	 */
	public static boolean verifySm3WithSm2Asn1Rs(byte[] msg, byte[] rs, Sm2PublicKey publicKey, AlgSign alg) {
		try {
			SM2ParameterSpec parameterSpec = null;
			if (publicKey.getWithId() != null)
				parameterSpec = new SM2ParameterSpec(publicKey.getWithId());

			Signature verifier = Signature.getInstance(alg.getAlg(), ProviderInstance.getBCProvider());
			if (parameterSpec != null)
				verifier.setParameter(parameterSpec);
			verifier.initVerify(publicKey);
			verifier.update(msg, 0, msg.length);
			return verifier.verify(rs);
		} catch (Exception e) {
			throw new KtfException(KtfError.E_CRYPTO, "SM2验签异常", e);
		}
	}

	public static boolean verifyWithNoHash(byte[] msg, byte[] rs, Sm2PublicKey publicKey) {
		try {
			SM2Signer signer = new SM2Signer();
			return signer.verifyNoHash(msg, new SM2Signer.Signature(rs), publicKey.getQ());
		} catch (Exception e) {
			throw new KtfException(KtfError.E_CRYPTO, "SM2验签异常", e);
		}
	}

	/**
	 * 分解SM2密文
	 *
	 * @param cipherText SM2密文
	 * @return
	 */
	public static Sm2Result parseSm2CipherText(byte[] cipherText) {
		int curveLength = BCECUtil.getCurveLength(Sm2KeyPair.DOMAIN_PARAMS);
		return parseSm2CipherText(curveLength, Sm2KeyPair.SM3_DIGEST_LENGTH, cipherText);
	}

	/**
	 * 分解SM2密文
	 *
	 * @param curveLength  ECC曲线长度
	 * @param digestLength HASH长度
	 * @param cipherText   SM2密文
	 * @return
	 */
	public static Sm2Result parseSm2CipherText(int curveLength, int digestLength, byte[] cipherText) {
		byte[] c1 = new byte[curveLength * 2 + 1];
		System.arraycopy(cipherText, 0, c1, 0, c1.length);
		byte[] c2 = new byte[cipherText.length - c1.length - digestLength];
		System.arraycopy(cipherText, c1.length, c2, 0, c2.length);
		byte[] c3 = new byte[digestLength];
		System.arraycopy(cipherText, c1.length + c2.length, c3, 0, c2.length);
		Sm2Result result = new Sm2Result();
		result.setC1(c1);
		result.setC2(c2);
		result.setC3(c3);
		result.setCipherText(cipherText);
		return result;
	}

	/**
	 * bc加解密使用旧标c1||c2||c3，此方法在加密后调用，将结果转化为c1||c3||c2
	 * 
	 * @param c1c2c3
	 * @return
	 */
	private static byte[] changeC1C2C3ToC1C3C2(byte[] c1c2c3) {
		final int	c1Len	= (Sm2KeyPair.x9ECParameters.getCurve().getFieldSize() + 7) / 8 * 2 + 1;	// sm2p256v1的这个固定65。可看GMNamedCurves、ECCurve代码。
		final int	c3Len	= 32;																		// new
																										// SM3Digest().getDigestSize();
		byte[]		result	= new byte[c1c2c3.length];
		System.arraycopy(c1c2c3, 0, result, 0, c1Len); // c1
		System.arraycopy(c1c2c3, c1c2c3.length - c3Len, result, c1Len, c3Len); // c3
		System.arraycopy(c1c2c3, c1Len, result, c1Len + c3Len, c1c2c3.length - c1Len - c3Len); // c2
		return result;
	}

	/**
	 * bc加解密使用旧标c1||c3||c2，此方法在解密前调用，将密文转化为c1||c2||c3再去解密
	 * 
	 * @param c1c3c2
	 * @return
	 */
	private static byte[] changeC1C3C2ToC1C2C3(byte[] c1c3c2) {
		final int	c1Len	= (Sm2KeyPair.x9ECParameters.getCurve().getFieldSize() + 7) / 8 * 2 + 1;	// sm2p256v1的这个固定65。可看GMNamedCurves、ECCurve代码。
		final int	c3Len	= 32;																		// new
																										// SM3Digest().getDigestSize();
		byte[]		result	= new byte[c1c3c2.length];
		System.arraycopy(c1c3c2, 0, result, 0, c1Len); // c1: 0->65
		System.arraycopy(c1c3c2, c1Len + c3Len, result, c1Len, c1c3c2.length - c1Len - c3Len); // c2
		System.arraycopy(c1c3c2, c1Len, result, c1c3c2.length - c3Len, c3Len); // c3
		return result;
	}

	private final static int RS_LEN = 32;

	private static byte[] bigIntToFixexLengthBytes(BigInteger rOrS) {
		// for sm2p256v1, n is
		// 00fffffffeffffffffffffffffffffffff7203df6b21c6052b53bbf40939d54123,
		// r and s are the result of mod n, so they should be less than n and have
		// length<=32
		byte[] rs = rOrS.toByteArray();
		if (rs.length == RS_LEN)
			return rs;
		else if (rs.length == RS_LEN + 1 && rs[0] == 0)
			return Arrays.copyOfRange(rs, 1, RS_LEN + 1);
		else if (rs.length < RS_LEN) {
			byte[] result = new byte[RS_LEN];
			Arrays.fill(result, (byte) 0);
			System.arraycopy(rs, 0, result, RS_LEN - rs.length, rs.length);
			return result;
		} else {
			throw new RuntimeException("err rs: " + Hex.toHexString(rs));
		}
	}

	/**
	 * BC的SM3withSM2签名得到的结果的rs是asn1格式的，这个方法转化成直接拼接r||s
	 * 
	 * @param rsDer rs in asn1 format
	 * @return sign result in plain byte array
	 */
	private static byte[] rsAsn1ToPlainByteArray(byte[] rsDer) {
		ASN1Sequence	seq		= ASN1Sequence.getInstance(rsDer);
		byte[]			r		= bigIntToFixexLengthBytes(ASN1Integer.getInstance(seq.getObjectAt(0)).getValue());
		byte[]			s		= bigIntToFixexLengthBytes(ASN1Integer.getInstance(seq.getObjectAt(1)).getValue());
		byte[]			result	= new byte[RS_LEN * 2];
		System.arraycopy(r, 0, result, 0, r.length);
		System.arraycopy(s, 0, result, RS_LEN, s.length);
		return result;
	}

	/**
	 * BC的SM3withSM2验签需要的rs是asn1格式的，这个方法将直接拼接r||s的字节数组转化成asn1格式
	 * 
	 * @param sign in plain byte array
	 * @return rs result in asn1 format
	 */
	private static byte[] rsPlainByteArrayToAsn1(byte[] sign) {
		if (sign.length != RS_LEN * 2)
			throw new RuntimeException("err rs. ");
		BigInteger			r	= new BigInteger(1, Arrays.copyOfRange(sign, 0, RS_LEN));
		BigInteger			s	= new BigInteger(1, Arrays.copyOfRange(sign, RS_LEN, RS_LEN * 2));
		ASN1EncodableVector	v	= new ASN1EncodableVector();
		v.add(new ASN1Integer(r));
		v.add(new ASN1Integer(s));
		try {
			return new DERSequence(v).getEncoded("DER");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
