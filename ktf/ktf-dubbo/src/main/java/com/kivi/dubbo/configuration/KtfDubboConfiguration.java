package com.kivi.dubbo.configuration;

import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.nacos.api.config.ConfigService;
import com.kivi.nacos.KtfNacosConfigKit;

@Configuration
@EnableDubboConfig
public class KtfDubboConfiguration {

//	@NacosInjected
	ConfigService configService;

	@Bean
	ServiceParameterBeanPostProcessor serviceParameterBeanPostProcessor() {
		return new ServiceParameterBeanPostProcessor();
	}

	@Bean("ktfNacosConfigKit")
	@ConditionalOnProperty(prefix = "ktf.nacos", name = "enabled", havingValue = "true", matchIfMissing = false)
	KtfNacosConfigKit ktfNacosConfigKit(@Autowired LoggingSystem loggingSystem) {
		return new KtfNacosConfigKit(loggingSystem, configService);
	}

}
