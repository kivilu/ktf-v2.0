package com.kivi.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kivi.dubbo.annotation.EnableKtfDubbo;
import com.kivi.framework.annotation.EnableKTF;

@RestController
@EnableKTF
@EnableKtfDubbo
@EnableAutoConfiguration
@SpringBootApplication
public class SmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmsApplication.class, args);

	}

	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/swagger/index.html");

		return mv;
	}

}
