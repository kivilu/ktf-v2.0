package com.kivi.dashboard.shiro.configure;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kivi.dashboard.shiro.RetryLimitCredentialsMatcher;
import com.kivi.dashboard.shiro.ShiroDBRealm;
import com.kivi.dashboard.shiro.base.ShiroBaseConfig;
import com.kivi.dashboard.shiro.cache.ShiroRedisCacheManager;
import com.kivi.dashboard.shiro.cache.ShiroRedisSessionDAO;
import com.kivi.framework.properties.KtfDashboardProperties;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * @Description Apache Shiro配置类
 */

@ConditionalOnProperty(
		prefix = KtfDashboardProperties.PREFIX,
		value = "auth",
		havingValue = "shiro",
		matchIfMissing = false)
@AutoConfigureAfter({ ShiroRedisConfig.class })
@Configuration
public class ShiroConfig extends ShiroBaseConfig {

	@Autowired
	private KtfDashboardProperties	ktfDashboardProperties;

	@Autowired
	ShiroRedisCacheManager			shiroRedisCacheManager;

	@Autowired
	ShiroRedisSessionDAO			shiroRedisSessionDAO;

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

		filterChainDefinitionMap.put("/logout", "logout");
		// 配置记住我或认证通过可以访问的地址
		filterChainDefinitionMap.put("/index", "user");
		filterChainDefinitionMap.put("/", "user");

		super.filterChainAnonMap(filterChainDefinitionMap);

		filterChainDefinitionMap.put("/**", "authc");

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
		// 设置realm.
		securityManager.setRealm(shiroDBRealm());
		// 注入Session管理器
		securityManager.setSessionManager(sessionManager(shiroRedisSessionDAO));
		// 注入缓存管理器
		securityManager.setCacheManager(shiroRedisCacheManager);
		// 注入记住我管理器;
		securityManager.setRememberMeManager(rememberMeManager());
		return securityManager;
	}

	/**
	 * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
	 */
	@Bean
	public ShiroDBRealm shiroDBRealm() {
		ShiroDBRealm shiroDBRealm = new ShiroDBRealm();
		shiroDBRealm.setCredentialsMatcher(hashedCredentialsMatcher(shiroRedisCacheManager));
		shiroDBRealm.setCacheManager(shiroRedisCacheManager);
		// 启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
		shiroDBRealm.setAuthenticationCachingEnabled(true);
		// 缓存AuthenticationInfo信息的缓存名称
		shiroDBRealm.setAuthenticationCacheName("authenticationCache");
		// 缓存AuthorizationInfo信息的缓存名称
		shiroDBRealm.setAuthorizationCacheName("authorizationCache");
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
	 * cookie对象
	 *
	 * @return
	 */
	@Bean
	public SimpleCookie sessionIdCookie() {
		SimpleCookie simpleCookie = new SimpleCookie();
		// 设置Cookie的过期时间，秒为单位，默认-1表示关闭浏览器时过期Cookie
		simpleCookie.setMaxAge(ktfDashboardProperties.getCookie().getMaxAge());
		// 设置Cookie名字，默认为JSESSIONID
		simpleCookie.setName("session-z-id");
		simpleCookie.setPath(ktfDashboardProperties.getCookie().getPath());
		simpleCookie.setHttpOnly(true);
		return simpleCookie;
	}

	/**
	 * cookie对象;
	 *
	 * @return
	 */
	@Bean
	public SimpleCookie rememberMeCookie() {
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		// 记住我cookie生效时间1小时 ,单位秒
		simpleCookie.setMaxAge(ktfDashboardProperties.getCookie().getMaxAge());
		simpleCookie.setPath(ktfDashboardProperties.getCookie().getPath());
		simpleCookie.setHttpOnly(true);
		return simpleCookie;
	}

	/**
	 * cookie管理对象;
	 *
	 * @return
	 */
	@Bean
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		cookieRememberMeManager.setCipherKey(Base64.decode("5aaC5qKm5oqA5pyvAAAAAA=="));
		return cookieRememberMeManager;
	}

	@Bean(name = "sessionManager")
	public SessionManager sessionManager(ShiroRedisSessionDAO shiroRedisSessionDAO) {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(60 * 60 * 1 * 1 * 1000);
		sessionManager.setSessionDAO(shiroRedisSessionDAO);
		// url中是否显示session Id
		sessionManager.setSessionIdUrlRewritingEnabled(false);
		// 删除失效的session
		sessionManager.setDeleteInvalidSessions(true);

		// 会话验证
		sessionManager.setSessionValidationScheduler(getExecutorServiceSessionValidationScheduler());
		sessionManager.setSessionValidationSchedulerEnabled(true);

		// 设置cookie
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.getSessionIdCookie().setName("session-z-id");
		sessionManager.getSessionIdCookie().setPath(ktfDashboardProperties.getCookie().getPath());
		sessionManager.getSessionIdCookie().setMaxAge(ktfDashboardProperties.getCookie().getMaxAge());
		sessionManager.getSessionIdCookie().setHttpOnly(true);
		return sessionManager;
	}

	@Bean(name = "sessionValidationScheduler")
	public ExecutorServiceSessionValidationScheduler getExecutorServiceSessionValidationScheduler() {
		ExecutorServiceSessionValidationScheduler sessionValidationScheduler = new ExecutorServiceSessionValidationScheduler();
		sessionValidationScheduler
				.setInterval(TimeUnit.SECONDS.toMillis(ktfDashboardProperties.getCookie().getMaxAge()));
		return sessionValidationScheduler;
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

	@Override
	protected KtfDashboardProperties ktfProperties() {
		return ktfDashboardProperties;
	}

}
