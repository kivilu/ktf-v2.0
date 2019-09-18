package com.kivi.dashboard.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.kivi.dashboard.enums.WebAuthEnum;
import com.kivi.framework.properties.IKtfProperties;

import lombok.Data;

@Data
@Configuration(KtfDashboardProperties.BEAN_NAME)
@ConfigurationProperties(prefix = KtfDashboardProperties.PREFIX)
public class KtfDashboardProperties implements IKtfProperties {
	public static final String	BEAN_NAME	= "ktfDashboardProperties";
	public static final String	PREFIX		= "ktf.dashboard";

	/**
	 * 登录认证方式支持：shiro,jwt,cas。默认：jwt
	 */
	private WebAuthEnum			auth		= WebAuthEnum.jwt;

	/**
	 * shiro相关配置信息
	 */
	private Shiro				shiro;

	/**
	 * Session相关配置信息
	 */
	private Session				session;

	/**
	 * Cookie 相关设置
	 * 
	 */
	private Cookie				cookie;

	/**
	 * CAS相关配置
	 */
	private Cas					cas;

	@Data
	public class Shiro {
		/**
		 * 是否启用
		 */
		private Boolean	enabled		= true;

		/**
		 * shiro的缓存名字，默认值：ktf-shiro-cache-
		 */
		private String	cache		= "ktf-shiro-cache-";
		/**
		 * 登录URL，默认：/login
		 */
		private String	loginUrl	= "/login";

		/**
		 * 登录成功URL，默认：/login
		 */
		private String	successUrl	= "/index";

		/**
		 * 未登录URL，默认：/unauth
		 */
		private String	unauthorizedUrl;

	}

	@Data
	public class Session {

		/**
		 * 是否启用
		 */
		private Boolean	enabled		= true;

		/**
		 * shiro的共享session缓存的名字，默认值：ktf-shiro-session-
		 */
		private String	cache		= "ktf-shiro-session-";

		// session 在redis过期时间默认是30分钟30*60
		private int		expireTime	= 1800;
	}

	@Data
	public class Cookie {

		private final static int	MAX_AGE_SECONDS	= 60 * 60 * 1 * 1;

		/**
		 * Cookie路径，默认：/ktf-dashboard
		 */
		private String				path			= "/ktf-dashboard";

		// Cookie最大存活时间，单位：秒，默认：60 * 60 * 1 * 1
		private int					maxAge			= MAX_AGE_SECONDS;
	}

	@Data
	public class Cas {

		/**
		 * CasServerUrlPrefix，示例： http://127.0.0.1:8000/cas
		 */
		private String	prefixUrl;

		/**
		 * Cas登录页面地址，示例：http://127.0.0.1:8000/cas/login
		 */
		private String	casLoginUrl;

		/**
		 * Cas退出页面地址 示例：http://127.0.0.1:8000/cas/logout
		 */
		private String	logoutUrl;

		/**
		 * Cas回调地址 示例：http://127.0.0.1:8080/callback
		 */
		private String	callbackUrl;

		/**
		 * Dashboard服务地址，http:// 127.0.0.1:8080/
		 */
		private String	serviceUrl;

		/**
		 * cas密码盐，默认：12345678901234567890123456789012
		 */
		private String	salt;

	}

	@Override
	public String prefix() {
		return PREFIX;
	}

	@Override
	public String beanName() {
		return BEAN_NAME;
	}

}
