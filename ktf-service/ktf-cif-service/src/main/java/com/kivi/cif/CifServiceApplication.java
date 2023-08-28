package com.kivi.cif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kivi.framework.annotation.EnableKTF;

@EnableKTF
@EnableAutoConfiguration
@SpringBootApplication
public class CifServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CifServiceApplication.class, args);

	}

}
