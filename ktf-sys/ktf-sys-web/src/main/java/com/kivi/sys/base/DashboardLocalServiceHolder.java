package com.kivi.sys.base;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.kivi.cif.service.CifCertsService;
import com.kivi.cif.service.CifCustomerAuthsService;
import com.kivi.cif.service.CustomerService;
import com.kivi.framework.properties.KtfCommonProperties;
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

@ConditionalOnProperty(prefix = KtfCommonProperties.PREFIX, name = {"enable-dubbo"}, havingValue = "false",
    matchIfMissing = false)
@Component
public class DashboardLocalServiceHolder {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CifCertsService cifCertsService;

    @Autowired
    private CifCustomerAuthsService customerAuthsService;

    @Autowired
    private OrgCorpService iEnterpriseService;

    @Autowired
    private SysFileService sysFileService;

    @Autowired
    private OrgDeptService iEnterpriseDepartmentService;

    @Autowired
    private OrgTitleService iEnterpriseJobService;

    @Autowired
    private SysUserTokenService userTokenService;

    @Autowired
    private SysApplicationService iSysApplicationService;

    @Autowired
    private SysDicService iSysDicService;

    @Autowired
    private SysIndustryService sysIndustryService;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private SysRegionService iSysRegionService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private SysRoleService iSysRoleService;

    @Autowired
    private SysRoleResourceService sysRoleResourceService;

    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysUserOrgService sysUserEnterpriseService;
    @Autowired
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
