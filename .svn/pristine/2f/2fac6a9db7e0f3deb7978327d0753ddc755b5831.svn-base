package com.kivi.dashboard.shiro.service.impl;

import java.util.List;

import com.kivi.dashboard.shiro.service.ShiroUserService;
import com.kivi.dashboard.sys.service.ISysRoleService;
import com.kivi.dashboard.sys.service.ISysUserEnterpriseService;
import com.kivi.dashboard.sys.service.ISysUserService;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.service.KtfTokenService;
import com.kivi.framework.vo.RoleVo;
import com.kivi.framework.vo.UserVo;
import com.kivi.framework.web.jwt.JwtKit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShiroUserServiceImpl implements ShiroUserService {

	private final ISysUserService			userService;
	private final ISysRoleService			roleService;
	private final ISysUserEnterpriseService	userEnterpriseService;
	private final KtfTokenService			tokenService;

	public ShiroUserServiceImpl(ISysUserService userService, ISysRoleService roleService,
			ISysUserEnterpriseService userEnterpriseService, KtfTokenService tokenService) {
		this.userService			= userService;
		this.roleService			= roleService;
		this.userEnterpriseService	= userEnterpriseService;
		this.tokenService			= tokenService;
	}

	@Override
	public UserVo getUserByLoginName(String loginName) {
		return userService.selectByLoginName(loginName);
	}

	@Override
	public RoleVo getRoleById(Long roleId) {
		return roleService.selectByRoleId(roleId);
	}

	@Override
	public List<Long> getEnterpriseIdByUserId(Long userId) {
		return userEnterpriseService.selectEnterpriseIdByUserId(userId);
	}

	@Override
	public UserVo getUserById(Long userId) {
		return userService.selectByUserId(userId);
	}

	@Override
	public Boolean verifyAccessToken(String userId, String accessToken) {

		String token = tokenService.cache(userId);
		log.trace("从缓存中获取用户{}的token:{}", userId, token);
		if (token == null) {
			throw new KtfException(KtfError.E_UNAUTHORIZED, "登录状态已过期，请重新登录");
		}

		// 验证 token
		if (!JwtKit.verify(accessToken, token)) {
			log.error("验证JWT token失败");
			throw new KtfException(KtfError.E_UNAUTHORIZED, "用户尚未登录，请重新登录");
		}

		return true;
	}

}
