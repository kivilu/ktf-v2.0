package com.kivi.mqtt.models;

import java.util.regex.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class KtfTopicBiz extends KtfTopic {
    public static final String REGEX = "\\{[A-Za-z0-9_-]+\\}";

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * topic名称
     */
    private String name;

    /**
     * action 订阅、发布
     */
    private String action;

    /**
     * topic正则
     */
    private String regex;

    private Pattern pattern;

    /**
     * 替换topic中的占位字符串
     * 
     * @param topic
     * @param items
     * @return
     */
    public static String replace(String topic, String... items) {
        String result = topic;
        for (String item : items) {
            result = result.replaceFirst(REGEX, item);
        }
        return result;
    }

}
