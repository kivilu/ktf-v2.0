package com.kivi.dashboard.shiro.jwt;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import com.kivi.dashboard.shiro.ShiroUserKit;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.vo.UserVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTCredentialsMatcher implements CredentialsMatcher {

    /**
     * Matcher中直接调用工具包中的verify方法即可
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        String token = (String)authenticationToken.getCredentials();
        // Object stored = authenticationInfo.getCredentials();
        // String salt = stored.toString();

        UserVo user = (UserVo)authenticationInfo.getPrincipals().getPrimaryPrincipal();
        try {
            ShiroUserKit.me().verifyAccessToken(user.getId().toString(), token);
            return true;
        } catch (KtfException e) {
            log.error("Token Error:{}", e);
        } catch (Exception e) {
            log.error("Token Error:{}", e);
        }
        return false;
    }

}
