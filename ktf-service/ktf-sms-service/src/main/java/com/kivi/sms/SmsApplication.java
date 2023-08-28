package com.kivi.sms;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kivi.framework.annotation.EnableKTF;

@EnableKTF
@EnableAutoConfiguration
@SpringBootApplication
@EnableDubbo
public class SmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmsApplication.class, args);

	}
}
