package com.kivi.sys.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kivi.sys.sys.dto.SysRegionDTO;

public interface SysRegionExMapper {

	/**
	 * 分页查询
	 *
	 * @param page
	 * @param params
	 * @return
	 */
	IPage<SysRegionDTO> selectDTO(IPage<SysRegionDTO> page, @Param("params") Map<String, Object> params);

	/**
	 * 查询子节点
	 * 
	 * @param parentId
	 * @return
	 */
	List<SysRegionDTO> getChildren(Map<String, Object> params);

}
