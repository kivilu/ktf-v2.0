package com.kivi.dashboard.permission.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kivi.dashboard.permission.dto.SysRoleDTO;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface SysRoleExMapper {

	SysRoleDTO selectByRoleId(@Param("roleId") Long roleId);

	/**
	 * 分页查询
	 * 
	 * @param map
	 * @return
	 */
	IPage<SysRoleDTO> selectRoles(IPage<SysRoleDTO> page, @Param("params") Map<String, Object> params);
	// RoleVo selectByUserId(@Param("userId") Long userId);

}
