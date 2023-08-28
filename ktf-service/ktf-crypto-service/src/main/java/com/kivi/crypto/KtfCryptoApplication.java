package com.kivi.crypto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kivi.dubbo.annotation.EnableKtfDubbo;
import com.kivi.framework.annotation.EnableKTF;

@EnableKTF
@EnableKtfDubbo
@EnableAutoConfiguration
@SpringBootApplication
public class KtfCryptoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KtfCryptoApplication.class, args);

	}

}
