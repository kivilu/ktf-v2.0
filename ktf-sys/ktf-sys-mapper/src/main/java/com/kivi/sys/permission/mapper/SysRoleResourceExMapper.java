package com.kivi.sys.permission.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kivi.sys.permission.dto.SysRoleResourceDTO;

/**
 * <p>
 * 角色资源 Mapper 接口
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface SysRoleResourceExMapper {

	/**
	 * 查询选中node
	 * 
	 * @param roleId
	 * @return
	 */
	List<SysRoleResourceDTO> selectResourceNodeListByRoleId(@Param("roleId") Long roleId);
}
