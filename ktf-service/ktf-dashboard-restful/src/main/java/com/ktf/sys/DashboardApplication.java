package com.ktf.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.kivi.framework.annotation.EnableKTF;
import com.kivi.sys.sys.service.ISysRegionService;

/**
 * @desc: springboot入口
 * 
 */
@EnableKTF
@SpringBootApplication
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)
public class DashboardApplication {

	@Autowired
	ISysRegionService sysRegionService;

	public static void main(String[] args) {
		SpringApplication.run(DashboardApplication.class, args);
	}

}
