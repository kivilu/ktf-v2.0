package com.kivi.mqtt.service;

public interface IMqttMessage {

    void onMessage(String topic, Object payload);
}
