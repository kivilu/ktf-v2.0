package com.kivi.dashboard.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.dashboard.sys.dto.SysRoleDTO;
import com.kivi.dashboard.sys.entity.SysRole;
import com.kivi.framework.vo.RoleVo;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface ISysRoleService extends IService<SysRole> {

	/**
	 * 根据ID查询DTO
	 */
	SysRoleDTO getDTOById(Long id);

	/**
	 * 新增
	 */
	Boolean save(SysRoleDTO sysRoleDTO);

	/**
	 * 新增
	 */
	Boolean saveByVo(SysRoleDTO sysRoleDTO);

	/**
	 * 修改
	 */
	Boolean updateById(SysRoleDTO sysRoleDTO);

	/**
	 * 修改
	 */
	Boolean updateByVo(SysRoleDTO sysRoleDTO);

	/**
	 * 查询列表
	 */
	List<SysRoleDTO> list(SysRoleDTO sysRoleDTO);

	/**
	 * 指定列查询列表
	 */
	List<SysRoleDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<SysRoleDTO> listLike(SysRoleDTO applicationDTO);

	/**
	 * 指定列模糊查询
	 */
	List<SysRoleDTO> listLike(Map<String, Object> params, String... columns);

	/**
	 * 分页查询
	 */
	PageInfoVO<SysRoleDTO> page(Map<String, Object> params);

	/**
	 * 根据角色ID查找角色信息
	 * 
	 * @param roleId
	 * @return
	 */
	RoleVo selectByRoleId(Long roleId);

	/**
	 * 批量删除
	 * 
	 * @param roleIds
	 */
	void deleteBatch(Long[] roleIds);

}
