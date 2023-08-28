package com.kivi.cif.dubbo.mock;

import java.util.List;

import com.kivi.cif.dto.CifCertsDTO;
import com.kivi.cif.dubbo.service.CertService;
import com.kivi.framework.crypto.enums.KtfCertType;
import com.kivi.framework.dto.warapper.WarpReqDTO;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.exception.KtfMockException;

public class CifCertServiceMock implements CertService {

	@Override
	public byte[] getCifCert(WarpReqDTO<String> identifierDTO, KtfCertType type) throws KtfException {
		throw new KtfMockException();
	}

	@Override
	public List<CifCertsDTO> getCifCerts(WarpReqDTO<String> identifierDTO, KtfCertType... types) throws KtfException {
		throw new KtfMockException();
	}

	@Override
	public Boolean genKeypair(WarpReqDTO<String> identifierDTO, String signAlg) throws KtfException {
		throw new KtfMockException();
	}

}
