package com.kivi.dashboard.shiro.ktf;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import com.kivi.cif.service.CifCustomerAuthsService;
import com.kivi.framework.vo.UserVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KtfCredentialsMatcher implements CredentialsMatcher {

    private CifCustomerAuthsService cifAuthService;

    public KtfCredentialsMatcher(CifCustomerAuthsService cifAuthService) {
        this.cifAuthService = cifAuthService;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        KtfShiroToken ktfToken = (KtfShiroToken)token;
        UserVo userVo = (UserVo)info.getPrincipals().getPrimaryPrincipal();
        try {
            Integer authType = cifAuthService.auth(ktfToken.getForm(), userVo);
            userVo.setAuthType(authType);
            return true;
        } catch (Exception e) {
            log.error("登录验证异常", e);
        }
        return false;
    }

}
