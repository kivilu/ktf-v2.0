package com.kivi.cif.service;

import java.util.List;

import com.kivi.cif.dto.CifCustomerAuthsDTO;
import com.kivi.cif.dto.CifCustomerDTO;
import com.kivi.framework.dto.warapper.WarpReqDTO;
import com.kivi.framework.exception.KtfException;

public interface CustomerService {

	public static final String MOCK = "com.kivi.cif.dubbo.mock.CifCustomerServiceMock";

	/**
	 * 根据主键查找客户信息
	 * 
	 * @param cifId 客户信息ID
	 * @return
	 */
	CifCustomerDTO getCustomer(Long cifId) throws KtfException;

	/**
	 * 根据主键查找客户信息集合
	 * 
	 * @param cifIds 客户信息ID集合
	 * @return
	 */
	List<CifCustomerDTO> getCustomer(List<Long> cifIds) throws KtfException;

	/**
	 * 注册客户
	 * 
	 * @param customerDTO 客户信息
	 * @param cifAuthDTO  认证信息
	 * @return
	 */
	CifCustomerAuthsDTO registCustomer(WarpReqDTO<CifCustomerDTO> customerDTO, CifCustomerAuthsDTO cifAuthDTO)
			throws KtfException;

}
