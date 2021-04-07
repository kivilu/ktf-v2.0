package com.kivi.dashboard.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kivi.dashboard.sys.dto.SysIndustryDTO;

public interface SysIndustryExMapper {

	/**
	 * 分页查询
	 *
	 * @param page
	 * @param params
	 * @return
	 */
	IPage<SysIndustryDTO> selectDTO(IPage<SysIndustryDTO> page, @Param("params") Map<String, Object> params);

	/**
	 * 查询子节点
	 * 
	 * @param parentId
	 * @return
	 */
	List<SysIndustryDTO> getChildren(Map<String, Object> params);

}
