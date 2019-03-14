package com.kivi.framework.properties;

import java.io.UnsupportedEncodingException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration( KtfSwaggerProperties.BEAN_NAME )
@ConfigurationProperties( prefix = KtfSwaggerProperties.PREFIX )
@ConditionalOnProperty(
                        prefix = KtfSwaggerProperties.PREFIX,
                        name = "enabled",
                        havingValue = "true",
                        matchIfMissing = false )
public class KtfSwaggerProperties implements IKtfProperties {
    public static final String BEAN_NAME            = "ktfSwaggerProperties";
    public static final String PREFIX               = "ktf.swagger";

    private Boolean            enabled              = true;
    private String             title;
    private String             description;
    private String             version              = "1.0.0";
    private String             termsOfServiceUrl;
    private Boolean            authorizationEnabled = true;
    private String             license;
    private String             licenseUrl;

    public String getTitleUTF8() {
        String utf8 = title;

        try {
            utf8 = new String(title.getBytes("iso-8859-1"), "utf-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return utf8;
    }

    public String getDescriptionUTF8() {
        String utf8 = description;

        try {
            utf8 = new String(description.getBytes("iso-8859-1"), "utf-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return utf8;
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
