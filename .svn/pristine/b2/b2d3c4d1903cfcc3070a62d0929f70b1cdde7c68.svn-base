package com.kivi.dashboard.shiro.none;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kivi.dashboard.shiro.RetryLimitCredentialsMatcher;
import com.kivi.dashboard.shiro.cache.ShiroRedisCacheManager;
import com.kivi.framework.properties.KtfDashboardProperties;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * 用于放开全部权限的情况
 * 
 * @Description Apache Shiro配置类
 */

@ConditionalOnProperty(
		prefix = KtfDashboardProperties.PREFIX,
		value = "auth",
		havingValue = "none",
		matchIfMissing = false)
@Configuration
public class ShiroConfig {

	@Autowired
	private KtfDashboardProperties ktfDashboardProperties;

	/**
	 * ShiroFilterFactoryBean 处理拦截资源文件问题。 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
	 * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager Filter Chain定义说明
	 * 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过 3、部分过滤器可指定参数，如perms，roles
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		Map<String, Filter> filtersMap = shiroFilterFactoryBean.getFilters();
		shiroFilterFactoryBean.setFilters(filtersMap);
		// 拦截器
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

		filterChainDefinitionMap.put("/**", "anon");

		shiroFilterFactoryBean.setLoginUrl(ktfDashboardProperties.getShiro().getLoginUrl());
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl(ktfDashboardProperties.getShiro().getSuccessUrl());

		shiroFilterFactoryBean.setUnauthorizedUrl(ktfDashboardProperties.getShiro().getUnauthorizedUrl());

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;
	}

	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		return securityManager;
	}

	/**
	 * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
	 */
	@Bean
	public SimpleAccountRealm shiroDBRealm() {
		SimpleAccountRealm shiroDBRealm = new SimpleAccountRealm();

		return shiroDBRealm;
	}

	/**
	 * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
	 * 所以我们需要修改下doGetAuthenticationInfo中的代码; @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher(@Autowired ShiroRedisCacheManager shiroRedisCacheManager) {
		HashedCredentialsMatcher hashedCredentialsMatcher = new RetryLimitCredentialsMatcher(shiroRedisCacheManager);
		hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(2);// 散列的次数，比如散列两次，相当于md5(md5(""));
		hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);// 表示是否存储散列后的密码为16进制，需要和生成密码时的一样，默认是base64；
		return hashedCredentialsMatcher;
	}

	/**
	 * 开启shiro aop注解支持. 使用代理方式; 所以需要开启代码支持;
	 *
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

	@Bean(name = "shiroDialect")
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

}
