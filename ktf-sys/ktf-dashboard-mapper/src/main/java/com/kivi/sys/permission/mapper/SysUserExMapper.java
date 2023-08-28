package com.kivi.sys.permission.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kivi.framework.vo.UserVo;
import com.kivi.sys.permission.dto.SysUserDTO;

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
	 * 分页查询简单信息
	 *
	 * @param page
	 * @param params
	 * @return
	 */
	IPage<SysUserDTO> selectSimpleByPage(IPage<SysUserDTO> page, @Param("params") Map<String, Object> params);

}
