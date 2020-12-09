package com.kivi.dashboard.shiro.cas;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.beans.factory.annotation.Autowired;

import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.shiro.ShiroUser;
import com.kivi.dashboard.shiro.ShiroUserKit;
import com.kivi.dashboard.shiro.service.ShiroUserService;
import com.kivi.framework.vo.UserVo;

import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.subject.Pac4jPrincipal;
import io.buji.pac4j.token.Pac4jToken;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShiroCasRealm extends Pac4jRealm {

	@Autowired
	private ShiroUserService	shiroUserService;

	@Autowired
	ShiroUserKit				shiroUserKit;

	public ShiroCasRealm() {
		super();
	}

	/**
	 * Shiro登录认证(原理：用户提交 用户名和密码 --- shiro 封装令牌 ---- realm 通过用户名将密码查询返回 ---- shiro
	 * 自动去比较查询出密码和用户输入密码是否一致---- 进行登陆控制 )
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {

		log.info("Shiro开始登录认证");
		Pac4jToken			token		= (Pac4jToken) authcToken;
		List<CommonProfile>	profiles	= token.getProfiles();
		Pac4jPrincipal		principal	= new Pac4jPrincipal(profiles);

		String				loginName	= principal.getProfile().getId();
		UserVo				userVo		= shiroUserService.getUserByLoginName(loginName);
		// 账号不存在
		if (userVo == null) {
			return null;
		}
		// 账号未启用
		if (userVo.getStatus() == 1) {
			return null;
		}
		ShiroUser su = shiroUserKit.userVoToShiroUser(userVo);
		// 认证缓存信息
		return new SimpleAuthenticationInfo(su, profiles.hashCode(), getName());
	}

	/**
	 * Shiro权限认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		log.info("Shiro开始权限配置");
		ShiroUser				shiroUser	= (ShiroUser) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo	info		= new SimpleAuthorizationInfo();
		Set<String>				roles		= new HashSet<>();
		List<String>			roleList	= shiroUser.getRoleIds().stream().map(id -> id.toString())
				.collect(Collectors.toList());
		roles.addAll(roleList);
		info.setRoles(roles);
		info.addStringPermissions(shiroUser.getUrlSet());
		return info;
	}

	@Override
	public void onLogout(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
		log.info("从session中获取的LoginName：" + ShiroKit.getUser().getLoginName());
		removeUserCache(ShiroKit.getUser());

	}

	/**
	 * 清除用户缓存
	 *
	 * @param shiroUser
	 */
	public void removeUserCache(ShiroUser shiroUser) {
		removeUserCache(shiroUser.getLoginName());
	}

	/**
	 * 清除用户缓存
	 *
	 * @param loginName
	 */
	public void removeUserCache(String loginName) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection();
		principals.add(loginName, super.getName());
		super.clearCachedAuthenticationInfo(principals);
	}

}
