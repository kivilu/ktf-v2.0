package com.kivi.cif.dubbo.service;

import java.util.List;

import com.kivi.cif.dto.CifCertsDTO;
import com.kivi.framework.crypto.enums.KtfCertType;
import com.kivi.framework.dto.warapper.WarpReqDTO;
import com.kivi.framework.exception.KtfException;

public interface CertService {

	public static final String MOCK = "com.kivi.cif.dubbo.mock.CifCertServiceMock";

	/**
	 * 获取客户证书
	 * 
	 * @param identifier 用户标识
	 * @param type       类型
	 * @return
	 */
	List<CifCertsDTO> getCifCerts(WarpReqDTO<String> identifierDTO, KtfCertType... types) throws KtfException;

	/**
	 * 获取客户证书
	 * 
	 * @param identifier 用户标识
	 * @param type       类型
	 * @return
	 */
	byte[] getCifCert(WarpReqDTO<String> identifierDTO, KtfCertType type) throws KtfException;

	/**
	 * 保存签名密钥
	 * 
	 * @param identifier
	 * @param type
	 * @param signAlg
	 * @param pemPriKey
	 * @param pemPubKey
	 * @return
	 */
	Boolean genKeypair(WarpReqDTO<String> identifierDTO, String signAlg) throws KtfException;

}
