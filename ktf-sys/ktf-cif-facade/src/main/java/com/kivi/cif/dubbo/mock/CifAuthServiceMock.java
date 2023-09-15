package com.kivi.cif.dubbo.mock;

import com.kivi.cif.dto.CifCustomerAuthsDTO;
import com.kivi.cif.service.AuthService;
import com.kivi.framework.dto.warapper.WarpReqDTO;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.exception.KtfMockException;

public class CifAuthServiceMock implements AuthService {

	@Override
	public CifCustomerAuthsDTO auth(WarpReqDTO<CifCustomerAuthsDTO> dto) throws KtfException {
		throw new KtfMockException();
	}

	@Override
	public CifCustomerAuthsDTO getAuth(WarpReqDTO<CifCustomerAuthsDTO> dto) throws KtfException {
		throw new KtfMockException();
	}

	@Override
	public void rollbackAuth(WarpReqDTO<CifCustomerAuthsDTO> dto) {
		throw new KtfMockException();

	}

	@Override
	public CifCustomerAuthsDTO resetAuthPass(WarpReqDTO<CifCustomerAuthsDTO> condCifAuthDTO) throws KtfException {
		throw new KtfMockException();
	}

	@Override
	public CifCustomerAuthsDTO updateAuthPass(WarpReqDTO<CifCustomerAuthsDTO> condCifAuthDTO, String newPassword)
			throws KtfException {
		throw new KtfMockException();
	}

}
