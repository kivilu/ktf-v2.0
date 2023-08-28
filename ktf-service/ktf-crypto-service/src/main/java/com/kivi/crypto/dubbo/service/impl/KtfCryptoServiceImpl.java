package com.kivi.crypto.dubbo.service.impl;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.security.PublicKey;

import javax.annotation.PostConstruct;

import org.apache.dubbo.config.annotation.DubboService;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.springframework.beans.factory.annotation.Autowired;

import com.kivi.crypto.dto.KtfKeyDTO;
import com.kivi.crypto.dubbo.service.KtfCryptoService;
import com.kivi.crypto.enums.KtfCryptoAlg;
import com.kivi.crypto.properties.KtfCryptoServiceProperties;
import com.kivi.crypto.service.AesService;
import com.kivi.crypto.service.CtxCacheService;
import com.kivi.crypto.service.RsaService;
import com.kivi.crypto.service.SM2Service;
import com.kivi.crypto.service.SM4Service;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.crypto.domain.KeyPairDO;
import com.kivi.framework.crypto.enums.AlgAES;
import com.kivi.framework.crypto.enums.AlgRSA;
import com.kivi.framework.crypto.enums.AlgSM2;
import com.kivi.framework.crypto.enums.AlgSM4;
import com.kivi.framework.crypto.enums.AlgSign;
import com.kivi.framework.crypto.enums.KtfCertType;
import com.kivi.framework.crypto.sm2.Sm2PublicKey;
import com.kivi.framework.crypto.sm2.Sm2PublicKeyImpl;
import com.kivi.framework.crypto.util.CertUtil;
import com.kivi.framework.dto.warapper.WarpReqDTO;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.CRC16Util;
import com.kivi.framework.util.kit.ByteStringKit;
import com.kivi.framework.util.kit.StrKit;

import lombok.extern.slf4j.Slf4j;

@DubboService(version = KtfCryptoServiceProperties.DUBBO_VERSION)
@Slf4j
public class KtfCryptoServiceImpl implements KtfCryptoService {

	private static final String	CALLBACK_NAME	= "KtfCryptoServiceImpl";

	@Autowired
	private SM2Service			sm2Service;

	@Autowired
	private RsaService			rsaService;

	@Autowired
	private SM4Service			sm4Service;

	@Autowired
	private AesService			aesService;

	@Autowired
	private CtxCacheService		ctxCacheService;

	@PostConstruct
	private void init() {
	}

	@Override
	public String ctx(WarpReqDTO<byte[]> keyDTO, KtfCryptoAlg alg) throws KtfException {
		final Long		hawkSeqId	= keyDTO.getTranUniqueId();
		final byte[]	key			= keyDTO.getReqObject();
		String			ctxToken	= null;
		String			keyOfCTX	= null;

		switch (alg) {
		case NONE:
			break;
		case SM4: {
			keyOfCTX	= StrKit.join(StrKit.DOT, CALLBACK_NAME, "AlgSM4.SM4_ECB_PKCS7", ByteStringKit.toHex(key));
			ctxToken	= ctxCacheService.get(keyOfCTX);
			if (StrKit.isNotEmpty(ctxToken)) {
				log.trace("【{}】加密/解密算法{}的CTX之前已经创建，直接返回，key：{}，ctx：{}", hawkSeqId, alg, ByteStringKit.toHex(key),
						ctxToken);
				return ctxToken;
			}
			String ctx = sm4Service.ctx(AlgSM4.SM4_ECB_PKCS7, key);
			ctxToken = StrKit.join(StrKit.DOT, CALLBACK_NAME, "AlgSM4.SM4_ECB_PKCS7", ctx);
			break;
		}
		case SM4_NoPadding: {
			keyOfCTX	= StrKit.join(StrKit.DOT, CALLBACK_NAME, "AlgSM4.SM4_ECB_NoPadding", ByteStringKit.toHex(key));
			ctxToken	= ctxCacheService.get(keyOfCTX);
			if (StrKit.isNotEmpty(ctxToken)) {
				log.trace("【{}】加密/解密算法{}的CTX之前已经创建，直接返回，key：{}，ctx：{}", hawkSeqId, alg, ByteStringKit.toHex(key),
						ctxToken);
				return ctxToken;
			}
			String ctx = sm4Service.ctx(AlgSM4.SM4_ECB_NoPadding, key);
			ctxToken = StrKit.join(StrKit.DOT, CALLBACK_NAME, "AlgSM4.SM4_ECB_NoPadding", ctx);
			break;
		}
		case AES: {
			keyOfCTX	= StrKit.join(StrKit.DOT, CALLBACK_NAME, "AlgAES.AES_ECB_PKCS5", ByteStringKit.toHex(key));
			ctxToken	= ctxCacheService.get(keyOfCTX);
			if (StrKit.isNotEmpty(ctxToken)) {
				log.trace("【{}】加密/解密算法{}的CTX之前已经创建，直接返回，key：{}，ctx：{}", hawkSeqId, alg, ByteStringKit.toHex(key),
						ctxToken);
				return ctxToken;
			}
			String ctx = aesService.ctx(AlgAES.AES_ECB_PKCS5, key);
			ctxToken = StrKit.join(StrKit.DOT, CALLBACK_NAME, "AlgAES.AES_ECB_PKCS5", ctx);
			break;
		}
		default:
			log.error("【{}】创建加密/解密算法{}的CTX失败，key：{}，ctx：{}", hawkSeqId, alg,
					ByteStringKit.toString(key, ByteStringKit.HEX));
			throw new KtfException(KtfError.E_NOT_IMPLEMENT, "不支持的对称加密算法");
		}

		// 缓存CTX
		ctxCacheService.put(keyOfCTX, ctxToken);

		// 缓存密钥
		KtfKeyDTO<byte[]> ktfKeyDTO = KtfKeyDTO.build(alg, key);
		ctxCacheService.put(ctxToken, ktfKeyDTO);

		if (log.isTraceEnabled())
			log.trace("【{}】创建加密/解密算法{}的CTX成功，key：{}，ctx：{}", hawkSeqId, alg, ByteStringKit.toHex(key), ctxToken);

		return ctxToken;

	}

