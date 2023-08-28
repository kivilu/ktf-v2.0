package com.example.zknacos.demozknacos;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class DemoZknacosApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoZknacosApplication.class, args);
    }

}
