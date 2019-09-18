package com.kivi.dashboard.sys.service;

import com.kivi.dashboard.sys.entity.SysUserRole;
import com.kivi.dashboard.sys.dto.SysUserRoleDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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
}
