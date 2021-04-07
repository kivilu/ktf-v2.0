package com.kivi.framework.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component(KtfLogbackProperties.BEAN_NAME)
@ConfigurationProperties(prefix = KtfLogbackProperties.PREFIX)
public class KtfLogbackProperties implements IKtfProperties {
	public static final String	BEAN_NAME	= "ktfLogbackProperties";
	public static final String	PREFIX		= "ktf.logback";

	/**
	 * 日志保存路径，默认：/tmp/logs
	 */
	private String				logPath		= "/tmp/logs";

	/**
	 * 业务应用ID
	 */
	private Datasource			datasource	= new Datasource();

	@Data
	public class Datasource {
		/**
		 * JDBC驱动，默认：org.apache.commons.dbcp.BasicDataSource
		 */
		private String	driverClassName	= "org.apache.commons.dbcp.BasicDataSource";

		/**
		 * JDBC URL，默认：jdbc:mysql://127.0.0.1:3306/logback?characterEncoding=UTF-8
		 */
		private String	url				= "jdbc:mysql://127.0.0.1:3306/logback?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=Asia/Shanghai";

		/**
		 * 数据库用户名
		 */
		private String	username		= "root";

		/**
		 * 数据库密码
		 */
		private String	password		= "root";

		/**
		 * 初始连接数，默认：5
		 */
		private Integer	initialSize		= 5;

		/**
		 * 连接池最大数量，默认：20
		 */
		private Integer	maxTotal		= 20;

		/**
		 * 连接池最大空闲，默认：20
		 */
		private Integer	maxIdle			= 20;

		/**
		 * 最小空闲连接数，默认：5
		 */
		private Integer	minIdle			= 5;

		/**
		 * Datasource类型，默认：org.apache.commons.dbcp2.BasicDataSource
		 */
		private String	type			= "org.apache.commons.dbcp2.BasicDataSource";
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
