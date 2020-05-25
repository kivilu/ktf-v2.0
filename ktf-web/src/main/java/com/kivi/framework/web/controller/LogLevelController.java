package com.kivi.framework.web.controller;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kivi.framework.util.kit.StrKit;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/log")
@Slf4j
public class LogLevelController {

	@GetMapping(value = "/logback")
	public String logj() {
		log.error("我是error");
		log.warn("我是warn");
		log.info("我是info");
		log.debug("我是debug");
		log.trace("我是trace");
		return "success";
	}

	@GetMapping(value = "level/{logLevel}/{baseClassPackages}")
	public String changeLogLevel(
			@PathVariable("logLevel") String logLevel,
			@PathVariable(value = "baseClassPackages",
					required = false) String baseClassPackages) {
		try {
			LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
			loggerContext.getLogger("org.mybatis").setLevel(Level.valueOf(logLevel));
			loggerContext.getLogger("org.springframework").setLevel(Level.valueOf(logLevel));
			loggerContext.getLogger("com.kivi").setLevel(Level.valueOf(logLevel));
			if (StrKit.isBlank(baseClassPackages)) {
				String[] classPackages = StrKit.split(baseClassPackages, StrKit.COMMA);
				for (String classPackage : classPackages) {
					loggerContext.getLogger(classPackage).setLevel(Level.valueOf(logLevel));
				}
			}

		} catch (Exception e) {
			log.error("动态修改日志级别出错", e);
			return "fail";
		}
		return "success";
	}
}
