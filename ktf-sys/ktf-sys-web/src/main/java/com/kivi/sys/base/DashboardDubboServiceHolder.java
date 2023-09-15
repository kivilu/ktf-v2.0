package com.kivi.sys.base;

import javax.annotation.PostConstruct;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.kivi.cif.properties.CifProperties;
import com.kivi.cif.service.CifCertsService;
import com.kivi.cif.service.CifCustomerAuthsService;
import com.kivi.cif.service.CustomerService;
import com.kivi.framework.properties.KtfCommonProperties;
import com.kivi.framework.properties.KtfSysProperties;
import com.kivi.sys.org.service.OrgCorpService;
import com.kivi.sys.org.service.OrgDeptService;
import com.kivi.sys.org.service.OrgTitleService;
import com.kivi.sys.permission.service.SysResourceService;
import com.kivi.sys.permission.service.SysRoleResourceService;
import com.kivi.sys.permission.service.SysRoleService;
import com.kivi.sys.permission.service.SysUserOrgService;
import com.kivi.sys.permission.service.SysUserRoleService;
import com.kivi.sys.permission.service.SysUserService;
import com.kivi.sys.permission.service.SysUserTokenService;
import com.kivi.sys.sys.service.SysApi3rdpartyService;
import com.kivi.sys.sys.service.SysApplicationService;
import com.kivi.sys.sys.service.SysDicService;
import com.kivi.sys.sys.service.SysFileService;
import com.kivi.sys.sys.service.SysIndustryService;
import com.kivi.sys.sys.service.SysLogService;
import com.kivi.sys.sys.service.SysRegionService;

@ConditionalOnProperty(prefix = KtfCommonProperties.PREFIX, name = {"enable-dubbo"}, havingValue = "true",
    matchIfMissing = false)
@Component
public class DashboardDubboServiceHolder {

    @DubboReference(check = false, version = CifProperties.DUBBO_VERSION)
    private CifCertsService cifCertsService;

    @DubboReference(check = false, version = KtfSysProperties.DUBBO_VERSION)
    private CustomerService customerService;

    @DubboReference(check = false, version = KtfSysProperties.DUBBO_VERSION)
    private CifCustomerAuthsService customerAuthsService;

    @DubboReference(check = false, version = KtfSysProperties.DUBBO_VERSION)
    private OrgCorpService iEnterpriseService;

    @DubboReference(check = false, version = KtfSysProperties.DUBBO_VERSION)
    private SysFileService sysFileService;

    @DubboReference(check = false, version = KtfSysProperties.DUBBO_VERSION)
    private OrgDeptService iEnterpriseDepartmentService;

    @DubboReference(check = false, version = KtfSysProperties.DUBBO_VERSION)
    private OrgTitleService iEnterpriseJobService;

    @DubboReference(check = false, version = KtfSysProperties.DUBBO_VERSION)
    private SysUserTokenService userTokenService;

    @DubboReference(check = false, version = KtfSysProperties.DUBBO_VERSION)
    private SysApplicationService iSysApplicationService;

    @DubboReference(check = false, version = KtfSysProperties.DUBBO_VERSION)
    private SysDicService iSysDicService;

    @DubboReference(check = false, version = KtfSysProperties.DUBBO_VERSION)
    private SysIndustryService sysIndustryService;

    @DubboReference(check = false, version = KtfSysProperties.DUBBO_VERSION)
    private SysLogService sysLogService;

    @DubboReference(check = false, version = KtfSysProperties.DUBBO_VERSION)
    private SysRegionService iSysRegionService;

    @DubboReference(check = false, version = KtfSysProperties.DUBBO_VERSION)
    private SysUserService sysUserService;

    @DubboReference(check = false, version = KtfSysProperties.DUBBO_VERSION)
    private SysResourceService sysResourceService;

    @DubboReference(check = false, version = KtfSysProperties.DUBBO_VERSION)
    private SysRoleService iSysRoleService;

    @DubboReference(check = false, version = KtfSysProperties.DUBBO_VERSION)
    private SysRoleResourceService sysRoleResourceService;

    @DubboReference(check = false, version = KtfSysProperties.DUBBO_VERSION)
    private SysUserRoleService sysUserRoleService;

    @DubboReference(check = false, version = KtfSysProperties.DUBBO_VERSION)
    private SysUserOrgService sysUserEnterpriseService;

    @DubboReference(check = false, version = KtfSysProperties.DUBBO_VERSION)
    private SysApi3rdpartyService sysApi3rdpartyService;

    @PostConstruct
    void registBeans() {
        DashboardController.registBean(CustomerService.class.getName(), customerService);
        DashboardController.registBean(CifCertsService.class.getName(), cifCertsService);
        DashboardController.registBean(CifCustomerAuthsService.class.getName(), customerAuthsService);
        DashboardController.registBean(OrgCorpService.class.getName(), iEnterpriseService);
        DashboardController.registBean(SysFileService.class.getName(), sysFileService);
        DashboardController.registBean(OrgDeptService.class.getName(), iEnterpriseDepartmentService);
        DashboardController.registBean(OrgTitleService.class.getName(), iEnterpriseJobService);
        DashboardController.registBean(SysUserTokenService.class.getName(), userTokenService);
        DashboardController.registBean(SysApplicationService.class.getName(), iSysApplicationService);
        DashboardController.registBean(SysDicService.class.getName(), iSysDicService);
        DashboardController.registBean(SysIndustryService.class.getName(), sysIndustryService);
        DashboardController.registBean(SysLogService.class.getName(), sysLogService);
        DashboardController.registBean(SysRegionService.class.getName(), iSysRegionService);
        DashboardController.registBean(SysUserService.class.getName(), sysUserService);

        DashboardController.registBean(SysResourceService.class.getName(), sysResourceService);
        DashboardController.registBean(SysRoleService.class.getName(), iSysRoleService);
        DashboardController.registBean(SysRoleResourceService.class.getName(), sysRoleResourceService);
        DashboardController.registBean(SysUserRoleService.class.getName(), sysUserRoleService);
        DashboardController.registBean(SysUserOrgService.class.getName(), sysUserEnterpriseService);
        DashboardController.registBean(SysApi3rdpartyService.class.getName(), sysApi3rdpartyService);
    }

}
