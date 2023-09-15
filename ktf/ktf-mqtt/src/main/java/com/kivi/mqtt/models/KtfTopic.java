package com.kivi.mqtt.models;

import java.io.Serializable;

import com.kivi.mqtt.enums.MqttQos;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class KtfTopic implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * topic内容
     */
    private String topic;

    private MqttQos qos;
}
