package com.ktf.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.kivi.dashboard.sys.service.ISysRegionService;
import com.kivi.framework.annotation.EnableKTF;

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
