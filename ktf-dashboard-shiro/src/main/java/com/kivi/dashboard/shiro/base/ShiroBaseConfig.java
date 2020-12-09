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
		filterChainDefinitionMap.put("/favicon.ico", "anon");// 网站图标
		filterChainDefinitionMap.put("/static/**", "anon");// 配置static文件下资源能被访问
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/font/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/plugins/**", "anon");
		filterChainDefinitionMap.put("/captcha.jpg", "anon");// 图片验证码(kaptcha框架)
		filterChainDefinitionMap.put("/captcha/status", "anon");
		filterChainDefinitionMap.put("/xlsFile/**", "anon");
		filterChainDefinitionMap.put("/upload/**", "anon");
		// filterChainDefinitionMap.put("/api/**", "anon");// API接口

		// swagger接口文档
		filterChainDefinitionMap.put("/v2/api-docs", "anon");
		filterChainDefinitionMap.put("/v2/api-docs-ext", "anon");
		filterChainDefinitionMap.put("/webjars/**", "anon");
		filterChainDefinitionMap.put("/swagger-resources/**", "anon");
		filterChainDefinitionMap.put("/swagger-ui.html", "anon");
		filterChainDefinitionMap.put("/doc.html", "anon");

		// 其他的
		filterChainDefinitionMap.put("/druid/**", "anon");
		filterChainDefinitionMap.put("/actuator/**", "anon");
		filterChainDefinitionMap.put("/ws/**", "anon");
		filterChainDefinitionMap.put("/qr/**", "anon");
		filterChainDefinitionMap.put("/test/**", "anon");
		filterChainDefinitionMap.put("/nonce", "anon");
		filterChainDefinitionMap.put("/**/nonce", "anon");
		filterChainDefinitionMap.put("/login/settings", "anon");
		filterChainDefinitionMap.put("/sys/login", "anon");
		filterChainDefinitionMap.put("/login", "anon");

		List<String> anonList = ktfProperties().getShiro().getAnonFilter();
		if (CollectionKit.isNotEmpty(anonList)) {
			anonList.stream().forEach(anon -> {
				filterChainDefinitionMap.put(anon, "anon");
			});
		}

		return filterChainDefinitionMap;
	}

}
