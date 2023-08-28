package com.kivi.demo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kivi.framework.annotation.EnableKTF;

@EnableKTF
@EnableAutoConfiguration
@RestController
@SpringBootApplication
@EnableDubbo
public class DubboConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboConsumerApplication.class, args);
	}

	@RequestMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/doc.html");
		return mv;
	}

}
