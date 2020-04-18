package com.kivi.dashboard.enterprise.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 企业信息 Mapper 接口
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface EnterpriseExMapper {

	/**
	 * 多表页面信息查询
	 *
	 * @param page
	 * @param params
	 * @return
	 */
	IPage<Map<String, Object>>
			selectEnterprisePage(IPage<Map<String, Object>> page, @Param("params") Map<String, Object> params);

	/*
	 * 多表信息查询
	 * 
	 * @param params
	 * 
	 * @return
	 */
	List<Map<String, Object>> selectEnterpriseList(@Param("params") Map<String, Object> params);

}
