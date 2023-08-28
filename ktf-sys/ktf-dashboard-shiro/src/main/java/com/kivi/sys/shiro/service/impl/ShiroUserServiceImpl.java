package com.kivi.sys.shiro.service.impl;

import java.util.List;
import java.util.Set;

import com.kivi.cif.service.CifCustomerAuthsService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.vo.UserVo;
import com.kivi.sys.permission.service.SysResourceService;
import com.kivi.sys.permission.service.SysUserOrgService;
import com.kivi.sys.permission.service.SysUserService;
import com.kivi.sys.shiro.service.ShiroUserService;

public class ShiroUserServiceImpl implements ShiroUserService {

	private final SysUserService			userService;
	private final SysResourceService		resourceService;
	private final SysUserOrgService			userOrgService;
	private final CifCustomerAuthsService	cifAuthService;

	public ShiroUserServiceImpl(SysUserService userService, SysResourceService resourceService,
			SysUserOrgService userOrgService, CifCustomerAuthsService cifAuthService) {
		this.userService		= userService;
		this.resourceService	= resourceService;
		this.userOrgService		= userOrgService;
		this.cifAuthService		= cifAuthService;
	}

	@Override
	public UserVo getUserByLoginName(String loginName) {
		return userService.getUserVo(loginName);
	}

	@Override
	public UserVo getUserById(Long userId) {
		return userService.getUserVo(userId);
	}

	@KtfTrace("获取角色权限")
	@Override
	public Set<String> getPermissions(List<Long> roleIds) {
		return resourceService.getPermissions(roleIds);
	}

	@Override
	public CifCustomerAuthsService cifAuthService() {
		return this.cifAuthService;
	}

}
