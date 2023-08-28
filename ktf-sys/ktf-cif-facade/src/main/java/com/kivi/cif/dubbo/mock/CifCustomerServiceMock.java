package com.kivi.cif.dubbo.mock;

import java.util.List;

import com.kivi.cif.dto.CifCustomerAuthsDTO;
import com.kivi.cif.dto.CifCustomerDTO;
import com.kivi.cif.dubbo.service.CustomerService;
import com.kivi.framework.dto.warapper.WarpReqDTO;
import com.kivi.framework.exception.KtfMockException;

public class CifCustomerServiceMock implements CustomerService {

	@Override
	public CifCustomerDTO getCustomer(Long cifId) {
		throw new KtfMockException();
	}

	@Override
	public List<CifCustomerDTO> getCustomer(List<Long> cifIds) {
		throw new KtfMockException();
	}

	@Override
	public CifCustomerAuthsDTO registCustomer(WarpReqDTO<CifCustomerDTO> customerDTO, CifCustomerAuthsDTO cifAuthDTO) {
		throw new KtfMockException();
	}

}
