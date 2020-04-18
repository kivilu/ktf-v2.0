package com.kivi.zkconfig.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration(KtfZkConfigProperties.BEAN_NAME)
@ConfigurationProperties(prefix = KtfZkConfigProperties.PREFIX)
public class KtfZkConfigProperties {
	public static final String	BEAN_NAME		= "ktfZkConfigProperties";
	public static final String	PREFIX			= "ktf.zkconfig";

	private Boolean				enabled			= false;

	/**
	 * zookeeper集群地址，默认值：127.0.0.1:2181
	 */
	private String				connectStr		= "127.0.0.1:2181";

	/**
	 * 根节点地址，默认值：/ktf/zkconfig/project1
	 */
	private String				rootNode		= "/ktf/root";

	/**
	 * 版本号，默认值：1.0.0
	 */
	private String				version			= "1.0.0";

	/**
	 * 配置信息组，默认值：property-group1
	 */
	private String				propertyGroup	= "default-group";

	public String root() {
		String rootPath = new StringBuilder().append(rootNode).append("/").append(version).append("/")
				.append(propertyGroup).toString();

		return rootPath;
	}
}
