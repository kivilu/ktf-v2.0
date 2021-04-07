package com.kivi.nacos;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.stereotype.Component;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.spring.util.parse.DefaultPropertiesConfigParse;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.CollectionKit;
import com.kivi.framework.util.kit.StrKit;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KtfNacosConfigKit {

    private static final String LOGGER_TAG = "logging.level.";

    @Autowired
    private LoggingSystem loggingSystem;

    @NacosInjected
    private ConfigService configService;

    private class MyEntry<K, V> implements Map.Entry<K, V> {
        private K key;
        private V value;

        public void setKey(K key) {
            this.key = key;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

    }

    /**
     * 读取Nacos配置
     * 
     * @param dataId
     * @param group
     * @param keyPrefix
     * @return
     */
    public Map<String, String> getValue(String dataId, String group, String keyPrefix) {
        try {
            String content = configService.getConfig(dataId, group, 5000);
            log.trace("current config: {}", content);
            Properties curProps = new KtfYamlConfigParse().parse(content);

            Map<String, String> result = curProps.entrySet().stream()
                .filter(ent -> StrKit.startWith(String.valueOf(ent.getKey()), keyPrefix, false)).map(ent -> {
                    MyEntry<String, String> newEnt = new MyEntry<>();
                    newEnt.setKey(String.valueOf(ent.getKey()));
                    newEnt.setValue(String.valueOf(ent.getValue()));
                    return newEnt;
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (k1, k2) -> k1));
            return result;
        } catch (NacosException e) {
            log.error("从Nacos配置中心读取配置异常", e);
            throw new KtfException("从Nacos配置中心读取配置异常");
        }

    }

    @KtfTrace("设置Nacos值")
    public boolean setValue(String dataId, String group, String key, Object value) {
        Map<String, String> values = CollectionKit.newHashMap(key, value.toString());

        return this.setValue(dataId, group, values);
    }

    @KtfTrace("设置Nacos值")
    public boolean setValue(String dataId, String group, Map<String, String> values) {
        boolean isPublishOk = false;
        try {
            String content = configService.getConfig(dataId, group, 5000);
            log.trace("current config: {}", content);
            Properties curProps = new KtfYamlConfigParse().parse(content);
            curProps.putAll(values);

            List<Object> newProps = curProps.entrySet().stream().map(ent -> {
                return StringUtils.joinWith(": ", ent.getKey(), ent.getValue());
            }).sorted().collect(Collectors.toList());

            String newContent = StringUtils.join(newProps, "\n");

            log.trace("new config: {}", newContent);
            isPublishOk = configService.publishConfig(dataId, group, newContent);
        } catch (NacosException e) {
            log.error("向Nacos配置中心设置值异常", e);
            throw new KtfException("向Nacos配置中心设置值异常");
        }

        return isPublishOk;
    }

    @KtfTrace("设置Nacos值")
    public boolean setValue(String dataId, String group, String key, List<Object> values) {
        Map<String, String> props = CollectionKit.newHashMap();
        IntStream.range(0, values.size()).forEach(x -> {
            String realKey = StrKit.join("[", x, "]");
            props.put(realKey, values.get(x).toString());
        });

        return this.setValue(dataId, group, props);
    }

    // @NacosConfigListener(groupId = "${ktf.nacos.loglevel.group-id:KTF_GROUP}",
    // dataId = "${ktf.nacos.loglevel.data-id:logging.level}")
    @KtfTrace("修改日志级别")
    public void onChangeLogLevel(String newLog) throws Exception {
        Map<Object, Object> properties = new DefaultPropertiesConfigParse().parse(newLog);

        for (Object t : properties.keySet()) {
            String key = String.valueOf(t);
            if (key.startsWith(LOGGER_TAG)) {
                String strLevel = (String)properties.getOrDefault(key, "info");
                LogLevel level = LogLevel.valueOf(strLevel.toUpperCase());
                loggingSystem.setLogLevel(key.replace(LOGGER_TAG, ""), level);
                log.info("{}: {}", key, strLevel);
            }
        }
    }

}
