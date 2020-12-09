package com.kivi.dashboard.shiro.service.impl;

import java.util.List;
import java.util.Set;

import com.kivi.dashboard.permission.service.SysResourceService;
import com.kivi.dashboard.permission.service.SysUserOrgService;
import com.kivi.dashboard.permission.service.SysUserService;
import com.kivi.dashboard.shiro.service.ShiroUserService;
import com.kivi.framework.vo.UserVo;

public class ShiroUserServiceImpl implements ShiroUserService {

	private final SysUserService		userService;
	private final SysResourceService	resourceService;
	private final SysUserOrgService		userOrgService;

	public ShiroUserServiceImpl(SysUserService userService, SysResourceService resourceService,
			SysUserOrgService userOrgService) {
		this.userService		= userService;
		this.resourceService	= resourceService;
		this.userOrgService		= userOrgService;
	}

	@Override
	public UserVo getUserByLoginName(String loginName) {
		return userService.getUserVo(loginName);
	}

	@Override
	public UserVo getUserById(Long userId) {
		return userService.getUserVo(userId);
	}

	@Override
	public Set<String> getPermissions(List<Long> roleIds) {
		return resourceService.getPermissions(roleIds);
	}

	/*
	 * @Override public RoleVo getRoleById(Long roleId) { return
	 * roleService.selectByRoleId(roleId); }
	 * 
	 * @Override public List<Long> getEnterpriseIdByUserId(Long userId) { return
	 * userEnterpriseService.selectEnterpriseIdByUserId(userId); }
	 */

}
