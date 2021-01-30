package com.kivi.cif.dubbo.service.impl;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.dubbo.config.annotation.DubboService;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kivi.cif.dto.CifCertsDTO;
import com.kivi.cif.dubbo.service.CertService;
import com.kivi.cif.entity.CifCerts;
import com.kivi.cif.properties.CifProperties;
import com.kivi.cif.service.CifCertsService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.constant.enums.KtfStatus;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.crypto.domain.KeyPairDO;
import com.kivi.framework.crypto.domain.KeyPairResult;
import com.kivi.framework.crypto.enums.AlgSign;
import com.kivi.framework.crypto.enums.KeyType;
import com.kivi.framework.crypto.enums.KtfCertType;
import com.kivi.framework.crypto.sm2.Sm2PublicKey;
import com.kivi.framework.crypto.sm2.Sm2PublicKeyImpl;
import com.kivi.framework.crypto.util.CertUtil;
import com.kivi.framework.crypto.util.KeyPairUtil;
import com.kivi.framework.dto.warapper.WarpReqDTO;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.ByteStringKit;
import com.kivi.framework.util.kit.CollectionKit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DubboService(version = CifProperties.DUBBO_VERSION)
public class CertServiceImpl implements CertService {

	@Autowired
	private CifCertsService cifCertsService;

	@KtfTrace("查询客户证书")
	@Override
	public List<CifCertsDTO> getCifCerts(WarpReqDTO<String> identifierDTO, KtfCertType... types) {
		final Long		hawkSeqId	= identifierDTO.getTranUniqueId();
		final String	identifier	= identifierDTO.getReqObject();

		log.trace("【{}】用户{}查询客户证书，证书类型：{}", hawkSeqId, identifier, types);

		LambdaQueryWrapper<CifCerts> queryWrapper = Wrappers.<CifCerts>lambdaQuery();
		queryWrapper.eq(CifCerts::getIdentifier, identifier).eq(CifCerts::getState,
				String.valueOf(KtfStatus.ENABLED.code));

		if (CollectionKit.isNotEmpty(types))
			queryWrapper.in(CifCerts::getType,
					Arrays.asList(types).stream().map(KtfCertType::getCode).collect(Collectors.toList()));

		List<CifCerts> list = cifCertsService.list(queryWrapper);

		return BeanConverter.convert(CifCertsDTO.class, list);
	}

	@Override
	public byte[] getCifCert(WarpReqDTO<String> identifierDTO, KtfCertType type) throws KtfException {
		final Long		hawkSeqId	= identifierDTO.getTranUniqueId();
		final String	identifier	= identifierDTO.getReqObject();

		byte[]			result		= null;
		try {
			CifCerts cifCert = cifCertsService.getCifCert(identifier, type.code);
			if (cifCert == null) {
				// 生成密钥对
				this.genKeypair(identifierDTO, AlgSign.SM2_SM3.getAlg());
				cifCert = cifCertsService.getCifCert(identifier, type.code);
			}

			ByteBuffer certBuf = null;
			if (type.isPublickey()) {
				PublicKey		publicKey		= CertUtil
						.convertPemToPublicKey(new ByteArrayInputStream(cifCert.getDataBase64().getBytes()));
				Sm2PublicKey	sm2PublicKey	= new Sm2PublicKeyImpl((BCECPublicKey) publicKey);
				byte[]			pubkey			= sm2PublicKey.getQ().getEncoded(false);

				certBuf = ByteBuffer.allocate(64);
				certBuf.put(pubkey, 1, 64);
			}

			if (log.isTraceEnabled())
				log.trace("【{}】pem格式的{}密钥转换结果：{}", hawkSeqId, type,
						ByteStringKit.toString(certBuf.array(), ByteStringKit.HEX));

			result = certBuf.array();
		} catch (Exception e) {
			log.error("【" + hawkSeqId + "】获取用户证书异常：identifier=" + identifier + "，类型：" + type.name(), e);
			throw new KtfException(KtfError.E_UNDEFINED, "保存签名密钥对异常", e);
		}

		return result;
	}

	@Transactional
	@KtfTrace("生成客户密钥对")
	@Override
	public Boolean genKeypair(WarpReqDTO<String> identifierDTO, String signAlg) throws KtfException {
		final Long		hawkSeqId	= identifierDTO.getTranUniqueId();
		final String	identifier	= identifierDTO.getReqObject();

		log.trace("【{}】用户{}生成证书，算法：{}", hawkSeqId, identifier, signAlg);

		AlgSign alg = AlgSign.alg(signAlg);
		try {
			// 生成密钥对
			KeyPairDO	keyPair	= genKeypair(alg);

			CifCerts	pubCert	= cifCertsService.getCifCert(identifier, KtfCertType.VERIFY.code);
			if (pubCert == null) {
				// 保存公钥
				CifCerts entity = new CifCerts();
				entity.setIdentifier(identifier);
				entity.setType(KtfCertType.VERIFY.code);
				entity.setState(String.valueOf(KtfStatus.ENABLED.code));
				entity.setAlgSign(signAlg);
				entity.setExt("pem");
				entity.setDataBase64(keyPair.getPub_pem());
				cifCertsService.save(entity);
			} else {
				CifCerts entity = new CifCerts();
				entity.setId(pubCert.getId());
				entity.setIdentifier(identifier);
				entity.setType(KtfCertType.VERIFY.code);
				entity.setAlgSign(signAlg);
				entity.setDataBase64(keyPair.getPub_pem());
				cifCertsService.save(entity);
			}

			// 保存私钥
			CifCerts priCert = cifCertsService.getCifCert(identifier, KtfCertType.SIGN.code);

			if (priCert == null) {
				CifCerts entity = new CifCerts();
				entity.setIdentifier(identifier);
				entity.setType(KtfCertType.SIGN.code);
				entity.setState(String.valueOf(KtfStatus.ENABLED.code));
				entity.setAlgSign(signAlg);
				entity.setExt("pem");
				entity.setDataBase64(keyPair.getPri_pem());
				cifCertsService.save(entity);
			} else {
				CifCerts entity = new CifCerts();
				entity.setId(priCert.getId());
				entity.setIdentifier(identifier);
				entity.setType(KtfCertType.SIGN.code);
				entity.setAlgSign(signAlg);
				entity.setDataBase64(keyPair.getPri_pem());
				cifCertsService.save(entity);
			}
		} catch (Exception e) {
			log.error("【" + hawkSeqId + "】生成并保存签名密钥对异常", e);
			throw new KtfException(KtfError.E_UNDEFINED, "保存签名密钥对异常", e);
		}

		return true;
	}

	private KeyPairDO genKeypair(AlgSign alg) {
		KeyPairDO keyPair = null;
		switch (alg) {
		case RSA_SHA1:
		case RSA_SHA256:
		case RSA_SM3:
			break;
		case SM2_NO:
		case SM2_SHA1:
		case SM2_SHA256:
		case SM2_SM3:
			// 生成密钥对、
			try {
				keyPair = new KeyPairDO();
				KeyPairResult keypair = KeyPairUtil.gen(KeyType.SM2, 256);
				keyPair.setPri_pem(keypair.getPri_pem());
				keyPair.setPub_pem(keypair.getPub_pem());
			} catch (Exception e) {
				log.error("生成SM2密钥对异常", e);
				throw new KtfException(KtfError.E_CRYPTO, "生成SM2密钥对异常", e);
			}
			break;
		default:
			break;

		}
		return keyPair;
	}

}
