package com.kivi.framework.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.kivi.framework.constant.enums.LoginType;
import com.kivi.framework.constant.enums.WebAuthEnum;
import com.kivi.framework.util.kit.CollectionKit;

import lombok.Data;

@Data
// @Configuration(KtfDashboardProperties.BEAN_NAME)
@Component(KtfSysProperties.BEAN_NAME)
@ConfigurationProperties(prefix = KtfSysProperties.PREFIX)
public class KtfSysProperties implements IKtfProperties {
	public static final String	BEAN_NAME			= "ktfSysProperties";
	public static final String	PREFIX				= "ktf.sys";

	public static final String	DUBBO_VERSION		= "${" + PREFIX + ".dubbo-service-version}";

	/**
	 * dubbo service 版本号
	 */
	private String				dubboServiceVersion	= "1.0.0";

	/**
	 * 图像验证码是否启用，默认：true——启用
	 */
	private Boolean				enableKaptcha		= true;

	/**
	 * 登录方式支持：UserName,Ukey，默认：UserName
	 */
	private LoginType			loginType			= LoginType.USERNAME;

	/**
	 * 安全框架：shiro,jwt,cas。默认：jwt
	 */
	private WebAuthEnum			auth				= WebAuthEnum.jwt;

	/**
	 * 系统预留菜单最大ID，默认：90
	 */
	private Integer				maxReserveMenuId	= 90;

	/**
	 * shiro相关配置信息
	 */
	private Shiro				shiro				= new Shiro();

	/**
	 * Session相关配置信息
	 */
	private Session				session				= new Session();

	/**
	 * Cookie 相关设置
	 * 
	 */
	private Cookie				cookie				= new Cookie();

	/**
	 * CAS相关配置
	 */
	private Cas					cas					= new Cas();

	/**
	 * upload相关设置
	 */
	private Upload				upload				= new Upload();

	@Data
	public class Upload {
		/**
		 * 文件上传路径前缀
		 */
		private String	filePrefix		= "/tmp/file-upload";

		/**
		 * 文件上传服务器名称
		 */
		private String	fileServer		= "";

		/**
		 * 二维码文件本地路径
		 */
		private String	qrCodeDir		= "/tmp/file-download/qr";

		/**
		 * FastDFS文件上传服务器名称
		 */
		private String	fdfsFileServer	= "";
	}

	@Data
	public class Shiro {
		/**
		 * shiro的缓存名字，默认值：ktf-shiro-cache-
		 */
		private String			cache			= "shiro.";
		/**
		 * 登录URL，默认：/login
		 */
		private String			loginUrl		= "/login";

		/**
		 * 登录成功URL，默认：/index
		 */
		private String			successUrl		= "/index";

		/**
		 * 未登录URL，默认：/unauth
		 */
		private String			unauthorizedUrl	= "/unauth";

		/**
		 * shiro 开发资源列表
		 */
		private List<String>	anonFilter		= CollectionKit.newArrayList();

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
		private String	cache		= "shiro.session.";

		// session 在redis过期时间默认是30分钟30*60
		private int		expireTime	= 1800;
	}

	@Data
	public class Cookie {

		private final static int	MAX_AGE_SECONDS	= 60 * 60 * 24 * 7;

		/**
		 * Cookie路径，默认：/ktf-dashboard
		 */
		private String				path			= "/ktf-dashboard";

		/**
		 * Cookie最大存活时间，单位：秒，默认7天：604800
		 */
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
