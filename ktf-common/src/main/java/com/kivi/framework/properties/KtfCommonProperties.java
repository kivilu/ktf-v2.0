package com.kivi.framework.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration( KtfCommonProperties.BEAN_NAME )
@ConfigurationProperties( prefix = KtfCommonProperties.PREFIX )
public class KtfCommonProperties implements IKtfProperties {
    public static final String BEAN_NAME               = "ktfCommonProperties";
    public static final String PREFIX                  = "ktf.common";

    
    private String             sidDir                  = "/app/sid";
    
    /**
     * 测试是否开启
     */
    private Boolean            enableTest              = false;


    @Override
    public String prefix() {
        return PREFIX;
    }

    @Override
    public String beanName() {
        return BEAN_NAME;
    }

}
