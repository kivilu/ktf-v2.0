package com.kivi.dashboard.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kivi.dashboard.sys.dto.SysDicDTO;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface SysDicExMapper {

	/**
	 * 查询DTO
	 * 
	 * @param varCode
	 * @param pVarCode
	 * @return
	 */
	SysDicDTO getDto(@Param("varCode") String varCode, @Param("pVarCode") String pVarCode);

	/**
	 * 根据条件查询
	 * 
	 * @param params
	 * @return
	 */
	IPage<SysDicDTO> selectDTO(IPage<SysDicDTO> page, @Param("params") Map<String, Object> params);

	/**
	 * 根据条件查询
	 * 
	 * @param params
	 * @return
	 */
	List<SysDicDTO> selectDTO(@Param("params") Map<String, Object> params);

	/**
	 * 树形数据查询
	 * 
	 * @param params
	 * @return
	 */
	List<SysDicDTO> treeQuery(@Param("params") Map<String, Object> params);

	/**
	 * 查询子节点
	 * 
	 * @param parentId
	 * @return
	 */
	List<SysDicDTO> getChildren(Map<String, Object> params);

	/**
	 * 查找子节点的代码列表
	 * 
	 * @param pVarCode
	 * @param ppId
	 * @return
	 */
	List<String> getChildrenCodes(@Param("pVarCode") String pVarCode, @Param("ppId") Long ppId);

	/**
	 * 查询父节点
	 * 
	 * @param params
	 * @return
	 */
	List<SysDicDTO> getParents(Map<String, Object> params);

	/**
	 * 获取value
	 * 
	 * @param varCode
	 * @param pVarCode
	 * @return
	 */
	List<Map<String, Object>> getKvMap(@Param("varCode") String varCode, @Param("pVarCodes") String[] pVarCodes);

	/**
	 * 删除节点以及其子节点
	 * 
	 * @param id
	 * @return
	 */
	int deleteWithChildren(@Param("id") Long id);
}
