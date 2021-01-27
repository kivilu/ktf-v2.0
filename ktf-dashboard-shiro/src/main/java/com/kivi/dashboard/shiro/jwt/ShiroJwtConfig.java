package com.kivi.dashboard.shiro.jwt;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kivi.dashboard.shiro.base.ShiroBaseConfig;
import com.kivi.dashboard.shiro.cache.ShiroRedisCacheManager;
import com.kivi.dashboard.shiro.configure.ShiroRedisConfig;
import com.kivi.dashboard.shiro.ktf.AnyRolesAuthorizationFilter;
import com.kivi.dashboard.shiro.ktf.KtfCredentialsMatcher;
import com.kivi.dashboard.shiro.ktf.KtfPermsFilter;
import com.kivi.dashboard.shiro.ktf.KtfRealm;
import com.kivi.dashboard.shiro.service.ShiroUserService;
import com.kivi.framework.cache.constant.KtfCache;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.service.KtfTokenService;

/**
 * @Description Apache Shiro配置类
 * 
 */
// 当运行一个Web应用程序时,Shiro将会创建一些有用的默认Filter实例,并自动地在[main]项中将它们置为可用自动地可用的默认的Filter实例是被DefaultFilter枚举类定义的,枚举的名称字段就是可供配置的名称
/**
 * anon---------------org.apache.shiro.web.filter.authc.AnonymousFilter 没有参数，表示可以匿名使用。
 * authc--------------org.apache.shiro.web.filter.authc.FormAuthenticationFilter 表示需要认证(登录)才能使用，没有参数
 * authcBasic---------org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter 没有参数表示httpBasic认证
 * logout-------------org.apache.shiro.web.filter.authc.LogoutFilter
 * noSessionCreation--org.apache.shiro.web.filter.session.NoSessionCreationFilter
 * perms--------------org.apache.shiro.web.filter.authz.PermissionAuthorizationFilter
 * 参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，例如/admins/user/**=perms["user:add:*,user:modify:*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
 * port---------------org.apache.shiro.web.filter.authz.PortFilter
 * port[8081],当请求的url的端口不是8081是跳转到schemal://serverName:8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。
 * rest---------------org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
 * 根据请求的方法，相当于/admins/user/**=perms[user:method] ,其中method为post，get，delete等。
 * roles--------------org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
 * 参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，例如admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。
 * ssl----------------org.apache.shiro.web.filter.authz.SslFilter 没有参数，表示安全的url请求，协议为https
 * user---------------org.apache.shiro.web.filter.authz.UserFilter 没有参数表示必须存在用户，当登入操作时不做检查
 */
/**
 * 通常可将这些过滤器分为两组 <br>
 * anon,authc,authcBasic,user是第一组认证过滤器 <br>
 * perms,port,rest,roles,ssl是第二组授权过滤器 <br>
 * 注意user和authc不同：当应用开启了rememberMe时,用户下次访问时可以是一个user,但绝不会是authc,因为authc是需要重新认证的
 * user表示用户不一定已通过认证,只要曾被Shiro记住过登录状态的用户就可以正常发起请求,比如rememberMe
 * 说白了,以前的一个用户登录时开启了rememberMe,然后他关闭浏览器,下次再访问时他就是一个user,而不会authc
 *
 *
 * 举几个例子 /admin=authc,roles[admin] 表示用户必需已通过认证,并拥有admin角色才可以正常发起'/admin'请求 /edit=authc,perms[admin:edit]
 * 表示用户必需已通过认证,并拥有admin:edit权限才可以正常发起'/edit'请求 /home=user 表示用户不一定需要已经通过认证,只需要曾经被Shiro记住过登录状态就可以正常发起'/home'请求
 */

