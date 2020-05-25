package com.kivi.dashboard.sys.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kivi.dashboard.sys.dto.SysApi3rdpartyDTO;

public interface SysApi3rdpartyExMapper {

	/**
	 * 分页查询
	 *
	 * @param page
	 * @param params
	 * @return
	 */
	IPage<SysApi3rdpartyDTO> selectByPage(IPage<SysApi3rdpartyDTO> page, @Param("params") Map<String, Object> params);

}
