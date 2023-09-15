/**
 * 
 */
package com.kivi.mqtt.properties;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.TypeReference;
import com.kivi.framework.properties.IKtfProperties;
import com.kivi.framework.properties.KtfCommonProperties;
import com.kivi.framework.util.kit.ResKit;
import com.kivi.framework.util.kit.StrKit;
import com.kivi.mqtt.enums.MqttQos;
import com.kivi.mqtt.models.ChannelConfig;
import com.kivi.mqtt.models.KtfTopic;
import com.kivi.mqtt.models.KtfTopicBiz;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kivi
 *
 */
@Slf4j
@Data
@ConditionalOnProperty(prefix = KtfCommonProperties.PREFIX, name = {"enable-mqtt"}, havingValue = "true",
    matchIfMissing = false)
@ConfigurationProperties(prefix = KtfMqttProperties.PREFIX)
public class KtfMqttProperties implements IKtfProperties {
    public static final String BEAN_NAME = "ktfMqttProperties";
    public static final String PREFIX = "ktf.mqtt";

    public static final String CONSUMER_NAME = "MQTT.CONSUMER";
    public static final String PRODUCER_NAME = "MQTT.PRODUCER";
    public static final String DEFAULT_CONSUMER_TOPIC = "consumer/+/test";
    public static final String DEFAULT_PRODUCER_TOPIC = "producer/+/test";

    /**
     * 消费端配置
     */
    ChannelConfig consumer;

    /**
     * 生产端配置
     */
    ChannelConfig producer;

    /**
     * topic json文件
     */
    private String topicJson = "classpath:tucp.json";

    private Map<String, KtfTopicBiz> topics;

    @PostConstruct
    private void init() {
        if (null == this.getConsumer())
            return;

        KtfTopic t = new KtfTopic().setTopic(DEFAULT_CONSUMER_TOPIC).setQos(MqttQos.qos0);
        this.getConsumer().getTopics().put(DEFAULT_CONSUMER_TOPIC, t);

        String topicFile = ResKit.getFilepath(this.getTopicJson());
        try (FileInputStream fin = new FileInputStream(topicFile);) {
            JSONObject jsonObj = JSON.parseObject(fin);

            TypeReference<Map<String, KtfTopicBiz>> type = new TypeReference<Map<String, KtfTopicBiz>>() {};
            this.topics = jsonObj.to(type.getType(), JSONReader.Feature.ErrorOnEnumNotMatch);
            this.topics.forEach(this::parseTopic);

        } catch (Exception e) {
            log.error("加载topic文件异常", e);
        }
    }

    private void parseTopic(String name, KtfTopicBiz bizTopic) {
        bizTopic.setName(name);
        bizTopic.setPattern(Pattern.compile(bizTopic.getRegex()));

        if (StrKit.equalsIgnoreCase(bizTopic.getAction(), "sub")) {
            String topic = bizTopic.getTopic().replaceAll(KtfTopicBiz.REGEX, "+");
            if (!this.getConsumer().getTopics().containsKey(topic)) {
                KtfTopic t = new KtfTopic().setTopic(topic).setQos(bizTopic.getQos());
                this.getConsumer().getTopics().put(topic, t);
                log.info("Subscribe topic: {}", topic);
            }
        }
    }

    @Override
    public String prefix() {
        return PREFIX;
    }

    @Override
    public String beanName() {
        return BEAN_NAME;
    }

    public KtfTopicBiz getTopic(String name) {
        return this.topics.get(name.toLowerCase());
    }

    public KtfTopicBiz findTopic(String topic) {
        Optional<Entry<String, KtfTopicBiz>> op = this.topics.entrySet().stream().filter(ent -> {
            Matcher matcher = ent.getValue().getPattern().matcher(topic);
            return matcher.find();
        }).findFirst();

        return op.isPresent() ? null : op.get().getValue();
    }
}
