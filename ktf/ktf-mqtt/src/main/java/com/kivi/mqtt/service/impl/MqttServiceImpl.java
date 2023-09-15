package com.kivi.mqtt.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.stereotype.Service;

import com.kivi.framework.properties.KtfCommonProperties;
import com.kivi.mqtt.service.MqttService;

@ConditionalOnProperty(prefix = KtfCommonProperties.PREFIX, name = {"enable-mqtt"}, havingValue = "true",
    matchIfMissing = false)
@Service
public class MqttServiceImpl implements MqttService {
    MqttPahoMessageDrivenChannelAdapter adapter;

    // private KtfMqttProperties mqttProperties;

    @Autowired
    public MqttServiceImpl(MqttPahoMessageDrivenChannelAdapter adapter) {
        this.adapter = adapter;
        // this.mqttProperties = mqttProperties;
    }

    @Override
    public void addTopic(String topic) {
        String[] topics = adapter.getTopic();
        if (!Arrays.asList(topics).contains(topic)) {
            adapter.addTopic(topic, 0);
        }
    }

    @Override
    public void removeTopic(String topic) {
        adapter.removeTopic(topic);
    }
}