	@Override
	public String ctx(WarpReqDTO<KeyPairDO> keypairDTO, AlgSign signAlg) {
		final Long		hawkSeqId	= keypairDTO.getTranUniqueId();
		final KeyPairDO	keypair		= keypairDTO.getReqObject();
		String			ctxToken	= null;
		String			keyOfCTX	= null;

		switch (signAlg) {
		case RSA_SHA1:
		case RSA_SHA256:
		case RSA_SM3: {
			keyOfCTX	= StrKit.join(StrKit.DOT, CALLBACK_NAME, "AlgRSA.RSA_ECB_PKCS1", keypair.hashCode());
			ctxToken	= ctxCacheService.get(keyOfCTX);
			if (StrKit.isNotEmpty(ctxToken)) {
				log.trace("【{}】非对称加解密/签名/验签CTX，签名算法：{}，CTX：{}", hawkSeqId, signAlg, ctxToken);
				return ctxToken;
			}
			String ctx = rsaService.ctx(AlgRSA.RSA_ECB_PKCS1, signAlg, keypair.getPub_pem(), keypair.getPri_pem());
			ctxToken = StrKit.join(StrKit.DOT, CALLBACK_NAME, "AlgRSA.RSA_ECB_PKCS1", ctx);
			break;
		}
		case SM2_NO:
		case SM2_SHA1:
		case SM2_SHA256:
		case SM2_SM3: {
			keyOfCTX	= StrKit.join(StrKit.DOT, CALLBACK_NAME, "AlgSM2.SM2", keypair.hashCode());
			ctxToken	= ctxCacheService.get(keyOfCTX);
			if (StrKit.isNotEmpty(ctxToken)) {
				log.trace("【{}】非对称加解密/签名/验签CTX，签名算法：{}，CTX：{}", hawkSeqId, signAlg, ctxToken);
				return ctxToken;
			}
			String ctx = sm2Service.ctx(AlgSM2.SM2, signAlg, keypair.getPub_pem(), keypair.getPri_pem());
			ctxToken = StrKit.join(StrKit.DOT, CALLBACK_NAME, "AlgSM2.SM2", ctx);
			break;
		}
		default:
			break;

		}

		// 缓存CTX
		ctxCacheService.put(keyOfCTX, ctxToken);

		// 保存到Zookeeper
		KtfKeyDTO<KeyPairDO> ktfKeyDTO = KtfKeyDTO.build(signAlg, keypair);
		ctxCacheService.put(ctxToken, ktfKeyDTO);

		log.trace("【{}】创建非对称加解密/签名/验签CTX，签名算法：{}，CTX：{}", hawkSeqId, signAlg, ctxToken);

		return ctxToken;
	}

	@Override
	public byte[] encrypt(WarpReqDTO<String> ctxDTO, byte[] data) {
		String	ctxToken	= ctxToken(ctxDTO.getReqObject());
		byte[]	result		= sm4Service.encrypt(ctxToken, data);
		if (log.isTraceEnabled()) {
			log.trace("【{}】数据加密\r\nCTX:{}\r\n明文：{}\r\n密文：{}", ctxDTO.getTranUniqueId(), ctxToken,
					ByteStringKit.toString(data, ByteStringKit.HEX), ByteStringKit.toString(result, ByteStringKit.HEX));
		}

		return result;
	}

