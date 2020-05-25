package com.kivi.cif.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.cif.dto.CifCustomerAuthsDTO;
import com.kivi.cif.entity.CifCustomerAuths;
import com.kivi.framework.vo.page.PageInfoVO;

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
	 * 根据唯一键查询记录
	 * 
	 * @see CifCustomerAuthsDao.getCifCustomerAuths
	 * @param cifAuthDTO
	 * @return
	 */
	public CifCustomerAuths getCifCustomerAuths(final CifCustomerAuthsDTO cifAuthDTO);

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
	 * 根据ID查询DTO
	 */
	CifCustomerAuthsDTO getDTOById(Long id);

	/**
	 * 修改
	 */
	Boolean updateById(CifCustomerAuthsDTO cifCustomerAuthsDTO);

	/**
	 * 更新
	 * 
	 * @param condEntity
	 * @param updaeEntity
	 * @return
	 */
	Boolean updateByEntity(CifCustomerAuths condEntity, CifCustomerAuths updaeEntity);

	/**
	 * 查询列表
	 */
	List<CifCustomerAuthsDTO> list(CifCustomerAuthsDTO cifCustomerAuthsDTO);

	/**
	 * 指定列查询列表
	 */
	List<CifCustomerAuthsDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<CifCustomerAuthsDTO> listLike(CifCustomerAuthsDTO cifCustomerAuthsDTO);

	/**
	 * 指定列模糊查询
	 */
	List<CifCustomerAuthsDTO> listLike(Map<String, Object> params, String... columns);

	/**
	 * 分页查询
	 */
	PageInfoVO<CifCustomerAuthsDTO> page(Map<String, Object> params);
}
