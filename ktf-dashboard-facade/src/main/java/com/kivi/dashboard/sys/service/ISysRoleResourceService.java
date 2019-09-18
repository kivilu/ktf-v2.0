package com.kivi.dashboard.sys.service;

import com.kivi.dashboard.sys.entity.SysRoleResource;
import com.kivi.dashboard.sys.dto.SysRoleResourceDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 角色资源 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface ISysRoleResourceService extends IService<SysRoleResource> {
 
    /**
     * 根据ID查询DTO
     */
 	SysRoleResourceDTO getDTOById(Long id);   
 	
 	/**
 	 * 新增
 	 */
 	Boolean save(SysRoleResourceDTO sysRoleResourceDTO);
 	
 	/**
 	 * 修改
 	 */
 	Boolean updateById(SysRoleResourceDTO sysRoleResourceDTO);
 	
 	/**
 	 * 查询列表
 	 */
 	List<SysRoleResourceDTO> list(SysRoleResourceDTO sysRoleResourceDTO);
 	
 	/**
	 * 指定列查询列表
	 */
	List<SysRoleResourceDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<SysRoleResourceDTO> listLike(SysRoleResourceDTO applicationDTO);

	/**
	 * 指定列模糊查询
	 */
	List<SysRoleResourceDTO> listLike(Map<String, Object> params, String... columns);
 	
 	/**
 	 * 分页查询
 	 */
 	PageInfoVO<SysRoleResourceDTO> page(Map<String, Object> params);
}
