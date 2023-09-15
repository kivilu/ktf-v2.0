package com.kivi.framework.web.properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.kivi.framework.properties.IKtfProperties;

import lombok.Data;

@ConditionalOnProperty(prefix = KtfWebProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
// @Configuration(KtfWebProperties.BEAN_NAME)
@Component(KtfWebProperties.BEAN_NAME)
@ConfigurationProperties(prefix = KtfWebProperties.PREFIX)
@Data
public class KtfWebProperties implements IKtfProperties {
    public static final String BEAN_NAME = "ktfWebProperties";
    public static final String PREFIX = "ktf.web";

    /**
     * Web请求默认超时时间
     */
    private Long webRequestTimeout = 30000L;

    /**
     * 允许的上传文件类型
     */
    private String allowFileSuffix =
        "pkm,vkd,gif,jpg,jpeg,bmp,png,jar,doc,docx,xls,xlsx,pdf,txt,rar,zip,pem,key,cer,p12,pkcs12,pfx,jks";

    @Override
    public String prefix() {
        return PREFIX;
    }

    @Override
    public String beanName() {
        return BEAN_NAME;
    }

}
