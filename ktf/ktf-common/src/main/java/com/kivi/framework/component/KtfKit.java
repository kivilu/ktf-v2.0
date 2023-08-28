package com.kivi.framework.component;

import java.io.IOException;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.kivi.framework.exception.ToolBoxException;
import com.kivi.framework.service.ISessionNotify;
import com.kivi.framework.util.IdWalker;
import com.kivi.framework.util.kit.StrKit;

/**
 * 应用帮助类，主要用于获取SpringBoot应用的相关信息
 * 
 * @author Eric
 *
 */
@Component
public class KtfKit {

	@Autowired
	private ApplicationContext				applicationContext;

	@Autowired
	private Environment						env;

	private static HashSet<ISessionNotify>	sessionNotifySet	= new HashSet<>();

	private static IdWalker					idWalker			= new IdWalker();

	public static KtfKit me() {
		return SpringContextHolder.getBean(KtfKit.class);
	}

	public static long nextId() {
		return idWalker.nextId();
	}

	public static int nextIntId() {
		return idWalker.nextIntId();
	}

	public int getServerPort() {
		String port = getEnvProperty("server.port");
		return Integer.parseInt(port);
	}

	public String getServerContext() {
		String context = getEnvProperty("server.servlet.context");
		return context;
	}

	public String getAppcationName() {
		String name = getEnvProperty("spring.application.name");
		return name;
	}

	public String getEnvProperty(String key) {
		return env.getProperty(key);
	}

	public Boolean isActiveDev() {
		return StrKit.equals("dev", getEnvProperty("spring.profiles.active"));
	}

	public Resource[] getResources(String locationPattern) {
		Resource[] resources = null;

		try {
			resources = applicationContext.getResources(locationPattern);
		} catch (IOException e) {
			throw new ToolBoxException("获取资源文件异常", e);
		}

		return resources;
	}

	public void sessionCreated(String id) {

	}

	public void sessionDestroyed(String id) {
		sessionNotifySet.stream().forEach(notify -> notify.onDestroyed(id));
	}

}
