package com.kivi.framework.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration(KtfTaskPoolProperties.BEAN_NAME)
@ConfigurationProperties(prefix = KtfTaskPoolProperties.PREFIX)
public class KtfTaskPoolProperties implements IKtfProperties {
	public static final String	BEAN_NAME				= "taskPoolProperties";
	public static final String	PREFIX					= "ktf.task.pool";

	private Integer				corePoolSize			= 7;
	private Integer				maxPoolSize				= 42;
	private Integer				queueCapacity			= 11;
	private Integer				keepAliveSeconds		= 5;
	private Integer				awaitTerminationSeconds	= 5;

	@Override
	public String prefix() {
		return PREFIX;
	}

	@Override
	public String beanName() {
		return BEAN_NAME;
	}

	@Override
	public String toString() {
		return "KtfTaskPoolProperties [ corePoolSize=" + corePoolSize + ", maxPoolSize=" + maxPoolSize
				+ ", queueCapacity=" + queueCapacity + ", keepAliveSeconds=" + keepAliveSeconds
				+ ", awaitTerminationSeconds=" + awaitTerminationSeconds + "]";
	}

}
