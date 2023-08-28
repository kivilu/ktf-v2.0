package com.kivi.sms.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.kivi.framework.properties.IKtfProperties;

import lombok.Data;

@Data
@Configuration(KtfSmsProperties.BEAN_NAME)
@ConfigurationProperties(prefix = KtfSmsProperties.PREFIX)
public class KtfSmsProperties implements IKtfProperties {
	public static final String	BEAN_NAME		= "ktfSmsProperties";
	public static final String	PREFIX			= "ktf.sms";
	public static final String	DUBBO_VERSION	= "${" + PREFIX + ".dubbo-service-version}";

	/**
	 * sms dubbo service 版本号
	 */
	private String				dubboServiceVersion;

	/**
	 * 连接超时时间，单位毫秒，默认：10000
	 */
	private Integer				connectTimeout	= 10000;

	/**
	 * 读取超时时间，单位毫秒，默认：10000
	 */
	private Integer				readTimeout		= 10000;

	/**
	 * 验证码长度，默认：6
	 */
	private Integer				smsCodeLength	= 6;

	/**
	 * 验证码有效时间，默认：300000毫秒
	 */
	private Long				smsCodeExpire	= 300000L;

	@Override
	public String prefix() {
		return PREFIX;
	}

	@Override
	public String beanName() {
		return BEAN_NAME;
	}

}
