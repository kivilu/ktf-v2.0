package com.kivi.dashboard.permission.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysUserOrgExMapper {
	/**
	 * 根据ID查找所属企业ID
	 * 
	 * @param userId
	 * @return
	 */
	List<Long> selectOrgIdByUserId(@Param("userId") Long userId);
}
