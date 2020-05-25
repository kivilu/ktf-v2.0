package com.kivi.dashboard.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.dashboard.sys.dto.SysUserRoleDTO;
import com.kivi.dashboard.sys.entity.SysUserRole;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 用户角色 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

	/**
	 * 根据ID查询DTO
	 */
	SysUserRoleDTO getDTOById(Long id);

	/**
	 * 新增
	 */
	Boolean save(SysUserRoleDTO sysUserRoleDTO);

	/**
	 * 修改
	 */
	Boolean updateById(SysUserRoleDTO sysUserRoleDTO);

	/**
	 * 查询列表
	 */
	List<SysUserRoleDTO> list(SysUserRoleDTO sysUserRoleDTO);

	/**
	 * 指定列查询列表
	 */
	List<SysUserRoleDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<SysUserRoleDTO> listLike(SysUserRoleDTO applicationDTO);

	/**
	 * 指定列模糊查询
	 */
	List<SysUserRoleDTO> listLike(Map<String, Object> params, String... columns);

	/**
	 * 分页查询
	 */
	PageInfoVO<SysUserRoleDTO> page(Map<String, Object> params);

	/**
	 * 根据用户ID或者拥有角色ID集合
	 *
	 * @param userId
	 * @return
	 */
	List<Long> selectRoleIdListByUserId(Long userId);

	/**
	 * 保存或修改用户与角色关系
	 *
	 * @param userId
	 * @param roleIdList
	 */
	void saveOrUpdateUserRole(Long userId, List<Long> roleIdList);

	/**
	 * 根据用户批量删除
	 *
	 * @param userIds
	 */
	Boolean deleteBatchByUserIds(Long[] userIds);

	/**
	 * 根据角色批量删除
	 *
	 * @param roleIds
	 */
	Boolean deleteBatchByRoleIds(Long[] roleIds);
}