	@Override
	public byte[] decrypt(WarpReqDTO<String> ctxDTO, byte[] data) {
		String	ctxToken	= ctxToken(ctxDTO.getReqObject());
		byte[]	result		= sm4Service.decrypt(ctxToken, data);
		if (log.isTraceEnabled()) {
			log.trace("【{}】数据解密\r\nctx:{}\r\n密文：{}\r\n明文：{}", ctxDTO.getTranUniqueId(), ctxToken,
					ByteStringKit.toString(data, ByteStringKit.HEX), ByteStringKit.toString(result, ByteStringKit.HEX));
		}

		return result;
	}

	@Override
	public Short crc(WarpReqDTO<String> ctxDTO, byte[] data) throws KtfException {
		String		ctxToken	= ctxToken(ctxDTO.getReqObject());

		byte[]		sessionKey	= symmKey(ctxToken);

		ByteBuffer	crcBuff		= ByteBuffer
				.allocate((data == null ? 0 : data.length) + (sessionKey == null ? 0 : sessionKey.length));
		if (data != null)
			crcBuff.put(data);

		if (sessionKey != null)
			crcBuff.put(sessionKey);

		Integer crc = CRC16Util.calcCrc16(crcBuff.array());

		if (log.isTraceEnabled())
			log.trace("【{}】CRC计算，\r\n原文:{}\r\n结果：{}", ctxDTO.getTranUniqueId(),
					ByteStringKit.toString(crcBuff.array(), ByteStringKit.HEX), crc);

		return crc.shortValue();
	}

	@Override
	public Boolean crc(WarpReqDTO<String> ctxDTO, Short crc, byte[] data) throws KtfException {
		Short cacCrc = crc(ctxDTO, data);
		return cacCrc.compareTo(crc) == 0;
	}

	@Override
	public byte[] sign(WarpReqDTO<String> ctxDTO, byte[] data) throws KtfException {
		String	ctxToken	= ctxToken(ctxDTO.getReqObject());
		byte[]	result		= null;
		if (data.length == 32)
			result = sm2Service.signWithNoHash(ctxToken, data);
		else
			result = sm2Service.sign(ctxToken, data);
		if (log.isTraceEnabled()) {
			log.trace("【{}】SM2签名CTX:{}\r\n明文：{}\r\n签名：{}\r\n", ctxDTO.getTranUniqueId(), ctxToken,
					ByteStringKit.toString(data, ByteStringKit.HEX), ByteStringKit.toString(result, ByteStringKit.HEX));
		}
		return result;
	}

	@Override
	public byte[] sign(WarpReqDTO<String> ctxDTO, String withId, byte[] data) throws KtfException {
		String	ctxToken	= ctxToken(ctxDTO.getReqObject());
		byte[]	result		= sm2Service.signWithId(ctxToken, withId, data);
		if (log.isTraceEnabled()) {
			if (log.isTraceEnabled()) {
				log.trace("【{}】SM2withId签名CTX:{}\r\nwithId:{}\r\n明文：{}\r\n签名：{}\r\n", ctxDTO.getTranUniqueId(),
						ctxToken, withId, ByteStringKit.toString(data, ByteStringKit.HEX),
						ByteStringKit.toString(result, ByteStringKit.HEX));
			}
		}
		return result;
	}

	@Override
	public Boolean verify(WarpReqDTO<String> ctxDTO, byte[] data, byte[] sign) throws KtfException {
		String	ctxToken	= ctxToken(ctxDTO.getReqObject());
		Boolean	result		= false;
		if (data.length == 32)
			result = sm2Service.verifyWithNoHash(ctxToken, data, sign);
		else
			result = sm2Service.verify(ctxToken, data, sign);
		if (log.isTraceEnabled()) {
			log.trace("【{}】CTX:{}，明文：{}，签名：{}，验签结果：{}", ctxDTO.getTranUniqueId(), ctxToken,
					ByteStringKit.toString(data, ByteStringKit.HEX), ByteStringKit.toString(sign, ByteStringKit.HEX),
					result);
		}
		return result;
	}

