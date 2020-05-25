package com.kivi.dashboard.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
	 * 查找用户树
	 * 
	 * @return
	 */
	List<Map<String, Object>> selectUserTree();

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

	/**
	 * 查询用户的所有权限
	 *
	 * @param userId
	 * @return
	 */
	List<String> selectPerms(@Param("userId") Long userId);

	/**
	 * 分页查询
	 *
	 * @param page
	 * @param params
	 * @return
	 */
	IPage<Map<String, Object>>
			selectSysUserPage(IPage<Map<String, Object>> page, @Param("params") Map<String, Object> params);

}
