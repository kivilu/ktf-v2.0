package com.kivi.dashboard.permission.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kivi.dashboard.permission.dto.SysUserDTO;
import com.kivi.framework.vo.UserVo;

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
	 * 根据用户名查找
	 * 
	 * @param loginName
	 * @return
	 */
	UserVo selectUserVo(@Param("params") Map<String, Object> map);

	/**
	 * 根据用户ID查找
	 * 
	 * @param userId
	 * @return
	 */
	SysUserDTO selectUserDto(@Param("params") Map<String, Object> map);

	/**
	 * 分页查询
	 *
	 * @param page
	 * @param params
	 * @return
	 */
	IPage<SysUserDTO> selectByPage(IPage<SysUserDTO> page, @Param("params") Map<String, Object> params);

	/**
	 * 查找用户树
	 * 
	 * @return
	 */
	// List<Map<String, Object>> selectUserTree();

	/**
	 * 查询用户的所有权限
	 *
	 * @param userId
	 * @return
	 */
	// List<String> selectPerms(@Param("userId") Long userId);

}
