package com.kivi.dashboard.base;

import javax.annotation.PostConstruct;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import com.kivi.cif.properties.CifProperties;
import com.kivi.cif.service.CifCertsService;
import com.kivi.cif.service.CifCustomerAuthsService;
import com.kivi.cif.service.CifCustomerService;
import com.kivi.dashboard.enterprise.service.IEnterpriseDepartmentService;
import com.kivi.dashboard.enterprise.service.IEnterpriseJobService;
import com.kivi.dashboard.enterprise.service.IEnterpriseService;
import com.kivi.dashboard.sys.service.ISysApplicationService;
import com.kivi.dashboard.sys.service.ISysDicService;
import com.kivi.dashboard.sys.service.ISysFileService;
import com.kivi.dashboard.sys.service.ISysIndustryService;
import com.kivi.dashboard.sys.service.ISysLogService;
import com.kivi.dashboard.sys.service.ISysRegionService;
import com.kivi.dashboard.sys.service.ISysResourceService;
import com.kivi.dashboard.sys.service.ISysRoleResourceService;
import com.kivi.dashboard.sys.service.ISysRoleService;
import com.kivi.dashboard.sys.service.ISysUserEnterpriseService;
import com.kivi.dashboard.sys.service.ISysUserRoleService;
import com.kivi.dashboard.sys.service.ISysUserService;
import com.kivi.dashboard.sys.service.ISysUserTokenService;
import com.kivi.dashboard.sys.service.SysApi3rdpartyService;
import com.kivi.framework.properties.KtfDashboardProperties;

@ConditionalOnBean(name = { "ktfDubboProperties" })
@Component
public class DashboardDubboServiceHolder {

	@Reference(check = false, version = CifProperties.DUBBO_VERSION)
	private CifCertsService					cifCertsService;

	@Reference(check = false, version = KtfDashboardProperties.DUBBO_VERSION)
	private CifCustomerService				iCustomerService;

	@Reference(check = false, version = KtfDashboardProperties.DUBBO_VERSION)
	private CifCustomerAuthsService			customerAuthsService;

	@Reference(check = false, version = KtfDashboardProperties.DUBBO_VERSION)
	private IEnterpriseService				iEnterpriseService;

	@Reference(check = false, version = KtfDashboardProperties.DUBBO_VERSION)
	private ISysFileService					sysFileService;

	@Reference(check = false, version = KtfDashboardProperties.DUBBO_VERSION)
	private IEnterpriseDepartmentService	iEnterpriseDepartmentService;

	@Reference(check = false, version = KtfDashboardProperties.DUBBO_VERSION)
	private IEnterpriseJobService			iEnterpriseJobService;

	@Reference(check = false, version = KtfDashboardProperties.DUBBO_VERSION)
	private ISysUserTokenService			userTokenService;

	@Reference(check = false, version = KtfDashboardProperties.DUBBO_VERSION)
	private ISysApplicationService			iSysApplicationService;

	@Reference(check = false, version = KtfDashboardProperties.DUBBO_VERSION)
	private ISysDicService					iSysDicService;

	@Reference(check = false, version = KtfDashboardProperties.DUBBO_VERSION)
	private ISysIndustryService				sysIndustryService;

	@Reference(check = false, version = KtfDashboardProperties.DUBBO_VERSION)
	private ISysLogService					iSysLogService;

	@Reference(check = false, version = KtfDashboardProperties.DUBBO_VERSION)
	private ISysRegionService				iSysRegionService;

	@Reference(check = false, version = KtfDashboardProperties.DUBBO_VERSION)
	private ISysUserService					sysUserService;

	@Reference(check = false, version = KtfDashboardProperties.DUBBO_VERSION)
	private ISysResourceService				sysResourceService;

	@Reference(check = false, version = KtfDashboardProperties.DUBBO_VERSION)
	private ISysRoleService					iSysRoleService;

	@Reference(check = false, version = KtfDashboardProperties.DUBBO_VERSION)
	private ISysRoleResourceService			sysRoleResourceService;

	@Reference(check = false, version = KtfDashboardProperties.DUBBO_VERSION)
	private ISysUserRoleService				sysUserRoleService;

	@Reference(check = false, version = KtfDashboardProperties.DUBBO_VERSION)
	private ISysUserEnterpriseService		sysUserEnterpriseService;

	@Reference(check = false, version = KtfDashboardProperties.DUBBO_VERSION)
	private SysApi3rdpartyService			sysApi3rdpartyService;

	@PostConstruct
	void registBeans() {
		DashboardController.registBean(CifCustomerService.class.getName(), iCustomerService);
		DashboardController.registBean(CifCertsService.class.getName(), cifCertsService);
		DashboardController.registBean(CifCustomerAuthsService.class.getName(), customerAuthsService);
		DashboardController.registBean(IEnterpriseService.class.getName(), iEnterpriseService);
		DashboardController.registBean(ISysFileService.class.getName(), sysFileService);
		DashboardController.registBean(IEnterpriseDepartmentService.class.getName(), iEnterpriseDepartmentService);
		DashboardController.registBean(IEnterpriseJobService.class.getName(), iEnterpriseJobService);
		DashboardController.registBean(ISysUserTokenService.class.getName(), userTokenService);
		DashboardController.registBean(ISysApplicationService.class.getName(), iSysApplicationService);
		DashboardController.registBean(ISysDicService.class.getName(), iSysDicService);
		DashboardController.registBean(ISysIndustryService.class.getName(), sysIndustryService);
		DashboardController.registBean(ISysLogService.class.getName(), iSysLogService);
		DashboardController.registBean(ISysRegionService.class.getName(), iSysRegionService);
		DashboardController.registBean(ISysUserService.class.getName(), sysUserService);

		DashboardController.registBean(ISysResourceService.class.getName(), sysResourceService);
		DashboardController.registBean(ISysRoleService.class.getName(), iSysRoleService);
		DashboardController.registBean(ISysRoleResourceService.class.getName(), sysRoleResourceService);
		DashboardController.registBean(ISysUserRoleService.class.getName(), sysUserRoleService);
		DashboardController.registBean(ISysUserEnterpriseService.class.getName(), sysUserEnterpriseService);
		DashboardController.registBean(SysApi3rdpartyService.class.getName(), sysApi3rdpartyService);
	}

}
