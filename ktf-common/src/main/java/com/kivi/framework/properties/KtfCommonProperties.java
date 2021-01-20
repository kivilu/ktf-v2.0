package com.kivi.framework.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
// @Configuration(KtfCommonProperties.BEAN_NAME)
@Component(KtfCommonProperties.BEAN_NAME)
@ConfigurationProperties(prefix = KtfCommonProperties.PREFIX)
public class KtfCommonProperties implements IKtfProperties {
    public static final String BEAN_NAME = "ktfCommonProperties";
    public static final String PREFIX = "ktf.common";

    private String sidDir = "/app/sid";

    private String componentScan = "com.kivi";

    /**
     * 业务应用ID
     */
    private Long appId = 1L;

    /**
     * 业务应用代码
     */
    private String bzApplicatonCode = "BZ-default";

    /**
     * 业务应用名称
     */
    private String bzApplicatonName = "默认业务应用";

    /**
     * 测试是否开启
     */
    private Boolean enableTest = false;

    @Override
    public String prefix() {
        return PREFIX;
    }

    @Override
    public String beanName() {
        return BEAN_NAME;
    }

}
