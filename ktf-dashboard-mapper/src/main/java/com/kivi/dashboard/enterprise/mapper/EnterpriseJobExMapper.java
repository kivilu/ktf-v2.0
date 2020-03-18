package com.kivi.dashboard.enterprise.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kivi.dashboard.enterprise.dto.EnterpriseJobDTO;

/**
 * <p>
 * 企业职务配置 Mapper 接口
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface EnterpriseJobExMapper {

	/**
	 * 多表页面信息查询
	 *
	 * @param page
	 * @param params
	 * @return
	 */
	IPage<Map<String, Object>>
			selectEnterpriseJobPage(IPage<Map<String, Object>> page, @Param("params") Map<String, Object> params);

	/**
	 * 自定义查询
	 *
	 * @param params
	 * @return
	 */
	List<EnterpriseJobDTO> selectEnterpriseJobList(@Param("params") Map<String, Object> params);

}
