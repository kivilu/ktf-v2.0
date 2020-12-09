package com.kivi.dashboard.shiro.service;

import java.util.List;
import java.util.Set;

import com.kivi.framework.vo.UserVo;

public interface ShiroUserService {

	/**
	 * 根据登录名查找用户
	 * 
	 * @param loginName
	 * @return
	 */
	UserVo getUserByLoginName(String loginName);

	/**
	 * 根据登录名查找用户
	 * 
	 * @param userId
	 * @return
	 */
	UserVo getUserById(Long userId);

	/**
	 * 根据角色ID查询对应的权限URL
	 * 
	 * @param roleIds
	 * @return
	 */
	Set<String> getPermissions(List<Long> roleIds);

	/**
	 * 根据角色ID查询角色信息
	 * 
	 * @param roleId
	 * @return
	 */
	// RoleVo getRoleById(Long roleId);

	/**
	 * 根据ID查找所属企业ID
	 * 
	 * @param userId
	 * @return
	 */
	// List<Long> getEnterpriseIdByUserId(Long userId);

}
