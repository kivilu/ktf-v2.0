package com.kivi.crypto.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.bc.BcPEMDecryptorProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.stereotype.Service;

import com.kivi.crypto.service.SM2Service;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.crypto.ctx.AsymCtx;
import com.kivi.framework.crypto.domain.KeyPairDO;
import com.kivi.framework.crypto.domain.KeyPairResult;
import com.kivi.framework.crypto.enums.AlgSM2;
import com.kivi.framework.crypto.enums.AlgSign;
import com.kivi.framework.crypto.enums.KeyStoreType;
import com.kivi.framework.crypto.enums.KeyType;
import com.kivi.framework.crypto.sm2.Sm2PrivateKey;
import com.kivi.framework.crypto.sm2.Sm2PrivateKeyImpl;
import com.kivi.framework.crypto.sm2.Sm2PublicKey;
import com.kivi.framework.crypto.sm2.Sm2PublicKeyImpl;
import com.kivi.framework.crypto.sm2.Sm2Util;
import com.kivi.framework.crypto.util.CertUtil;
import com.kivi.framework.crypto.util.KeyPairUtil;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.ByteStringKit;
import com.kivi.framework.util.kit.StrKit;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SM2ServiceImpl extends BaseAsymCryptoService<AsymCtx> implements SM2Service {
	@Override
	public String ctx(AlgSM2 cipherAlg, AlgSign signAlg, String clientPubKeyBase64, String serverPriKeyBase64) {
		return ctx(cipherAlg, signAlg,
				clientPubKeyBase64 == null ? null : new ByteArrayInputStream(clientPubKeyBase64.getBytes()),
				serverPriKeyBase64 == null ? null : new ByteArrayInputStream(serverPriKeyBase64.getBytes()));
	}

	@Override
	public String ctx(
			String ctxToken,
			AlgSM2 cipherAlg,
			AlgSign signAlg,
			String clientPubKeyBase64,
			String serverPriKeyBase64) {
		InputStream	clientPubKeyStream	= new ByteArrayInputStream(clientPubKeyBase64.getBytes());
		InputStream	serverPriStream		= new ByteArrayInputStream(serverPriKeyBase64.getBytes());

		PrivateKey	privateKey			= serverPriStream == null ? null
				: CertUtil.convertPemToPrivateKey(serverPriStream);
		PublicKey	publicKey			= clientPubKeyStream == null ? null
				: CertUtil.convertPemToPublicKey(clientPubKeyStream);

		return ctx(ctxToken, cipherAlg, signAlg, privateKey, publicKey);
	}

	@Override
	public String ctx(AlgSM2 cipherAlg, AlgSign signAlg, InputStream clientPubKeyStream, InputStream serverPriStream) {
		PrivateKey	privateKey	= serverPriStream == null ? null : CertUtil.convertPemToPrivateKey(serverPriStream);
		PublicKey	publicKey	= clientPubKeyStream == null ? null
				: CertUtil.convertPemToPublicKey(clientPubKeyStream);

		String		uuid		= super.uuid(cipherAlg.getAlg() + signAlg.getAlg(),
				privateKey == null ? new byte[0] : privateKey.getEncoded(),
				publicKey == null ? new byte[0] : publicKey.getEncoded());
		if (super.getCtx(uuid) != null)
			return uuid;

		return ctx(uuid, cipherAlg, signAlg, privateKey, publicKey);
	}

	public String ctx(String ctxToken, AlgSM2 cipherAlg, AlgSign signAlg, PrivateKey privateKey, PublicKey publicKey) {
		AsymCtx ctx = AsymCtx.builder().uuid(ctxToken).keybits(256 / 8).signAlg(signAlg.getAlg())
				.clientPublicKey(publicKey).serverPrivateKey(privateKey).build();

		super.putCtx(ctx.getUuid(), ctx);

		return ctx.getUuid();
	}

	@Override
	public String ctx(
			String clientCertBase64,
			String serverPfxHex,
			String pfxPass,
			KeyStoreType keyStoreType,
			String rootCertBase64,
			String crlBase64) {
		return ctx(new ByteArrayInputStream(clientCertBase64.getBytes()),
				new ByteArrayInputStream(ByteStringKit.toBytes(serverPfxHex, ByteStringKit.HEX)), pfxPass, keyStoreType,
				rootCertBase64 == null ? null : new ByteArrayInputStream(rootCertBase64.getBytes()),
				crlBase64 == null ? null : new ByteArrayInputStream(crlBase64.getBytes()));
	}

	@Override
	public String ctx(
			InputStream clientCertStream,
			InputStream serverPfxStream,
			String pfxPass,
			KeyStoreType keyStoreType,
			InputStream rootCertStream,
			InputStream crlStream) {
		X509Certificate	clientCert		= super.getX509Cert(clientCertStream);
		PublicKey		publicKey		= super.getPublicKey(clientCert);

		KeyStore		ks				= super.getKeyStore(serverPfxStream, pfxPass, keyStoreType);
		X509Certificate	serverCert		= super.getX509Cert(ks);
		PrivateKey		privateKey		= super.getPrivateKey(ks, pfxPass);
		X509Certificate	rootCert		= this.getX509Cert(rootCertStream);
		X509CRL			crl				= super.getX509CRL(crlStream);

		String			srvCertSeqno	= super.saveCertExpireDate(serverCert);
		String			cltCertSeqno	= super.saveCertExpireDate(clientCert);

		String			uuid			= super.uuid(srvCertSeqno, cltCertSeqno.getBytes(), null);
		if (super.getCtx(uuid) != null)
			return uuid;

		AsymCtx ctx = AsymCtx.builder().uuid(uuid).keybits(256).signAlg(serverCert.getSigAlgName())
				.clientPublicKey(publicKey).serverPrivateKey(privateKey).clientCert(clientCert).serverCert(serverCert)
				.rootCert(rootCert).crl(crl).build();

		super.putCtx(uuid, ctx);

		return ctx.getUuid();
	}

	@Override
	public String ctx(String certsId) {
		throw new KtfException(KtfError.E_NOT_IMPLEMENT, "函数尚未实现");
	}

	@Override
	public byte[] encrypt(String ctxToken, byte[] data) {
		byte[]	result	= null;
		AsymCtx	ctx		= super.getCtx(ctxToken);

		result = Sm2Util.sm2Encrypt(data, new Sm2PublicKeyImpl((BCECPublicKey) ctx.getClientPublicKey()));

		return result;
	}

	@Override
	public byte[] decrypt(String ctxToken, byte[] data) {
		byte[]	result	= null;
		AsymCtx	ctx		= super.getCtx(ctxToken);

		result = Sm2Util.sm2Decrypt(data, new Sm2PrivateKeyImpl((BCECPrivateKey) ctx.getServerPrivateKey()));

		return result;
	}

	@Override
	public byte[] sign(String ctxToken, byte[] data) {
		byte[] result = null;

		try {
			AsymCtx ctx = super.getCtx(ctxToken);
			traceAsymCtx(ctx);
			// 校验签名证书
			super.checkCertValidity(ctx.getServerCert(), ctx.getRootCert(), ctx.getCrl());
			Signature signature = super.signature(ctx.getSignAlg());
			signature.initSign(ctx.getServerPrivateKey());
			signature.update(data);
			result = signature.sign();

		} catch (Exception e) {
			log.error("SM2签名异常", e);
			throw new KtfException(KtfError.E_CRYPTO, e);
		}

		return result;
	}

	@Override
	public byte[] signWithId(String ctxToken, String withId, byte[] data) {
		byte[] result = null;

		try {
			if (StrKit.isEmpty(withId))
				withId = "1234567812345678";// the default value

			AsymCtx ctx = super.getCtx(ctxToken);
			traceAsymCtx(ctx);

			Sm2PrivateKey sm2PrivateKey = new Sm2PrivateKeyImpl((BCECPrivateKey) ctx.getServerPrivateKey());
			sm2PrivateKey.setWithId(withId.getBytes(Charset.forName("UTF-8")));

			byte[] sign = Sm2Util.signSm3WithSm2(data, sm2PrivateKey);

			result = decodeASN1(sign);
		} catch (Exception e) {
			log.error("SM2签名异常", e);
			throw new KtfException(KtfError.E_CRYPTO, e);
		}

		return result;
	}

	@Override
	public byte[] signWithNoHash(String ctxToken, byte[] data) {
		byte[] result = null;

		try {
			AsymCtx ctx = super.getCtx(ctxToken);
			traceAsymCtx(ctx);
			// 校验签名证书
			super.checkCertValidity(ctx.getServerCert(), ctx.getRootCert(), ctx.getCrl());

			Sm2PrivateKey sm2PrivateKey = new Sm2PrivateKeyImpl((BCECPrivateKey) ctx.getServerPrivateKey());
			result = Sm2Util.signWithNoHash(data, sm2PrivateKey);
//			Signature signature = super.signature(AlgSign.SM2_NO.getAlg());
//			signature.initSign(ctx.getServerPrivateKey());
//			signature.update(data);
//			result	= signature.sign();
//			result	= decodeASN1(result);
		} catch (Exception e) {
			log.error("SM2签名异常", e);
			throw new KtfException(KtfError.E_CRYPTO, e);
		}

		return result;
	}

	/**
	 * 将DER编码的SM2签名解析成64字节的纯R+S字节流
	 * 
	 * @param asn1Sign
	 * @return
	 */
	public static byte[] decodeASN1(byte[] asn1Sign) {
		ASN1Sequence	as		= DERSequence.getInstance(asn1Sign);
		byte[]			rBytes	= ((ASN1Integer) as.getObjectAt(0)).getValue().toByteArray();
		byte[]			sBytes	= ((ASN1Integer) as.getObjectAt(1)).getValue().toByteArray();
		// 由于大数的补0规则，所以可能会出现33个字节的情况，要修正回32个字节
		rBytes	= fixTo32Bytes(rBytes);
		sBytes	= fixTo32Bytes(sBytes);
		byte[] rawSign = new byte[rBytes.length + sBytes.length];
		System.arraycopy(rBytes, 0, rawSign, 0, rBytes.length);
		System.arraycopy(sBytes, 0, rawSign, rBytes.length, sBytes.length);
		return rawSign;
	}

	private static byte[] fixTo32Bytes(byte[] src) {
		final int fixLen = 32;
		if (src.length == fixLen) {
			return src;
		}

		byte[] result = new byte[fixLen];
		if (src.length > fixLen) {
			System.arraycopy(src, src.length - result.length, result, 0, result.length);
		} else {
			log.trace("=======》src:{}，src.length：{}，result.length:{}", ByteStringKit.toString(src, ByteStringKit.HEX),
					src.length, result.length);
			System.arraycopy(src, 0, result, 0, src.length);
		}
		return result;
	}

	@Override
	public boolean verify(String ctxToken, byte[] data, byte[] sign) {
		boolean	result	= false;
		AsymCtx	ctx		= super.getCtx(ctxToken);
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
		} catch (InvalidKeyException | SignatureException e) {
			log.error("SM2验签异常", e);
			throw new KtfException(KtfError.E_CRYPTO, e);
		}
		return result;
	}

	@Override
	public boolean verifyWithId(String ctxToken, String withId, byte[] data, byte[] sign) {
		boolean result = false;

		try {
			if (StrKit.isEmpty(withId))
				withId = "1234567812345678";// the default value

			AsymCtx			ctx				= super.getCtx(ctxToken);
			Sm2PublicKey	sm2PublicKey	= new Sm2PublicKeyImpl((BCECPublicKey) ctx.getServerPrivateKey());
			sm2PublicKey.setWithId(withId.getBytes(Charset.forName("UTF-8")));

			result = Sm2Util.verifySm3WithSm2(data, sign, sm2PublicKey);

		} catch (Exception e) {
			log.error("SM2签名异常", e);
			throw new KtfException(KtfError.E_CRYPTO, e);
		}

		return result;
	}

	@Override
	public boolean verifyWithNoHash(String ctxToken, byte[] data, byte[] sign) {
		boolean	result	= false;
		AsymCtx	ctx		= super.getCtx(ctxToken);
		try {
			// 校验验签证书
			super.checkCertValidity(ctx.getClientCert(), ctx.getRootCert(), ctx.getCrl());

			Signature signature = super.signature(AlgSign.SM2_NO.getAlg());
			if (ctx.getClientCert() != null)
				signature.initVerify(ctx.getClientCert());
			else
				signature.initVerify(ctx.getClientPublicKey());
			signature.update(data);
			result = signature.verify(sign);
		} catch (InvalidKeyException | SignatureException e) {
			log.error("SM2验签异常", e);
			throw new KtfException(KtfError.E_CRYPTO, e);
		}
		return result;
	}

	/**
	 * 密文pem格式私钥读取
	 * 
	 * @param in
	 * @param passwd
	 * @return
	 */
	@SuppressWarnings("unused")
	private PrivateKey getPrivateKey(InputStream in, String passwd) {
		PrivateKey privateKey = null;

		try (PEMParser pemParser = new PEMParser(new InputStreamReader(in))) {
			Object readObject = pemParser.readObject();
			if (readObject instanceof PEMEncryptedKeyPair) {
				PEMEncryptedKeyPair		keyPair					= (PEMEncryptedKeyPair) readObject;
				PEMDecryptorProvider	keyDecryptorProvider	= new BcPEMDecryptorProvider(passwd.toCharArray());
				PEMKeyPair				decryptKeyPair			= keyPair.decryptKeyPair(keyDecryptorProvider);
				privateKey = new JcaPEMKeyConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME)
						.getKeyPair(decryptKeyPair).getPrivate();
			}
		} catch (Exception e) {
			log.error("获取SM2公钥失败", e);
			throw new KtfException(KtfError.E_CRYPTO, "读取SM2公钥失败", e);
		}

		return privateKey;
	}

	@Override
	public KeyPairDO genKeyPair() {
		KeyPairDO result = new KeyPairDO();
		// 生成密钥对、
		try {
			KeyPairResult keypair = KeyPairUtil.gen(KeyType.SM2, 256);
			result.setPri_pem(keypair.getPri_pem());
			result.setPub_pem(keypair.getPub_pem());
		} catch (Exception e) {
			log.error("生成SM2密钥对异常", e);
			throw new KtfException(KtfError.E_CRYPTO, "生成SM2密钥对异常", e);
		}
		return result;
	}

	void traceAsymCtx(AsymCtx ctx) {
		if (!log.isTraceEnabled())
			return;

		if (ctx.getServerPrivateKey() != null) {
			Sm2PrivateKey	sm2PrivateKey	= new Sm2PrivateKeyImpl((BCECPrivateKey) ctx.getServerPrivateKey());
			byte[]			priKey			= sm2PrivateKey.getD().toByteArray();
			log.trace("签名的私钥:{}", ByteStringKit.toString(priKey, ByteStringKit.HEX));
		}

		if (ctx.getClientPublicKey() != null) {
			Sm2PublicKey	sm2PublicKey	= new Sm2PublicKeyImpl((BCECPublicKey) ctx.getClientPublicKey());
			byte[]			pubKey			= sm2PublicKey.getQ().getEncoded(false);
			log.trace("验签的公钥:{}", ByteStringKit.toString(pubKey, ByteStringKit.HEX));
		}
	}

}
