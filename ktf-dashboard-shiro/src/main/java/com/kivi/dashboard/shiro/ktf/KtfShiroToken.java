package com.kivi.dashboard.shiro.ktf;

import org.apache.shiro.authc.AuthenticationToken;

import com.kivi.framework.form.LoginForm;

public class KtfShiroToken implements AuthenticationToken {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final LoginForm form;

    public KtfShiroToken(LoginForm form) {
        this.form = form;
    }

    @Override
    public Object getPrincipal() {
        return form.getUserName();
    }

    @Override
    public Object getCredentials() {
        return form.getPassword();
    }

    public LoginForm getForm() {
        return this.form;
    }
}
