package com.example.demonoweb;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class DemoNowebApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoNowebApplication.class, args);
    }

}
