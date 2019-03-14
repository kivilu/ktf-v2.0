package com.kivi.dubbo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KtfDubboConfiguration {
    @Bean
    ServiceParameterBeanPostProcessor serviceParameterBeanPostProcessor() {
        return new ServiceParameterBeanPostProcessor();
    }
}
