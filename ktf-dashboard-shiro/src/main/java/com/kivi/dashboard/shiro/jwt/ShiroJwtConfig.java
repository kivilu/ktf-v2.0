package com.kivi.dashboard.shiro.jwt;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;

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

import com.kivi.dashboard.shiro.base.ShiroBaseConfig;
import com.kivi.dashboard.shiro.cache.ShiroRedisCacheManager;
import com.kivi.dashboard.shiro.cache.ShiroRedisSessionDAO;
import com.kivi.dashboard.shiro.configure.ShiroRedisConfig;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.properties.KtfDashboardProperties.Cookie;

/**
 * @Description Apache Shiro配置类
 */

@ConditionalOnProperty(
		prefix = KtfDashboardProperties.PREFIX,
		value = "auth",
		havingValue = "jwt",
		matchIfMissing = false)
@AutoConfigureAfter({ ShiroRedisConfig.class })
@Configuration
public class ShiroJwtConfig extends ShiroBaseConfig {

	@Autowired
	private KtfDashboardProperties	ktfDashboardProperties;

	@Autowired
	private ShiroRedisSessionDAO	shiroRedisSessionDAO;

	@Autowired
	public ShiroRedisCacheManager	redisCacheManager;

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

		// oauth过滤
		filtersMap.put("jwt", new JwtFilter());
		shiroFilterFactoryBean.setFilters(filtersMap);
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		super.filterChainAnonMap(filterChainDefinitionMap);
		filterChainDefinitionMap.put("/**", "jwt");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;
	}

	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(jwtRealm());
		// 注入Session管理器
		securityManager.setSessionManager(sessionManager());
		// 注入缓存管理器
		securityManager.setCacheManager(redisCacheManager);
		// 注入记住我管理器
		securityManager.setRememberMeManager(rememberMeManager());
		return securityManager;
	}

	/**
	 * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
	 */
	@Bean
	public JwtRealm jwtRealm() {
		JwtRealm jwtRealm = new JwtRealm();
		jwtRealm.setCacheManager(redisCacheManager);
		// 启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
		jwtRealm.setAuthenticationCachingEnabled(true);
		// 缓存AuthenticationInfo信息的缓存名称
		jwtRealm.setAuthenticationCacheName("authenticationCache");
		// 缓存AuthorizationInfo信息的缓存名称
		jwtRealm.setAuthorizationCacheName("authorizationCache");
		return jwtRealm;
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
		simpleCookie.setMaxAge(shiroCookie().getMaxAge());
		simpleCookie.setPath(shiroCookie().getPath());
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
	public SessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		// 会话超时时间，单位：毫秒
		sessionManager.setGlobalSessionTimeout(TimeUnit.SECONDS.toMillis(shiroCookie().getMaxAge()));
		sessionManager.setSessionDAO(shiroRedisSessionDAO);
		// 去掉shiro登录时url里的JSESSIONID
		sessionManager.setSessionIdUrlRewritingEnabled(false);
		// 删除失效的session
		sessionManager.setDeleteInvalidSessions(true);

		// 会话验证
		sessionManager.setSessionValidationScheduler(getExecutorServiceSessionValidationScheduler());
		sessionManager.setSessionValidationSchedulerEnabled(true);

		// 设置cookie
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.getSessionIdCookie().setName("session-z-id");
		sessionManager.getSessionIdCookie().setPath(shiroCookie().getPath());
		sessionManager.getSessionIdCookie().setMaxAge(shiroCookie().getMaxAge());
		sessionManager.getSessionIdCookie().setHttpOnly(true);
		return sessionManager;
	}

	@Bean(name = "sessionValidationScheduler")
	public ExecutorServiceSessionValidationScheduler getExecutorServiceSessionValidationScheduler() {
		ExecutorServiceSessionValidationScheduler sessionValidationScheduler = new ExecutorServiceSessionValidationScheduler();
		sessionValidationScheduler.setInterval(TimeUnit.SECONDS.toMillis(shiroCookie().getMaxAge()));
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

	private Cookie shiroCookie() {
		return this.ktfDashboardProperties.getCookie();
	}

	@Override
	protected KtfDashboardProperties ktfProperties() {
		return this.ktfDashboardProperties;
	}
}
