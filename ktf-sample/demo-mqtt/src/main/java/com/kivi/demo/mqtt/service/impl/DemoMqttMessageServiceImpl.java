package com.kivi.demo.mqtt.service.impl;

import org.springframework.stereotype.Service;

import com.kivi.mqtt.service.IMqttMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DemoMqttMessageServiceImpl implements IMqttMessage {

    @Override
    public void onMessage(String topic, Object payload) {
        log.info("收到Mqtt消息，topic:{}，payload：{}", topic, payload);
    }

}
