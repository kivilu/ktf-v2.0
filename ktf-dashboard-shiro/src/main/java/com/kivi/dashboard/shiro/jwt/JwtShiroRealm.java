package com.kivi.dashboard.shiro.jwt;

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

import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.shiro.ShiroUser;
import com.kivi.dashboard.shiro.service.ShiroUserService;
import com.kivi.framework.constant.enums.KtfStatus;
import com.kivi.framework.vo.UserVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtShiroRealm extends AuthorizingRealm {

    private ShiroUserService shiroUserService;

    public JwtShiroRealm(ShiroUserService shiroUserService) {
        this.shiroUserService = shiroUserService;
        // 这里使用我们自定义的Matcher
        this.setCredentialsMatcher(new JWTCredentialsMatcher());
    }

    /**
     * 限定这个Realm只支持我们自定义的JWT Token
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.trace("Shiro权限设置使用默认设置, realm={}", super.getName());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        return simpleAuthorizationInfo;
    }

    /**
     * 更controller登录一样，也是获取用户的salt值，给到shiro，由shiro来调用matcher来做认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
        throws AuthenticationException {
        JwtToken jwtToken = (JwtToken)authcToken;
        String token = jwtToken.getPrincipal();

        ShiroUser shiroUser = ShiroKit.getUser();

        UserVo user = shiroUserService.getUserById(shiroUser.getId());
        // 账号不存在
        if (null == user) {
            throw new IncorrectCredentialsException("账号不存在");
        }
        // 账号未启用
        if (user.getStatus() == KtfStatus.DISABLED.code) {
            throw new LockedAccountException("账号未启用");
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, token, super.getName());

        return authenticationInfo;
    }

}
