package com.kivi.sys.shiro.jwt;

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

import com.kivi.framework.constant.enums.KtfStatus;
import com.kivi.framework.dto.JwtUserDTO;
import com.kivi.framework.vo.UserVo;
import com.kivi.framework.web.jwt.JwtKit;
import com.kivi.shiro.service.SysKit;
import com.kivi.sys.shiro.ShiroUser;
import com.kivi.sys.shiro.ShiroUserKit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtShiroRealm extends AuthorizingRealm {

    public JwtShiroRealm() {
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
        log.warn("Shiro权限设置使用默认设置, realm={}", super.getName());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        UserVo userVo = (UserVo)principals.getPrimaryPrincipal();
        ShiroUser shiroUser = ShiroUserKit.me().userVoToShiroUser(userVo);
        Set<String> roles = new HashSet<>();
        List<String> roleList = shiroUser.getRoleIds().stream().map(id -> id.toString()).collect(Collectors.toList());
        roles.addAll(roleList);
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(shiroUser.getUrlSet());
        log.trace("全部权限：{}", shiroUser.getUrlSet());
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

        JwtUserDTO jwtUser = null;
        try {
            jwtUser = JwtKit.getJwtUser(token);
        } catch (Exception e) {
            log.error("JWT token获取JwtUserDTO异常", e);
            throw new AuthenticationException("无效JwtToken");
        }

        UserVo user = SysKit.me().getUserById(jwtUser.getId());
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
