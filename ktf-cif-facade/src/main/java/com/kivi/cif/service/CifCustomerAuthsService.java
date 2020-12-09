package com.kivi.cif.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.cif.dto.CifCustomerAuthsDTO;
import com.kivi.cif.entity.CifCustomerAuths;
import com.kivi.framework.vo.UserVo;

/**
 * <p>
 * 客户验证 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-10-28
 */
public interface CifCustomerAuthsService extends IService<CifCustomerAuths> {

	/**
	 * 验证用户的有效性
	 * 
	 * @param userVo
	 * @return
	 */
	Boolean authUser(UserVo userVo);

	/**
	 * 根据ID查询DTO
	 */
	CifCustomerAuthsDTO getDto(Long id);

	/**
	 * 根据唯一键查询记录
	 * 
	 * @see CifCustomerAuthsDao.getCifCustomerAuths
	 * @param cifAuthDTO
	 * @return
	 */
	CifCustomerAuths getCifCustomerAuths(final CifCustomerAuthsDTO cifAuthDTO);

	/**
	 * 根据唯一键查询记录
	 * 
	 * @param bizCode
	 * @param identityType
	 * @param identifier
	 * @param userType
	 * @return
	 */
	CifCustomerAuths getCifCustomerAuths(Long appid, String identityType, String identifier, String userType);

	/**
	 * 修改
	 */
	Long save(CifCustomerAuthsDTO dto);

	/**
	 * 修改
	 */
	Boolean updateById(CifCustomerAuthsDTO dto);

	/**
	 * 更新
	 * 
	 * @param condEntity
	 * @param updaeEntity
	 * @return
	 */
	Boolean updateByEntity(CifCustomerAuths condEntity, CifCustomerAuths updaeEntity);

	/**
	 * 修改用户密码
	 * 
	 * @param userVo      用户信息， id和password属性必须
	 * @param newPassword 新密码，若未null，则修改为默认密码
	 * @return
	 */
	Boolean updateCredential(UserVo userVo, String newPassword);

}
