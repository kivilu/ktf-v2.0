package com.kivi.dashboard.shiro.base;

import java.util.List;
import java.util.Map;

import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.util.kit.CollectionKit;

public abstract class ShiroBaseConfig {

	protected abstract KtfDashboardProperties ktfProperties();

	/**
	 * 开放资源表
	 * 
	 * @param filterName
	 * @return
	 */
	protected Map<String, String> filterChainAnonMap(Map<String, String> filterChainDefinitionMap) {
		// 开放的静态资源
		filterChainDefinitionMap.put("/favicon.ico", "noSessionCreation,anon");// 网站图标
		filterChainDefinitionMap.put("/static/**", "noSessionCreation,anon");// 配置static文件下资源能被访问
		filterChainDefinitionMap.put("/css/**", "noSessionCreation,anon");
		filterChainDefinitionMap.put("/font/**", "noSessionCreation,anon");
		filterChainDefinitionMap.put("/img/**", "noSessionCreation,anon");
		filterChainDefinitionMap.put("/js/**", "noSessionCreation,anon");
		filterChainDefinitionMap.put("/plugins/**", "noSessionCreation,anon");
		filterChainDefinitionMap.put("/captcha.jpg", "noSessionCreation,anon");// 图片验证码(kaptcha框架)
		filterChainDefinitionMap.put("/captcha/status", "noSessionCreation,anon");
		filterChainDefinitionMap.put("/xlsFile/**", "noSessionCreation,anon");
		filterChainDefinitionMap.put("/upload/**", "noSessionCreation,anon");
		// filterChainDefinitionMap.put("/api/**", "anon");// API接口

		// swagger接口文档
		filterChainDefinitionMap.put("/v2/api-docs", "anon");
		filterChainDefinitionMap.put("/v2/api-docs-ext", "anon");
		filterChainDefinitionMap.put("/webjars/**", "anon");
		filterChainDefinitionMap.put("/swagger-resources/**", "anon");
		filterChainDefinitionMap.put("/swagger-ui.html", "anon");
		filterChainDefinitionMap.put("/doc.html", "anon");

		// 其他的
		filterChainDefinitionMap.put("/druid/**", "noSessionCreation,anon");
		filterChainDefinitionMap.put("/actuator/**", "noSessionCreation,anon");
		filterChainDefinitionMap.put("/ws/**", "anon");
		filterChainDefinitionMap.put("/qr/**", "noSessionCreation,anon");
		filterChainDefinitionMap.put("/test/**", "noSessionCreation,anon");
		filterChainDefinitionMap.put("/nonce", "anon");
		filterChainDefinitionMap.put("/**/nonce", "anon");
		filterChainDefinitionMap.put("/login/settings", "anon");
		filterChainDefinitionMap.put("/sys/login", "anon");
		filterChainDefinitionMap.put("/login", "anon"); // login不做认证，noSessionCreation的作用是用户在操作session时会抛异常
		filterChainDefinitionMap.put("/logout", "authcToken[permissive]");

		List<String> anonList = ktfProperties().getShiro().getAnonFilter();
		if (CollectionKit.isNotEmpty(anonList)) {
			anonList.stream().forEach(anon -> {
				filterChainDefinitionMap.put(anon, "anon");
			});
		}

		return filterChainDefinitionMap;
	}

}
