package com.kivi.dashboard.org.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kivi.dashboard.org.dto.OrgDeptDTO;

/**
 * <p>
 * 企业部门 Mapper 接口
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface OrgDeptExMapper {

	/**
	 * 分页查询
	 * 
	 * @param parentId
	 * @return
	 */
	IPage<OrgDeptDTO> selectDTO(IPage<OrgDeptDTO> page, @Param("params") Map<String, Object> params);

	/**
	 * 查询子节点
	 * 
	 * @param parentId
	 * @return
	 */
	List<OrgDeptDTO> getChildren(Map<String, Object> params);

	/**
	 * 查询父节点
	 * 
	 * @param params
	 * @return
	 */
	List<OrgDeptDTO> getParents(Map<String, Object> params);

	/**
	 * 删除节点以及其子节点
	 * 
	 * @param id
	 * @return
	 */
	int deleteWithChildren(@Param("id") Long id);

	/**
	 * 删除没有父节点的节点
	 * 
	 * @return
	 */
	int deleteOrphan();

}
