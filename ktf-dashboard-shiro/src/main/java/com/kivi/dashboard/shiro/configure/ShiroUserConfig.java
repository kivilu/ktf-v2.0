package com.kivi.dashboard.shiro.configure;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kivi.dashboard.permission.service.SysResourceService;
import com.kivi.dashboard.permission.service.SysUserOrgService;
import com.kivi.dashboard.permission.service.SysUserService;
import com.kivi.dashboard.shiro.service.ShiroUserService;
import com.kivi.dashboard.shiro.service.impl.ShiroUserServiceImpl;
import com.kivi.framework.properties.KtfDashboardProperties;

/**
 * @Description Apache Shiro配置类
 */

@Configuration
public class ShiroUserConfig {

	@Autowired(required = false)
	private SysUserService		userService;
	@Autowired(required = false)
	private SysResourceService	resourceService;
	@Autowired(required = false)
	private SysUserOrgService	userEnterpriseService;

	@DubboReference(version = KtfDashboardProperties.DUBBO_VERSION, check = false)
	private SysUserService		dubboUserService;
	@DubboReference(version = KtfDashboardProperties.DUBBO_VERSION, check = false)
	private SysResourceService	dubboResourceService;
	@DubboReference(version = KtfDashboardProperties.DUBBO_VERSION, check = false)
	private SysUserOrgService	dubboUserEnterpriseService;

	/*
	 * @ConditionalOnProperty( prefix = KtfDashboardProperties.PREFIX, value =
	 * "enable-dubbo", havingValue = "false", matchIfMissing = true)
	 */
	@ConditionalOnMissingClass(value = { "com.kivi.dubbo.annotation.EnableKtfDubbo" })
	@Bean("shiroUserService")
	public ShiroUserService shiroUserService() {

		return new ShiroUserServiceImpl(userService, resourceService, userEnterpriseService);
	}

	/*
	 * @ConditionalOnProperty( prefix = KtfDashboardProperties.PREFIX, value =
	 * "enable-dubbo", havingValue = "true", matchIfMissing = false)
	 */
	@ConditionalOnClass(name = { "com.kivi.dubbo.annotation.EnableKtfDubbo" })
	@Bean("shiroUserServiceDubbo")
	public ShiroUserService shiroUserServiceDubbo() {

		return new ShiroUserServiceImpl(dubboUserService, dubboResourceService, dubboUserEnterpriseService);
	}

}
