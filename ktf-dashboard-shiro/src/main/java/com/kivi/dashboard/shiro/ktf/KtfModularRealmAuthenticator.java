package com.kivi.dashboard.shiro.ktf;

import java.util.Collection;
import java.util.Iterator;

import org.apache.dubbo.rpc.RpcException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import com.kivi.framework.exception.KtfException;
import com.kivi.framework.exception.KtfMockException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KtfModularRealmAuthenticator extends ModularRealmAuthenticator {
    /**
     * 扩展父类原方法，对异常立即进行外抛
     * 
     * @param realms
     * @param token
     * @return
     */
    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {
        AuthenticationStrategy strategy = this.getAuthenticationStrategy();
        AuthenticationInfo aggregate = strategy.beforeAllAttempts(realms, token);
        if (log.isTraceEnabled()) {
            log.trace("Iterating through {} realms for PAM authentication", realms.size());
        }
        Iterator<Realm> var5 = realms.iterator();
        AuthenticationException authenticationException = null;
        while (var5.hasNext()) {
            Realm realm = var5.next();
            aggregate = strategy.beforeAttempt(realm, token, aggregate);
            if (realm.supports(token)) {
                log.trace("Attempting to authenticate token [{}] using realm [{}]", token, realm);
                AuthenticationInfo info = null;
                Throwable t = null;
                try {
                    info = realm.getAuthenticationInfo(token);
                } catch (Throwable var11) {
                    t = var11;
                    if (t instanceof RpcException) {
                        if (log.isDebugEnabled()) {
                            String msg =
                                "Realm [" + realm + "] threw an exceptionduring a multi-realm authentication attempt:";
                            log.debug(msg, var11);
                            authenticationException = new AuthenticationException("后台服务不可用");
                        }
                    } else if (t instanceof KtfException || t instanceof KtfMockException) {

                        if (log.isDebugEnabled()) {
                            String msg =
                                "Realm [" + realm + "] threw an exceptionduring a multi-realm authentication attempt:";
                            log.debug(msg, var11);
                            authenticationException = new AuthenticationException(((KtfException)t).getTips());
                        }
                    } else {
                        authenticationException = (AuthenticationException)var11;
                        if (log.isDebugEnabled()) {
                            String msg =
                                "Realm [" + realm + "] threw an exceptionduring a multi-realm authentication attempt:";
                            log.debug(msg, var11);
                        }
                    }
                }
                aggregate = strategy.afterAttempt(realm, token, info, aggregate, t);
                // 增加此逻辑，只有authenticationException不为null，则表示有Realm较验到了异常，则立即中断后续Realm验证直接外抛
                if (authenticationException != null) {
                    throw authenticationException;
                }
            } else {
                log.debug("Realm [{}] does not support token {}.  Skipping realm.", realm, token);
            }
        }

        aggregate = strategy.afterAllAttempts(token, aggregate);
        return aggregate;
    }
}
