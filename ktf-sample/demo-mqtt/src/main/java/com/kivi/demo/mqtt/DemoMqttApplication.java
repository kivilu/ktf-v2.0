/**
 * 
 */
package com.kivi.demo.mqtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kivi.framework.annotation.EnableKTF;

/**
 * @author kivi
 *
 */
@EnableKTF
@SpringBootApplication
public class DemoMqttApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoMqttApplication.class, args);
    }

}