	@Override
	public Boolean verify(WarpReqDTO<String> ctxDTO, String withId, byte[] data, byte[] sign) throws KtfException {
		Boolean	result		= false;
		String	ctxToken	= ctxToken(ctxDTO.getReqObject());
		result = sm2Service.verifyWithId(ctxToken, withId, data, sign);

		if (log.isTraceEnabled()) {
			log.trace("【{}】CTX:{}，明文：{}，签名：{}，验签结果：{}", ctxDTO.getTranUniqueId(), ctxToken,
					ByteStringKit.toString(data, ByteStringKit.HEX), ByteStringKit.toString(sign, ByteStringKit.HEX),
					result);
		}
		return result;
	}

	@Override
	public KeyPairDO genKeypair(AlgSign alg) {
		KeyPairDO keyPair = null;
		switch (alg) {
		case RSA_SHA1:
		case RSA_SHA256:
		case RSA_SM3:
			keyPair = rsaService.genKeyPair();
			break;
		case SM2_NO:
		case SM2_SHA1:
		case SM2_SHA256:
		case SM2_SM3:
			keyPair = sm2Service.genKeyPair();
			break;
		default:
			break;

		}
		return keyPair;
	}

	@Override
	public byte[] convertPem2Bin(WarpReqDTO<String> base64KeyDTO, KtfCertType type) {
		final Long		hawkSeqId	= base64KeyDTO.getTranUniqueId();
		final String	base64Key	= base64KeyDTO.getReqObject();

		ByteBuffer		result		= null;
		if (type.isPublickey()) {
			PublicKey		publicKey		= CertUtil
					.convertPemToPublicKey(new ByteArrayInputStream(base64Key.getBytes()));
			Sm2PublicKey	sm2PublicKey	= new Sm2PublicKeyImpl((BCECPublicKey) publicKey);
			byte[]			pubkey			= sm2PublicKey.getQ().getEncoded(false);

			result = ByteBuffer.allocate(64);
			result.put(pubkey, 1, 64);
		}

		if (log.isTraceEnabled())
			log.trace("【{}】pem格式的{}密钥转换结果：{}", hawkSeqId, type,
					ByteStringKit.toString(result.array(), ByteStringKit.HEX));

		return result.array();
	}

	/**
	 * 获取对策密钥
	 * 
	 * @param ctxToken
	 * @return
	 */
	private byte[] symmKey(String ctxToken) {
		byte[] result = sm4Service.key(ctxToken);
		if (result == null) {
			result = aesService.key(ctxToken);
		}
		return result;
	}

	String ctxToken(final String ctxToken) {
		String[] keyItems = StrKit.split(ctxToken, StrKit.DOT);
		if (keyItems == null || keyItems.length != 4 || !CALLBACK_NAME.equals(keyItems[0])) {
			log.trace("ctxToken[{}]不属于本类，不进行处理直接返回", ctxToken);
			return ctxToken;
		}

		String	algEnumName	= keyItems[1];
		String	algName		= keyItems[2];
		String	ctx			= keyItems[3];

		switch (algEnumName) {
		case "AlgSM4": {
			if (sm4Service.key(ctx) == null) {
				KtfKeyDTO<byte[]> keyDTO = ctxCacheService.getBytes(ctxToken);
				ctx = sm4Service.ctx(ctx, AlgSM4.valueOf(algName), keyDTO.getKey(), keyDTO.getIv());
			}

			break;
		}
		case "AlgAES": {
			if (aesService.key(ctx) == null) {
				KtfKeyDTO<byte[]> keyDTO = ctxCacheService.getBytes(ctxToken);
				ctx = aesService.ctx(ctx, AlgAES.valueOf(algName), keyDTO.getKey(), keyDTO.getIv());
			}
			break;
		}
		case "AlgRSA": {
			if (rsaService.asymCtx(ctx) == null) {
				KtfKeyDTO<KeyPairDO>	keyDTO		= ctxCacheService.getKeyPair(ctxToken);
				KeyPairDO				keyPairDO	= keyDTO.getKey();
				ctx = rsaService.ctx(ctx, AlgRSA.valueOf(algName), keyDTO.getSignAlg(), keyPairDO.getPub_pem(),
						keyPairDO.getPri_pem());
			}
			break;
		}
		case "AlgSM2": {
			if (sm2Service.asymCtx(ctx) == null) {
				log.trace("CTX Token:{}", ctxToken);
				KtfKeyDTO<KeyPairDO>	keyDTO		= ctxCacheService.getKeyPair(ctxToken);
				KeyPairDO				keyPairDO	= keyDTO.getKey();
				ctx = sm2Service.ctx(ctx, AlgSM2.valueOf(algName), keyDTO.getSignAlg(), keyPairDO.getPub_pem(),
						keyPairDO.getPri_pem());
			}
			break;
		}
		default:
			log.warn("无法识别的key：{}", ctxToken);
			break;
		}

		return ctx;
	}

}
