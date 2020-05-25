package com.kivi.dashboard.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kivi.dashboard.sys.entity.SysMenu;
import com.kivi.framework.vo.ResourceVo;

/**
 * <p>
 * 资源 Mapper 接口
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface SysResourceExMapper {

	/**
	 * 查询菜单
	 * 
	 * @param params
	 * @return
	 */
	List<ResourceVo> selectResourceList(Map<String, Object> params);

	/*
	 * 查询用户的所有菜单ID
	 * 
	 * @param userId
	 * 
	 * @return
	 */
	List<Long> selectResourceIdListByUserId(@Param("userId") Long userId);

	/*
	 * 查询用户的所有菜单
	 * 
	 * @param userId
	 * 
	 * @return
	 */
	List<SysMenu> selectResourceListByUserId(@Param("userId") Long userId);
}
