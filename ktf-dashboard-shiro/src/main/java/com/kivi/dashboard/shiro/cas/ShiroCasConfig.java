package com.kivi.dashboard.shiro.cas;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.config.CasProtocol;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.jwt.profile.JwtGenerator;
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
import com.kivi.framework.properties.KtfDashboardProperties.Cas;
import com.kivi.framework.properties.KtfDashboardProperties.Cookie;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.filter.LogoutFilter;
import io.buji.pac4j.filter.SecurityFilter;
import io.buji.pac4j.subject.Pac4jSubjectFactory;

/**
 * @description Shiro CAS配置
 */

@ConditionalOnProperty(
		prefix = KtfDashboardProperties.PREFIX,
		value = "auth",
		havingValue = "cas",
		matchIfMissing = false)
@AutoConfigureAfter({ ShiroRedisConfig.class })
@Configuration
public class ShiroCasConfig extends ShiroBaseConfig{

	@Autowired
	private KtfDashboardProperties	ktfDashboardProperties;

	@Autowired
	private ShiroRedisSessionDAO	sessionDAO;

	@Autowired
	public ShiroRedisCacheManager	redisCacheManager;

	/**
	 * JWT Token 生成器，对CommonProfile生成然后每次携带token访问
	 *
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Bean
	protected JwtGenerator jwtGenerator() {
		return new JwtGenerator(new SecretSignatureConfiguration(cas().getSalt()),
				new SecretEncryptionConfiguration(cas().getSalt()));
	}

	/**
	 * JWT校验器，也就是目前设置的ParameterClient进行的校验器，是rest/或者前后端分离的核心校验器
	 *
	 * @return
	 */
	@Bean
	protected JwtAuthenticator jwtAuthenticator() {
		JwtAuthenticator jwtAuthenticator = new JwtAuthenticator();
		jwtAuthenticator.addSignatureConfiguration(new SecretSignatureConfiguration(cas().getSalt()));
		jwtAuthenticator.addEncryptionConfiguration(new SecretEncryptionConfiguration(cas().getSalt()));
		return jwtAuthenticator;
	}

	/**
	 * cas服务端配置
	 *
	 * @return
	 */
	@Bean
	public CasConfiguration casConfiguration() {
		CasConfiguration casConfiguration = new CasConfiguration(cas().getCasLoginUrl());
		casConfiguration.setProtocol(CasProtocol.CAS30);
		casConfiguration.setPrefixUrl(cas().getPrefixUrl());
		return casConfiguration;
	}

	/**
	 * cas客户端配置
	 *
	 * @return
	 */
	@Bean
	public CasClient casClient() {
		CasClient casClient = new CasClient();
		casClient.setConfiguration(casConfiguration());
		casClient.setCallbackUrl(cas().getCallbackUrl());
		casClient.setName("cas");
		return casClient;
	}

	/**
	 * 通过rest接口可以获取tgt,获取service ticket,甚至可以获取casProfile
	 *
	 * @return
	 */
	@Bean
	public CasRestFormClient casRestFormClient() {
		CasRestFormClient casRestFormClient = new CasRestFormClient();
		casRestFormClient.setConfiguration(casConfiguration());
		casRestFormClient.setName("rest");
		return casRestFormClient;

	}

	@Bean
	public Clients clients() {
		// 设置默认client
		Clients			clients			= new Clients();
		// token校验器，可以用HeaderClient更安全
		ParameterClient	parameterClient	= new ParameterClient("token", jwtAuthenticator());
		parameterClient.setSupportGetRequest(true);
		parameterClient.setName("jwt");
		// 支持的client全部设置进去
		clients.setClients(casClient(), casRestFormClient(), parameterClient);
		return clients;
	}

	@Bean
	public Config casConfig() {
		Config config = new Config();
		config.setClients(clients());
		return config;
	}

	/**
	 * 身份认证realm(账号密码校验；权限等)
	 */
	@Bean(name = "pac4jRealm")
	public Realm pac4jRealm() {
		return new ShiroCasRealm();
	}

	@Bean(name = "pac4jSubjectFactory")
	public SubjectFactory pac4jSubjectFactory() {
		return new Pac4jSubjectFactory();
	}

	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置自定义Realm
		securityManager.setRealm(pac4jRealm());
		securityManager.setSubjectFactory(pac4jSubjectFactory());
		// 注入缓存管理器
		securityManager.setCacheManager(redisCacheManager);
		// 记住密码管理
		securityManager.setRememberMeManager(rememberMeManager());
		// session管理
		securityManager.setSessionManager(sessionManager());

		return securityManager;
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		Map<String, Filter>	filters				= new HashMap<>();
		SecurityFilter		casSecurityFilter	= new SecurityFilter();
		casSecurityFilter.setClients("cas,rest,jwt");
		casSecurityFilter.setConfig(casConfig());
		filters.put("casSecurityFilter", casSecurityFilter);
		CallbackFilter callbackFilter = new CallbackFilter();
		callbackFilter.setConfig(casConfig());
		filters.put("callbackFilter", callbackFilter);

		LogoutFilter logoutFilter = new LogoutFilter();
		logoutFilter.setConfig(casConfig());
		logoutFilter.setCentralLogout(true);
		logoutFilter.setDefaultUrl(cas().getServiceUrl());
		filters.put("logoutFilter", logoutFilter);

		shiroFilterFactoryBean.setFilters(filters);

		// 拦截器
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		super.filterChainAnonMap(filterChainDefinitionMap);

		filterChainDefinitionMap.put("/callback", "callbackFilter");
		filterChainDefinitionMap.put("/logout", "logoutFilter");
		filterChainDefinitionMap.put("/**", "casSecurityFilter");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;
	}

	/**
	 * cookie对象;
	 *
	 * @return
	 */
	@Bean
	public SimpleCookie rememberMeCookie() {
		SimpleCookie	simpleCookie	= new SimpleCookie("rememberMe");

		Cookie			cookie			= ktfDashboardProperties.getCookie();

		// 记住我cookie生效时间1小时 ,单位秒
		simpleCookie.setMaxAge(cookie.getMaxAge());
		simpleCookie.setPath(cookie.getPath());
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
		Cookie						cookie			= ktfDashboardProperties.getCookie();

		DefaultWebSessionManager	sessionManager	= new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(TimeUnit.SECONDS.toMillis(cookie.getMaxAge()));
		sessionManager.setSessionDAO(sessionDAO);
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
		sessionManager.getSessionIdCookie().setPath(cookie.getPath());
		sessionManager.getSessionIdCookie().setMaxAge(cookie.getMaxAge());
		sessionManager.getSessionIdCookie().setHttpOnly(true);
		return sessionManager;
	}

	@Bean(name = "sessionValidationScheduler")
	public ExecutorServiceSessionValidationScheduler getExecutorServiceSessionValidationScheduler() {
		Cookie										cookie						= ktfDashboardProperties.getCookie();
		ExecutorServiceSessionValidationScheduler	sessionValidationScheduler	= new ExecutorServiceSessionValidationScheduler();
		sessionValidationScheduler.setInterval(TimeUnit.SECONDS.toMillis(cookie.getMaxAge()));
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

	private Cas cas() {
		return ktfDashboardProperties.getCas();
	}

	@Override
	protected KtfDashboardProperties ktfProperties() {
		return ktfDashboardProperties;
	}
}
