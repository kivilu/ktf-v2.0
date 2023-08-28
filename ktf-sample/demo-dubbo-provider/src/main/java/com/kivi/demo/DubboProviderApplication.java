package com.kivi.demo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.kivi.framework.annotation.EnableKTF;

@EnableKTF
@EnableAutoConfiguration
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableDubbo
public class DubboProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboProviderApplication.class, args);
	}

}
