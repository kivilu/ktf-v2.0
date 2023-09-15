/**
 * 消息接收器
 */
package com.kivi.mqtt.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import com.kivi.framework.properties.KtfCommonProperties;
import com.kivi.mqtt.properties.KtfMqttProperties;
import com.kivi.mqtt.service.IMqttMessage;

import lombok.extern.slf4j.Slf4j;

/**
 * @author kivi
 *
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = KtfCommonProperties.PREFIX, name = {"enable-mqtt"}, havingValue = "true",
    matchIfMissing = false)
public class ConsumerHandler implements MessageHandler {

    @Autowired(required = false)
    private IMqttMessage iMqttMessage;

    @Override
    @ServiceActivator(inputChannel = KtfMqttProperties.CONSUMER_NAME)
    public void handleMessage(Message<?> message) throws MessagingException {
        String topic = (String)message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC);
        Object payload = message.getPayload();

        if (iMqttMessage != null) {
            iMqttMessage.onMessage(topic, payload);
        } else {
            log.warn("收到了Mqtt消息，没有实现IMqttMessage接口的bean");
        }

    }

}
