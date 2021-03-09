package com.kivi.dashboard.permission.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kivi.dashboard.permission.dto.SysResourceDTO;

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
	 * 查询菜单树数据
	 * 
	 * @param params
	 * @return
	 */
	List<SysResourceDTO> selectResourceList(@Param("params") Map<String, Object> params);

	/*
	 * 资源条件查询
	 * 
	 * 
	 * @param params 查询参数，可选项：resourceTypes:类型数组，hidden：是否显示，status：状态
	 * 
	 * @return
	 */
	IPage<SysResourceDTO> selectResources(IPage<SysResourceDTO> page, @Param("params") Map<String, Object> params);

	/*
	 * 查询用户的所有菜单
	 * 
	 * @param params params参数列表：
	 * userId：用户ID，resourceTypes:类型数组，hidden：是否显示，status：状态
	 * 
	 * @return
	 */
	List<SysResourceDTO> selectByUserId(@Param("params") Map<String, Object> params);

	/**
	 * 查询用户Urls
	 * 
	 * @param params params参数列表： roleIds：用户ID，hidden：是否显示，status：状态
	 * @return
	 */
	List<String> selectUrls(@Param("params") Map<String, Object> params);

	/**
	 * 查询资源ID
	 * 
	 * @param params
	 * @return
	 */
	List<Long> selectResourceIds(@Param("params") Map<String, Object> params);

	/**
	 * 查询子节点
	 * 
	 * @param parentId
	 * @return
	 */
	List<SysResourceDTO> getChildren(Map<String, Object> params);

	/**
	 * 查询父节点
	 * 
	 * @param parentId
	 * @return
	 */
	List<SysResourceDTO> getParents(Map<String, Object> params);

	/**
	 * 删除资源及其下属资源
	 * 
	 * @param id
	 * @return
	 */
	int deleteWithChildren(@Param("id") Long id);

	/**
	 * 删除没有父节点的资源，不包括parent_id=0的节点
	 */
	int deleteOrphan();

	/**
	 * 查询最大的ID
	 * 
	 * @param params
	 * @return
	 */
	Long getMaxId(Map<String, Object> params);

}