/**
 * 各默认过滤器常用如下(注意URL Pattern里用到的是两颗星,这样才能实现任意层次的全匹配) /admins/**=anon 无参,表示可匿名使用,可以理解为匿名用户或游客 /admins/user/**=authc
 * 无参,表示需认证才能使用 /admins/user/**=authcBasic 无参,表示httpBasic认证 /admins/user/**=ssl 无参,表示安全的URL请求,协议为https
 * /admins/user/**=perms[user:add:*]
 * 参数可写多个,多参时必须加上引号,且参数之间用逗号分割,如/admins/user/**=perms["user:add:*,user:modify:*"]。当有多个参数时必须每个参数都通过才算通过,相当于isPermitedAll()方法
 * /admins/user/**=port[8081]
 * 当请求的URL端口不是8081时,跳转到schemal://serverName:8081?queryString。其中schmal是协议http或https等,serverName是你访问的Host,8081是Port端口,queryString是你访问的URL里的?后面的参数
 * /admins/user/**=rest[user] 根据请求的方法,相当于/admins/user/**=perms[user:method],其中method为post,get,delete等
 * /admins/user/**=roles[admin]
 * 参数可写多个,多个时必须加上引号,且参数之间用逗号分割,如：/admins/user/**=roles["admin,guest"]。当有多个参数时必须每个参数都通过才算通过,相当于hasAllRoles()方法
 *
 */
@ConditionalOnProperty(prefix = KtfDashboardProperties.PREFIX, value = "auth", havingValue = "jwt",
    matchIfMissing = false)
@AutoConfigureAfter({ShiroRedisConfig.class})
@Configuration
public class ShiroJwtConfig extends ShiroBaseConfig {

    @Autowired
    private KtfDashboardProperties ktfDashboardProperties;

    // @Autowired
    // private ShiroRedisSessionDAO shiroRedisSessionDAO;

    @Autowired
    public ShiroRedisCacheManager redisCacheManager;

    @Autowired
    private KtfTokenService ktfTokenService;

    @Autowired
    ShiroUserService shiroUserService;

    /* @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean(SecurityManager securityManager) throws Exception {
        FilterRegistrationBean<Filter> filterRegistration = new FilterRegistrationBean<Filter>();
        filterRegistration.setFilter((Filter)shiroFilter(securityManager).getObject());
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setAsyncSupported(true);
        filterRegistration.setEnabled(true);
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);
    
        return filterRegistration;
    }*/

    @Bean
    public Authenticator authenticator() {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setRealms(Arrays.asList(jwtShiroRealm(), ktfShiroRealm()));
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return authenticator;
    }

    /**
     * 不存储Session
     * 
     * @return
     */
    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // shiroFilter.setLoginUrl("");//身份认证失败，则跳转到登录页面的配置
        // 没有登录的用户请求需要登录的页面时自动跳转到登录页面，不是必须的属性，不输入地址的话会自动寻找项目web项目的根目录下的”/login.jsp”页面。
        // shiroFilter.setSuccessUrl("");//登录成功默认跳转页面，不配置则跳转至”/”。如果登陆前点击的一个需要登录的页面，则在登录自动跳转到那个需要登录的页面。不跳转到此。
        // shiroFilter.setUnauthorizedUrl("");//没有权限默认跳转的页面
        // shiroFilter.setFilterChainDefinitions("");//filterChainDefinitions的配置顺序为自上而下,以最上面的为准

        Map<String, Filter> filtersMap = shiroFilterFactoryBean.getFilters();

        filtersMap.put("authcToken", createAuthFilter());
        filtersMap.put("anyRole", createRolesFilter());
        // filtersMap.put("perms", createPermsFilter());

        shiroFilterFactoryBean.setFilters(filtersMap);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        super.filterChainAnonMap(filterChainDefinitionMap);
        filterChainDefinitionMap.put("/**", "authcToken");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealms(Arrays.asList(ktfShiroRealm(), jwtShiroRealm()));
        // 注入Session管理器
        // securityManager.setSessionManager(sessionManager());
        // 注入缓存管理器
        // securityManager.setCacheManager(redisCacheManager);
        // 注入记住我管理器
        // securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * 用于用户名密码登录时认证的realm
     */
    @Bean("ktfRealm")
    public Realm ktfShiroRealm() {
        KtfRealm myShiroRealm = new KtfRealm(shiroUserService, ktfTokenService, ktfCredentialsMatcher());
        myShiroRealm.setCacheManager(redisCacheManager);
        myShiroRealm.setAuthenticationCachingEnabled(true);
        myShiroRealm.setAuthenticationCacheName(KtfCache.ShiroAuthentication);
        return myShiroRealm;
    }

