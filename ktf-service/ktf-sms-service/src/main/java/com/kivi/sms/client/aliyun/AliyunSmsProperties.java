package com.kivi.sms.client.aliyun;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.kivi.framework.properties.IKtfProperties;
import com.kivi.sms.enums.SmsBizType;

import lombok.Data;

@Data
@Configuration( AliyunSmsProperties.BEAN_NAME )
@ConfigurationProperties( prefix = AliyunSmsProperties.PREFIX )
public class AliyunSmsProperties implements IKtfProperties {
    public static final String      BEAN_NAME           = "aliSmsProperties";
    public static final String      PREFIX              = "ktf.sms.aliyun";

    /**
     * 访问秘钥ID
     */
    private String                  accessKeyId;

    /**
     * 访问秘钥
     */
    private String                  accessKeySecret;

    /**
     * 短信签名
     */
    private String                  signName;

    /**
     * 短信模板MAP
     */
    private Map<SmsBizType, String> templateMap         = new HashMap<>();

    /**
     * 地区id，默认：cn-hangzhou
     */
    private String                  regionId            = "cn-hangzhou";

    /**
     * 短信报告队列
     */
    private String                  smsReportQueue;

    /**
     * 短信报告拉取线程池最小线程数，默认：6
     */
    private Integer                 pullerMinThreadSize = 6;

    /**
     * 短信报告拉取线程池最大线程数，默认：16
     */
    private Integer                 pullerMaxThreadSize = 16;

    /**
     * 短信报告拉取线程池任务队列程数，默认：200
     */
    private Integer                 pullerQueueSize     = 200;

    /**
     * 拉取消息线程数量，默认：1
     */
    private Integer                 pullerMsgThreadSize = 1;

    /**
     * SDK debug日志开始开关
     */
    private Boolean                 openDebugLog        = false;

    /**
     * 短信报告拉取线程池无数据线程休眠时间(毫秒)，默认：5000
     */
    private Integer                 pullerIdleTime      = 5000;

    public String getTemplateCode( SmsBizType smsBizType ) {
        return templateMap.get(smsBizType);
    }

    public String getSignNameUTF8() {
        String utf8 = signName;

        try {
            utf8 = new String(signName.getBytes("iso-8859-1"), "utf-8");
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
