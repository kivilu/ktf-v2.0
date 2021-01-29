package com.kivi.dubbo.properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.kivi.framework.properties.IKtfProperties;

import lombok.Data;

@Data
// @Configuration(KtfDubboProperties.BEAN_NAME)
@Component(KtfDubboProperties.BEAN_NAME)
@ConfigurationProperties(prefix = KtfDubboProperties.PREFIX)
@ConditionalOnProperty(prefix = KtfDubboProperties.PREFIX, name = "enabled", havingValue = "true",
    matchIfMissing = true)
public class KtfDubboProperties implements IKtfProperties {
    public static final String BEAN_NAME = "ktfDubboProperties";
    public static final String PREFIX = "dubbo";

    private Boolean enabled;

    /**
     * dubbo启用Kryo和FST序列化工具时注册的类，多个类名之间使用“,”分割
     */
    private String serializeClasses;

    @Override
    public String prefix() {
        return PREFIX;
    }

    @Override
    public String beanName() {
        return BEAN_NAME;
    }

}
