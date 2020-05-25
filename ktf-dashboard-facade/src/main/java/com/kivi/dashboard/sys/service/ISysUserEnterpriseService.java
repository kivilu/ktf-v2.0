package com.kivi.dashboard.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.dashboard.sys.dto.SysUserEnterpriseDTO;
import com.kivi.dashboard.sys.entity.SysUserEnterprise;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 监管用户与企业关联 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface ISysUserEnterpriseService extends IService<SysUserEnterprise> {

	/**
	 * 根据ID查找所属企业ID
	 * 
	 * @param userId
	 * @return
	 */
	List<Long> selectEnterpriseIdByUserId(Long userId);

	/**
	 * 根据ID查询DTO
	 */
	SysUserEnterpriseDTO getDTOById(Long id);

	/**
	 * 新增
	 */
	Boolean save(SysUserEnterpriseDTO sysUserEnterpriseDTO);

	/**
	 * 修改
	 */
	Boolean updateById(SysUserEnterpriseDTO sysUserEnterpriseDTO);

	/**
	 * 查询列表
	 */
	List<SysUserEnterpriseDTO> list(SysUserEnterpriseDTO sysUserEnterpriseDTO);

	/**
	 * 指定列查询列表
	 */
	List<SysUserEnterpriseDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<SysUserEnterpriseDTO> listLike(SysUserEnterpriseDTO applicationDTO);

	/**
	 * 指定列模糊查询
	 */
	List<SysUserEnterpriseDTO> listLike(Map<String, Object> params, String... columns);

	/**
	 * 分页查询
	 */
	PageInfoVO<SysUserEnterpriseDTO> page(Map<String, Object> params);

	/**
	 * 保存或修改用户所监管的企业关系
	 *
	 * @param userId
	 * @param enterpriseIdList
	 */
	void saveOrUpdateUserEnterprise(Long userId, List<Long> enterpriseIdList);

	/**
	 * 根据用户批量删除
	 *
	 * @param userIds
	 */
	Boolean deleteBatchByUserIds(Long[] userIds);

	/**
	 * 根据企业批量删除
	 *
	 * @param enterpriseIds
	 */
	Boolean deleteBatchByEnterpriseIds(Long[] enterpriseIds);
}
