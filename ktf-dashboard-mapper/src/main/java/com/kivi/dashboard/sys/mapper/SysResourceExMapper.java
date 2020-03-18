package com.kivi.dashboard.sys.mapper;

import java.util.List;
import java.util.Map;

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
}