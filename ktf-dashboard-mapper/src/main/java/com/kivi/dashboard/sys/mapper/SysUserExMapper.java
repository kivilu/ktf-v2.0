package com.kivi.dashboard.sys.mapper;

import org.apache.ibatis.annotations.Param;

import com.kivi.dashboard.sys.entity.vo.UserVo;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface SysUserExMapper {

	/**
	 * 根据用户ID查找
	 * 
	 * @param userId
	 * @return
	 */
	UserVo selectByUserId(@Param("userId") Long userId);

	/**
	 * 根据用户名查找
	 * 
	 * @param loginName
	 * @return
	 */
	UserVo selectByLoginName(@Param("loginName") String loginName);

}
