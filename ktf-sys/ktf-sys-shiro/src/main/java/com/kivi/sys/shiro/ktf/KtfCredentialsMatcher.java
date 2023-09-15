package com.kivi.sys.shiro.ktf;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import com.kivi.framework.vo.UserVo;
import com.kivi.shiro.service.SysKit;

public class KtfCredentialsMatcher implements CredentialsMatcher {

    public KtfCredentialsMatcher() {}

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        KtfShiroToken ktfToken = (KtfShiroToken)token;
        UserVo userVo = (UserVo)info.getPrincipals().getPrimaryPrincipal();
        Integer authType = SysKit.me().cifAuthService().auth(ktfToken.getForm(), userVo);
        userVo.setAuthType(authType);
        return true;

    }

}
