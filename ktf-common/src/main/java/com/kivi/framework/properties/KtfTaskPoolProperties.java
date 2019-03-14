package com.kivi.framework.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration( KtfTaskPoolProperties.BEAN_NAME )
@ConfigurationProperties( prefix = KtfTaskPoolProperties.PREFIX )
public class KtfTaskPoolProperties implements IKtfProperties {
    public static final String BEAN_NAME               = "taskPoolProperties";
    public static final String PREFIX                  = "ktf.task.pool";

    private int                corePoolSize            = 7;
    private int                maxPoolSize             = 42;
    private int                queueCapacity           = 11;
    private int                keepAliveSeconds        = 5;
    private int                awaitTerminationSeconds = 5;

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
        return "KtfTaskPoolProperties [ corePoolSize=" + corePoolSize + ", maxPoolSize=" +
                maxPoolSize + ", queueCapacity=" + queueCapacity + ", keepAliveSeconds=" + keepAliveSeconds +
                ", awaitTerminationSeconds=" + awaitTerminationSeconds + "]";
    }

}
