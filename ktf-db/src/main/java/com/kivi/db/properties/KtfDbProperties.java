package com.kivi.db.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.kivi.framework.properties.IKtfProperties;

import lombok.Data;

@Data
// @Configuration(KtfDbProperties.BEAN_NAME)
@Component(KtfDbProperties.BEAN_NAME)
@ConfigurationProperties(prefix = KtfDbProperties.PREFIX)
public class KtfDbProperties implements IKtfProperties {
    public static final String BEAN_NAME = "ktfDbProperties";
    public static final String PREFIX = "ktf.db";
    public static final String MAPPER_SCAN = "${" + PREFIX + ".mappers}";

    private String mappers;

    @Override
    public String beanName() {
        return BEAN_NAME;
    }

    @Override
    public String prefix() {
        return PREFIX;
    }

}
