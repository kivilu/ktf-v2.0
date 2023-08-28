package com.kivi.cif.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.kivi.framework.crypto.enums.AlgDigest;
import com.kivi.framework.properties.IKtfProperties;

import lombok.Data;

@Data
// @Configuration(CifProperties.BEAN_NAME)
@Component(CifProperties.BEAN_NAME)
@ConfigurationProperties(prefix = CifProperties.PREFIX)
public class CifProperties implements IKtfProperties {

    public static final String BEAN_NAME = "cifProperties";
    public static final String PREFIX = "ktf.cif";
    public static final String DUBBO_VERSION = "${" + PREFIX + ".dubbo-service-version}";

    /**
     * dubbo service 版本号
     */
    private String dubboServiceVersion = "1.0.0";

    /**
     * 摘要算法
     */
    private AlgDigest algDigest = AlgDigest.MD5;

    /**
     * 用户默认密码
     */
    private String defaultPassword = "11111111";

    @Override
    public String prefix() {
        return PREFIX;
    }

    @Override
    public String beanName() {
        return BEAN_NAME;
    }

}
