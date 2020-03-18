package com.kivi.dashboard.enterprise.mapper;

import java.util.List;
import java.util.Map;

import com.kivi.dashboard.enterprise.dto.EnterpriseDepartmentDTO;

/**
 * <p>
 * 企业部门 Mapper 接口
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface EnterpriseDepartmentExMapper {

	/**
	 * 树表
	 *
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectTreeGrid(Map<String, Object> params);

	/**
	 * 自定义查询
	 *
	 * @param params
	 * @return
	 */
	List<EnterpriseDepartmentDTO> selectEnterpriseDepartmentList(Map<String, Object> params);

}
