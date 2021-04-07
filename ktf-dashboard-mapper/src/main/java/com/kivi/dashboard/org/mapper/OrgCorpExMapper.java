package com.kivi.dashboard.org.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kivi.dashboard.org.dto.OrgCorpDTO;

/**
 * <p>
 * 企业信息 Mapper 接口
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface OrgCorpExMapper {

	/**
	 * 多表页面信息查询
	 *
	 * @param page
	 * @param params
	 * @return
	 */
	IPage<OrgCorpDTO> selectDTO(IPage<OrgCorpDTO> page, @Param("params") Map<String, Object> params);

	/*
	 * 多表信息查询
	 *
	 * @param params
	 *
	 * @return
	 */
	List<OrgCorpDTO> selectDTO(@Param("params") Map<String, Object> params);

}
