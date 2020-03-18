package com.kivi.dashboard.base;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

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

@ConditionalOnMissingBean(name = { "ktfDubboProperties" })
@Component
public class DashboardLocalServiceHolder {

	@Autowired
	private CifCustomerService				iCustomerService;

	@Autowired
	private CifCertsService					cifCertsService;

	@Autowired
	private CifCustomerAuthsService			customerAuthsService;

	@Autowired
	private IEnterpriseService				iEnterpriseService;

	@Autowired
	private ISysFileService					sysFileService;

	@Autowired
	private IEnterpriseDepartmentService	iEnterpriseDepartmentService;

	@Autowired
	private IEnterpriseJobService			iEnterpriseJobService;

	@Autowired
	private ISysUserTokenService			userTokenService;

	@Autowired
	private ISysApplicationService			iSysApplicationService;

	@Autowired
	private ISysDicService					iSysDicService;

	@Autowired
	private ISysIndustryService				sysIndustryService;

	@Autowired
	private ISysLogService					iSysLogService;

	@Autowired
	private ISysRegionService				iSysRegionService;

	@Autowired
	private ISysUserService					sysUserService;

	@Autowired
	private ISysResourceService				sysResourceService;

	@Autowired
	private ISysRoleService					iSysRoleService;

	@Autowired
	private ISysRoleResourceService			sysRoleResourceService;

	@Autowired
	private ISysUserRoleService				sysUserRoleService;
	@Autowired
	private ISysUserEnterpriseService		sysUserEnterpriseService;
	@Autowired
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
