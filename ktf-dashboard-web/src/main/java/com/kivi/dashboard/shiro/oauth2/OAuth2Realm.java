package com.kivi.dashboard.shiro.oauth2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
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
import com.kivi.dashboard.sys.entity.SysResource;
import com.kivi.dashboard.sys.entity.SysRole;
import com.kivi.dashboard.sys.entity.SysUserToken;
import com.kivi.dashboard.sys.entity.vo.UserVo;
import com.kivi.dashboard.sys.service.ISysRoleService;
import com.kivi.dashboard.sys.service.ISysUserEnterpriseService;
import com.kivi.dashboard.sys.service.ISysUserService;
import com.kivi.dashboard.sys.service.ISysUserTokenService;
import com.kivi.framework.util.kit.DateTimeKit;
import com.vip.vjtools.vjkit.collection.ListUtil;
import com.vip.vjtools.vjkit.time.ClockUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @Descriptin 身份校验核心类
 */
@Slf4j
public class OAuth2Realm extends AuthorizingRealm {

	@Autowired
	private ISysUserService				userService;
	@Autowired
	private ISysUserTokenService		userTokenService;
	@Autowired
	ISysRoleService						roleService;
	@Autowired
	private ISysUserEnterpriseService	userEnterpriseService;

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
		String			accessToken	= (String) token.getPrincipal();
		SysUserToken	userToken	= userTokenService.selectByToken(accessToken);
		if (null == userToken || ClockUtil.elapsedTime(DateTimeKit.toMilliseconds(userToken.getExpireTime())) > 0) {
			throw new IncorrectCredentialsException("token失效，请重新登录");
		}
		// 通过UserId从数据库中查找 UserVo对象
		UserVo userVo = userService.selectByUserId(userToken.getUserId());
		// 账号不存在
		if (null == userVo) {
			throw new IncorrectCredentialsException("账号不存在");
		}
		// 账号未启用
		if (userVo.getStatus() == 1) {
			throw new LockedAccountException("账号未启用");
		}
		ShiroUser					su					= userVoToShiroUser(userVo);
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
		List<String>			roleList	= shiroUser.getRoles();
		roles.addAll(roleList);
		info.setRoles(roles);
		info.addStringPermissions(shiroUser.getUrlSet());
		return info;
	}

	/**
	 * 将UserVo赋值给shiroUser
	 *
	 * @param userVo
	 * @return
	 */
	public ShiroUser userVoToShiroUser(UserVo userVo) {
		if (userVo == null) {
			return null;
		} else {
			ShiroUser su = new ShiroUser();
			su.setId(userVo.getId());
			su.setName(userVo.getName());
			su.setLoginName(userVo.getLoginName());
			su.setUserType(userVo.getUserType());
			su.setStatus(userVo.getStatus());
			su.setIsLeader(userVo.getIsLeader());
			List<SysRole>	rvList	= userVo.getRoles();
			List<String>	urlSet	= new ArrayList<>();
			List<String>	roles	= new ArrayList<>();
			if (rvList != null && !rvList.isEmpty()) {
				for (SysRole rv : rvList) {
					roles.add(rv.getName());
					List<SysResource> rList = roleService.selectByRoleId(rv.getId()).getPermissions();
					if (rList != null && !rList.isEmpty()) {
						for (SysResource r : rList) {
							if (StringUtils.isNotBlank(r.getUrl())) {
								urlSet.add(r.getUrl());
							}
						}
					}
				}
			}
			su.setRoles(roles);
			su.setUrlSet(urlSet);
			List<Long>	enterpriseIdList	= new ArrayList<>();
			List<Long>	enterpriseIds		= userEnterpriseService.selectEnterpriseIdByUserId(userVo.getId());
			if (enterpriseIds != null && enterpriseIds.size() > 0) {
				enterpriseIdList.addAll(enterpriseIds);
			}
			if (userVo.getEnterpriseId() != null) {
				enterpriseIdList.add(userVo.getEnterpriseId());
			}
			su.setEnterpriseIdList(removeDuplicate(enterpriseIdList));
			su.setEnterpriseId(userVo.getEnterpriseId());
			su.setDepartmentId(userVo.getDepartmentId());
			su.setJobId(userVo.getJobId());
			return su;
		}
	}

	/**
	 * list去重复
	 *
	 * @param list
	 * @return
	 */
	public static List<Long> removeDuplicate(List<Long> list) {
		ListUtil.uniqueNotNullList(list);
		return list;
	}
}
