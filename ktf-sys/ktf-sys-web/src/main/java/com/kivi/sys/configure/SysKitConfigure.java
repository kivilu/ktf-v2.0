package com.kivi.sys.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kivi.cif.auth.CifAuthentication;
import com.kivi.cif.service.CifCustomerAuthsService;
import com.kivi.framework.properties.KtfCommonProperties;
import com.kivi.shiro.service.SysKit;
import com.kivi.sys.permission.service.SysResourceService;
import com.kivi.sys.permission.service.SysUserOrgService;
import com.kivi.sys.permission.service.SysUserService;
import com.kivi.sys.sys.service.SysDicService;
import com.kivi.sys.sys.service.SysLogService;

/**
 * @Description Apache Shiro配置类
 */

@Configuration
@ConditionalOnProperty(prefix = KtfCommonProperties.PREFIX, name = {"enable-dubbo"}, havingValue = "false",
    matchIfMissing = true)
public class SysKitConfigure {

    @Autowired
    private SysUserService userService;
    @Autowired
    private SysResourceService resourceService;
    @Autowired
    private SysUserOrgService userOrgService;
    @Autowired
    private CifCustomerAuthsService cifAuthService;

    @Autowired
    private SysDicService sysDicService;

    @Autowired
    private SysLogService logService;

    @Autowired
    private CifAuthentication cifAuthentication;

    @Bean
    public SysKit sysKit() {

        return new SysKit(userService, resourceService, userOrgService, cifAuthService, sysDicService, logService,
            cifAuthentication);
    }

}
