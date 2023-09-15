package com.kivi.mqtt.models;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ChannelConfig {
    /**
     * url数组, tcp://ip:port
     */
    private String[] url;

    /**
     * username - 账号
     */
    private String username;
    /**
     * password - 密码
     */
    private String password;
    /**
     * consumer clientId
     */
    private String clientId;
    /**
     * async - 是否异步发送消息
     */
    private Boolean async;

    /**
     * topics- 主题map
     */
    private Map<String, KtfTopic> topics = new HashMap<>();
}