    /**
     * 用于JWT token认证的realm
     */
    @Bean("jwtRealm")
    public JwtShiroRealm jwtShiroRealm() {
        JwtShiroRealm jwtRealm = new JwtShiroRealm(shiroUserService);
        // jwtRealm.setCacheManager(redisCacheManager);
        // 启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
        // jwtRealm.setAuthenticationCachingEnabled(true);
        // 缓存AuthenticationInfo信息的缓存名称
        // jwtRealm.setAuthenticationCacheName("authenticationCache");
        // 缓存AuthorizationInfo信息的缓存名称
        // jwtRealm.setAuthorizationCacheName("authorizationCache");
        return jwtRealm;
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

    @Override
    protected KtfDashboardProperties ktfProperties() {
        return this.ktfDashboardProperties;
    }

    // 注意不要加@Bean注解，不然spring会自动注册成filter
    protected JwtAuthFilter createAuthFilter() {
        return new JwtAuthFilter();
    }

    // 注意不要加@Bean注解，不然spring会自动注册成filter
    protected AnyRolesAuthorizationFilter createRolesFilter() {
        return new AnyRolesAuthorizationFilter();
    }

    protected KtfPermsFilter createPermsFilter() {
        return new KtfPermsFilter();
    }

    protected KtfCredentialsMatcher ktfCredentialsMatcher() {
        return new KtfCredentialsMatcher(shiroUserService.cifAuthService());
    }

    /**
     * cookie对象;
     *
     * @return
     */
    // @Bean
    // public SimpleCookie rememberMeCookie() {
    // SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
    // // 记住我cookie生效时间1小时 ,单位秒
    // simpleCookie.setMaxAge(shiroCookie().getMaxAge());
    // simpleCookie.setPath(shiroCookie().getPath());
    // simpleCookie.setHttpOnly(true);
    // return simpleCookie;
    // }

    /**
     * cookie管理对象;
     *
     * @return
     */
    // @Bean
    // public CookieRememberMeManager rememberMeManager() {
    // CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
    // cookieRememberMeManager.setCookie(rememberMeCookie());
    // cookieRememberMeManager.setCipherKey(Base64.decode("5aaC5qKm5oqA5pyvAAAAAA=="));
    // return cookieRememberMeManager;
    // }

    /*
     * @Bean(name = "sessionManager") public SessionManager sessionManager() {
     * DefaultWebSessionManager sessionManager = new DefaultWebSessionManager(); //
     * 会话超时时间，单位：毫秒
     * sessionManager.setGlobalSessionTimeout(TimeUnit.SECONDS.toMillis(shiroCookie(
     * ).getMaxAge())); sessionManager.setSessionDAO(shiroRedisSessionDAO); //
     * 去掉shiro登录时url里的JSESSIONID
     * sessionManager.setSessionIdUrlRewritingEnabled(false); // 删除失效的session
     * sessionManager.setDeleteInvalidSessions(true);
     * 
     * // 会话验证 sessionManager.setSessionValidationScheduler(
     * getExecutorServiceSessionValidationScheduler());
     * sessionManager.setSessionValidationSchedulerEnabled(true);
     * 
     * // 设置cookie sessionManager.setSessionIdCookieEnabled(true);
     * sessionManager.getSessionIdCookie().setName("session-z-id");
     * sessionManager.getSessionIdCookie().setPath(shiroCookie().getPath());
     * sessionManager.getSessionIdCookie().setMaxAge(shiroCookie().getMaxAge());
     * sessionManager.getSessionIdCookie().setHttpOnly(true); return sessionManager;
     * }
     * 
     * @Bean(name = "sessionValidationScheduler") public
     * ExecutorServiceSessionValidationScheduler
     * getExecutorServiceSessionValidationScheduler() {
     * ExecutorServiceSessionValidationScheduler sessionValidationScheduler = new
     * ExecutorServiceSessionValidationScheduler();
     * sessionValidationScheduler.setInterval(TimeUnit.SECONDS.toMillis(shiroCookie(
     * ).getMaxAge())); return sessionValidationScheduler; }
     */

    // private Cookie shiroCookie() {
    // return this.ktfDashboardProperties.getCookie();
    // }

}
