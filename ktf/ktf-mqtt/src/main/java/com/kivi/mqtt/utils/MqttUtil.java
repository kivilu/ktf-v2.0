package com.kivi.mqtt.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.alibaba.fastjson2.JSON;
import com.kivi.framework.exception.KtfException;
import com.kivi.mqtt.enums.MqttQos;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MqttUtil {
    public static final Map<String, MqttPahoMessageHandler> HANDLER_MAP = new HashMap<>(16);

    public static final int DEFAULT_QOS = 0;

    /**
     * 获取默认生产者的消息处理器
     *
     * @return 消息处理器
     */
    public static MqttPahoMessageHandler getDefaultHandler() throws KtfException {
        Collection<MqttPahoMessageHandler> handlers = HANDLER_MAP.values();
        Iterator<MqttPahoMessageHandler> iterator = handlers.iterator();
        MqttPahoMessageHandler handler = iterator.next();
        if (handler == null) {
            throw new KtfException("无可用消息通道, 请保证mqtt中至少有一个producer");
        }
        return handler;
    }

    /**
     * 给主题发送消息
     *
     * @param topic 主题
     * @param data 消息内容
     */
    public static void sendMessage(String topic, Object data) throws KtfException {
        sendMessage(getDefaultHandler(), topic, DEFAULT_QOS, data);
    }

    /**
     * 给主题发送消息
     *
     * @param topic 主题
     * @param data 消息内容
     */
    public static void sendMessage(String topic, MqttQos qos, Object data) throws KtfException {
        sendMessage(getDefaultHandler(), topic, qos.ordinal(), data);
    }

    /**
     * 使用指定生产者通道给主题发送消息
     *
     * @param producerName 生产者名称
     * @param topic 主题
     * @param data 消息
     */
    public static void sendMessage(String producerName, String topic, MqttQos qos, Object data) throws KtfException {
        if (!HANDLER_MAP.containsKey(producerName)) {
            throw new KtfException(String.format("生产者[%s]不存在", producerName));
        }
        sendMessage(HANDLER_MAP.get(producerName), topic, qos.ordinal(), data);
    }

    /**
     * 使用消息处理器给主题发送消息
     *
     * @param handler 消息处理器
     * @param topic 主题
     * @param data 消息
     */
    private static void sendMessage(MqttPahoMessageHandler handler, String topic, int qos, Object data) {
        // ObjectMapper mapper = new ObjectMapper();
        // String json = mapper.writeValueAsString(data);
        String json = JSON.toJSONString(data);
        Message<String> mqttMessage = MessageBuilder.withPayload(json).setHeader(MqttHeaders.TOPIC, topic)
            .setHeader(MqttHeaders.QOS, qos).build();
        handler.handleMessage(mqttMessage);
        log.info(String.format("MQTT: 向主题[%s]发送消息[%s]", topic, json));
    }
}
