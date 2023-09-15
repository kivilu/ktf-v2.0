package com.kivi.mqtt.configuration;

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;

import com.kivi.framework.properties.KtfCommonProperties;
import com.kivi.mqtt.models.ChannelConfig;
import com.kivi.mqtt.models.KtfTopic;
import com.kivi.mqtt.properties.KtfMqttProperties;
import com.kivi.mqtt.utils.MqttUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
@ConditionalOnProperty(prefix = KtfCommonProperties.PREFIX, name = {"enable-mqtt"}, havingValue = "true",
    matchIfMissing = false)
@Configuration
@EnableConfigurationProperties(KtfMqttProperties.class)
public class MqttConfiguration implements ApplicationContextAware {
    public static final String ADAPTER_SUFFIX = "-adapter";
    public static final String HANDLER_SUFFIX = "-handler";
    private ConfigurableApplicationContext applicationContext;
    private final KtfMqttProperties mqttProperties;

    /**
     * 设置应用上下文
     *
     * @param applicationContext 应用上下文
     * @throws BeansException bean异常
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext)applicationContext;

        // mqttProperties.getProducers().forEach(this::initProducer);
        // mqttProperties.getConsumers().forEach(this::initConsumer);
        this.initProducer(KtfMqttProperties.PRODUCER_NAME, mqttProperties.getProducer());
        this.initConsumer(KtfMqttProperties.CONSUMER_NAME, mqttProperties.getConsumer());
    }

    /**
     * 初始化生产者通道
     *
     * @param name 生产者通道名称
     * @param producer 生产者配置
     */
    private void initProducer(String name, ChannelConfig producer) {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getBeanFactory();
        beanFactory.registerBeanDefinition(name, mqttChannel());
        String handlerName = name + HANDLER_SUFFIX;
        beanFactory.registerBeanDefinition(handlerName, mqttOutbound(producer));
        MqttUtil.HANDLER_MAP.put(name, beanFactory.getBean(handlerName, MqttPahoMessageHandler.class));
        log.info(String.format("MQTT: 生产者[%s]已创建", name));
    }

    /**
     * 初始化消费者
     *
     * @param name 消费者通道名称
     * @param consumer 消费者配置
     */
    private void initConsumer(String name, ChannelConfig consumer) {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getBeanFactory();
        beanFactory.registerBeanDefinition(name, mqttChannel());
        MessageChannel mqttChannel = beanFactory.getBean(name, MessageChannel.class);
        beanFactory.registerBeanDefinition(name + ADAPTER_SUFFIX, channelAdapter(consumer, mqttChannel));
        log.info(String.format("MQTT: 消费者[%s]已创建", name));
    }

    /**
     * 初始化mqtt客户端工厂
     *
     * @param channel 通道配置
     * @return 客户端工厂
     */
    public MqttPahoClientFactory mqttClientFactory(ChannelConfig channel) {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        // 是否清空session，设置false表示服务器会保留客户端的连接记录（订阅主题，qos）,客户端重连之后能获取到服务器在客户端断开连接期间推送的消息
        // 设置为true表示每次连接服务器都是以新的身份
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
        options.setCleanSession(true);
        options.setConnectionTimeout(5);
        // 设置心跳时间 单位为秒，表示服务器每隔 1.5*60秒的时间向客户端发送心跳判断客户端是否在线
        options.setKeepAliveInterval(60);
        options.setAutomaticReconnect(true);
        options.setUserName(channel.getUsername());
        options.setPassword(channel.getPassword().toCharArray());
        options.setServerURIs(channel.getUrl());

        factory.setConnectionOptions(options);
        return factory;
    }

    /**
     * 初始化通道
     *
     * @return 通道定义
     */
    private AbstractBeanDefinition mqttChannel() {
        BeanDefinitionBuilder messageChannelBuilder = BeanDefinitionBuilder.genericBeanDefinition(DirectChannel.class);
        messageChannelBuilder.setScope(BeanDefinition.SCOPE_SINGLETON);
        return messageChannelBuilder.getBeanDefinition();
    }

    /**
     * 初始化通道适配器
     *
     * @param channel 通道配置
     * @param mqttChannel 通道定义
     * @return 通道
     */
    private AbstractBeanDefinition channelAdapter(ChannelConfig channel, MessageChannel mqttChannel) {
        List<KtfTopic> ktfTopics =
            channel.getTopics().entrySet().stream().map(Entry::getValue).collect(Collectors.toList());
        String[] topics = new String[ktfTopics.size()];
        int[] qoses = new int[topics.length];
        int i = 0;
        for (KtfTopic t : ktfTopics) {
            topics[i] = t.getTopic();
            qoses[i++] = t.getQos().ordinal();
        }

        BeanDefinitionBuilder messageProducerBuilder =
            BeanDefinitionBuilder.genericBeanDefinition(MqttPahoMessageDrivenChannelAdapter.class);
        messageProducerBuilder.setScope(BeanDefinition.SCOPE_SINGLETON);
        messageProducerBuilder.addConstructorArgValue(channel.getClientId());
        messageProducerBuilder.addConstructorArgValue(mqttClientFactory(channel));
        messageProducerBuilder.addConstructorArgValue(topics);
        messageProducerBuilder.addPropertyValue("converter", new DefaultPahoMessageConverter());
        messageProducerBuilder.addPropertyValue("qos", qoses);
        messageProducerBuilder.addPropertyValue("outputChannel", mqttChannel);
        return messageProducerBuilder.getBeanDefinition();
    }

    /**
     * 初始化生产者出站通道
     *
     * @param channel 通道配置
     * @return 生产者出站通道
     */
    private AbstractBeanDefinition mqttOutbound(ChannelConfig channel) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MqttPahoMessageHandler.class);
        builder.addConstructorArgValue(channel.getClientId());
        builder.addConstructorArgValue(mqttClientFactory(channel));
        builder.addPropertyValue("async", channel.getAsync());
        return builder.getBeanDefinition();
    }

}
