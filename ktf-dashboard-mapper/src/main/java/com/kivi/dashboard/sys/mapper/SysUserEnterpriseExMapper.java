package com.kivi.dashboard.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysUserEnterpriseExMapper {
	/**
	 * 根据ID查找所属企业ID
	 * 
	 * @param userId
	 * @return
	 */
	List<Long> selectEnterpriseIdByUserId(@Param("userId") Long userId);
}
