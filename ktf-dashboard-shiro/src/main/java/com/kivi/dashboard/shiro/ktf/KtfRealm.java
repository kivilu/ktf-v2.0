package com.kivi.dashboard.shiro.ktf;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.shiro.ShiroUser;
import com.kivi.dashboard.shiro.ShiroUserKit;
import com.kivi.dashboard.shiro.service.ShiroUserService;
import com.kivi.framework.constant.enums.KtfStatus;
import com.kivi.framework.form.LoginForm;
import com.kivi.framework.service.KtfTokenService;
import com.kivi.framework.vo.UserVo;

import lombok.extern.slf4j.Slf4j;

/**
 * @Descriptin 身份校验核心类
 */
@Slf4j
public class KtfRealm extends AuthorizingRealm {

	private ShiroUserService	shiroUserService;

	private KtfTokenService		ktfTokenService;

	public KtfRealm(ShiroUserService shiroUserService, KtfTokenService ktfTokenService,
			CredentialsMatcher credentialsMatcher) {
		this.shiroUserService	= shiroUserService;
		this.ktfTokenService	= ktfTokenService;
		this.setCredentialsMatcher(credentialsMatcher);
	}

	/**
	 * 找它的原因是这个方法返回true
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof KtfShiroToken;
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
		LoginForm	form		= ((KtfShiroToken) token).getForm();
		// 获取用户的输入的账号.
		String		loginName	= (String) token.getPrincipal();
		log.info("Shiro开始对用户{}进行身份验证", loginName);

		// 通过loginName从数据库中查找 UserVo对象
		UserVo userVo = shiroUserService.getUserByLoginName(loginName);
		if (null == userVo) {
			log.error("账号{}不存在", loginName);
			throw new AuthenticationException("用户名或者密码错误");
		}

		// 当企业不存在或者企业被禁用不允许登录
		/*
		 * if (userVo.getUserType() != UserType.SYS.value) { OrgCorp orgCorp =
		 * orgCorpService().getById(userVo.getOrgId()); if (null != orgCorp &&
		 * orgCorp.getStatus() == KtfStatus.DISABLED.code) { throw new
		 * AuthenticationException("企业被禁用，该账户不允许登录"); } else if (null == orgCorp) {
		 * throw new AuthenticationException("企业不存在，该账户不允许登录"); } }
		 */
		// 判断用户状态
		if (KtfStatus.LOCKED.code == userVo.getStatus()) {
			// 用户已被禁用
			throw new AuthenticationException("用户已被禁用");
		}

		userVo.setAuthType(form.getType());

		// ShiroUser su = shiroUserKit.userVoToShiroUser(userVo);

		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userVo, form, getName());
		return authenticationInfo;
	}

	@Override
	public void onLogout(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
		log.info("从session中获取的LoginName：{}", ShiroKit.getUser().getLoginName());
		removeUserCache(ShiroKit.getUser());

	}

	/**
	 * 清除用户缓存
	 *
	 * @param shiroUser
	 */
	public void removeUserCache(ShiroUser shiroUser) {
		// 生成一个token
		ktfTokenService.evictJwt(shiroUser.getId().toString());
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

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		log.warn("Shiro权限设置使用默认设置,, realm={}", super.getName());
		SimpleAuthorizationInfo	simpleAuthorizationInfo	= new SimpleAuthorizationInfo();
		UserVo					userVo					= (UserVo) principals.getPrimaryPrincipal();
		ShiroUser				shiroUser				= ShiroUserKit.me().userVoToShiroUser(userVo);
		Set<String>				roles					= new HashSet<>();
		List<String>			roleList				= shiroUser.getRoleIds().stream().map(id -> id.toString())
				.collect(Collectors.toList());
		roles.addAll(roleList);
		simpleAuthorizationInfo.setRoles(roles);
		simpleAuthorizationInfo.addStringPermissions(shiroUser.getUrlSet());

		log.trace("全部权限：{}", shiroUser.getUrlSet());

		return simpleAuthorizationInfo;
	}

	/**
	 * 清理权限缓存
	 */
	public void clearCachedAuthorization() {
		// 清空权限缓存
		clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
	}

}
