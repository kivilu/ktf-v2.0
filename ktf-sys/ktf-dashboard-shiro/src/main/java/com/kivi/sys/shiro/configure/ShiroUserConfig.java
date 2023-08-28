package com.kivi.sys.shiro.configure;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kivi.cif.properties.CifProperties;
import com.kivi.cif.service.CifCustomerAuthsService;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.sys.permission.service.SysResourceService;
import com.kivi.sys.permission.service.SysUserOrgService;
import com.kivi.sys.permission.service.SysUserService;
import com.kivi.sys.shiro.service.ShiroUserService;
import com.kivi.sys.shiro.service.impl.ShiroUserServiceImpl;

/**
 * @Description Apache Shiro配置类
 */

@Configuration
public class ShiroUserConfig {

	@Autowired(required = false)
	private SysUserService			userService;
	@Autowired(required = false)
	private SysResourceService		resourceService;
	@Autowired(required = false)
	private SysUserOrgService		userEnterpriseService;
	@Autowired(required = false)
	private CifCustomerAuthsService	cifAuthService;

	@DubboReference(version = KtfDashboardProperties.DUBBO_VERSION, check = false)
	private SysUserService			dubboUserService;
	@DubboReference(version = KtfDashboardProperties.DUBBO_VERSION, check = false)
	private SysResourceService		dubboResourceService;
	@DubboReference(version = KtfDashboardProperties.DUBBO_VERSION, check = false)
	private SysUserOrgService		dubboUserEnterpriseService;

	@DubboReference(version = CifProperties.DUBBO_VERSION, check = false)
	private CifCustomerAuthsService	dubboCifAuthService;

	/*
	 * @ConditionalOnProperty( prefix = KtfDashboardProperties.PREFIX, value =
	 * "enable-dubbo", havingValue = "false", matchIfMissing = true)
	 */
	@ConditionalOnMissingClass(value = { "com.kivi.dubbo.configuration.KtfDubboConfiguration" })
	@Bean("shiroUserService")
	public ShiroUserService shiroUserService() {

		return new ShiroUserServiceImpl(userService, resourceService, userEnterpriseService, cifAuthService);
	}

	/*
	 * @ConditionalOnProperty( prefix = KtfDashboardProperties.PREFIX, value =
	 * "enable-dubbo", havingValue = "true", matchIfMissing = false)
	 */
	@ConditionalOnClass(name = { "com.kivi.dubbo.configuration.KtfDubboConfiguration" })
	@Bean("shiroUserServiceDubbo")
	public ShiroUserService shiroUserServiceDubbo() {

		return new ShiroUserServiceImpl(dubboUserService, dubboResourceService, dubboUserEnterpriseService,
				dubboCifAuthService);
	}

}
