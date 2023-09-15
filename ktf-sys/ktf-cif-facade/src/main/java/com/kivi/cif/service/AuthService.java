package com.kivi.cif.service;

import com.kivi.cif.dto.CifCustomerAuthsDTO;
import com.kivi.framework.dto.warapper.WarpReqDTO;
import com.kivi.framework.exception.KtfException;

public interface AuthService {

	public static final String MOCK = "com.kivi.cif.dubbo.mock.CifAuthServiceMock";

	/**
	 * 获取客户认证记录
	 * 
	 * @param bizCode    业务代码
	 * @param identifier 用户标识
	 * @return
	 */
	CifCustomerAuthsDTO auth(WarpReqDTO<CifCustomerAuthsDTO> dto) throws KtfException;

	/**
	 * 重置客户密码
	 * 
	 * @param dto
	 * @return
	 * @throws CifException
	 */
	CifCustomerAuthsDTO resetAuthPass(WarpReqDTO<CifCustomerAuthsDTO> condCifAuthDTO) throws KtfException;

	/**
	 * 更新客户密码
	 * 
	 * @param dto
	 * @return
	 * @throws CifException
	 */
	CifCustomerAuthsDTO updateAuthPass(WarpReqDTO<CifCustomerAuthsDTO> condCifAuthDTO, String newPassword)
			throws KtfException;

	/**
	 * 获取客户认证记录
	 * 
	 * @param bizCode      业务代码
	 * @param identityType 认证类型
	 * @param identifier   用户标识
	 * @return
	 */
	CifCustomerAuthsDTO getAuth(WarpReqDTO<CifCustomerAuthsDTO> dto) throws KtfException;

	/**
	 * 回滚创建的客户认证记录
	 * 
	 * @param dto
	 */
	void rollbackAuth(WarpReqDTO<CifCustomerAuthsDTO> dto);

}
