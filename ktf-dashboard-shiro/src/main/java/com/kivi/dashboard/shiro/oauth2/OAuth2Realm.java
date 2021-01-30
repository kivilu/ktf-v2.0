package com.kivi.dashboard.shiro.oauth2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.kivi.dashboard.shiro.ShiroUser;
import com.kivi.dashboard.shiro.ShiroUserKit;
import com.kivi.dashboard.shiro.service.ShiroUserService;
import com.kivi.framework.vo.UserVo;

import lombok.extern.slf4j.Slf4j;

/**
 * @Descriptin 身份校验核心类
 */
@Slf4j
public class OAuth2Realm extends AuthorizingRealm {

	@Autowired
	private ShiroUserService	shiroUserService;

	@Autowired
	ShiroUserKit				shiroUserKit;

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof OAuth2Token;
	}

	/**
	 * 认证信息.(身份验证) Authentication 是用来验证用户身份
	 *
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		log.info("Shiro开始权限认证");
		String	userId		= (String) token.getPrincipal();
		String	accessToken	= (String) token.getCredentials();

		/*
		 * try { shiroUserService.verifyAccessToken(userId, accessToken); } catch
		 * (KtfException e) { throw new IncorrectCredentialsException(e.getTips()); }
		 */

		// 通过UserId从数据库中查找 UserVo对象
		UserVo	userVo		= shiroUserService.getUserById(Long.parseLong(userId));
		// 账号不存在
		if (null == userVo) {
			throw new IncorrectCredentialsException("账号不存在");
		}
		// 账号未启用
		if (userVo.getStatus() == 1) {
			throw new LockedAccountException("账号未启用");
		}
		ShiroUser					su					= shiroUserKit.userVoToShiroUser(userVo);
		SimpleAuthenticationInfo	authenticationInfo	= new SimpleAuthenticationInfo(su, accessToken, getName());
		return authenticationInfo;
	}

	/**
	 * 此方法调用hasRole,hasPermission的时候才会进行回调.
	 * <p>
	 * 权限信息.(授权): 1、如果用户正常退出，缓存自动清空； 2、如果用户非正常退出，缓存自动清空；
	 * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。 （需要手动编程进行实现；放在service进行调用）
	 * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，调用clearCached方法；
	 * Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
	 *
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		log.info("Shiro开始权限配置");
		ShiroUser				shiroUser	= JSON.parseObject(principals.getPrimaryPrincipal().toString(),
				ShiroUser.class);
		SimpleAuthorizationInfo	info		= new SimpleAuthorizationInfo();
		Set<String>				roles		= new HashSet<>();
		List<String>			roleList	= shiroUser.getRoleIds().stream().map(id -> id.toString())
				.collect(Collectors.toList());
		roles.addAll(roleList);
		info.setRoles(roles);
		info.addStringPermissions(shiroUser.getUrlSet());
		return info;
	}

}
