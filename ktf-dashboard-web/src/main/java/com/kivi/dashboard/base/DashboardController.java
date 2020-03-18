package com.kivi.dashboard.base;

import java.util.concurrent.ConcurrentHashMap;

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
import com.kivi.framework.web.controller.BaseController;

public abstract class DashboardController extends BaseController {

	private static ConcurrentHashMap<String, Object> beanMap = new ConcurrentHashMap<>();

	public static void registBean(String className, Object bean) {
		beanMap.put(className, bean);
	}

	protected CifCustomerService customerService() {
		return (CifCustomerService) beanMap.get(CifCustomerService.class.getName());
	}

	protected CifCertsService cifCertsService() {
		return (CifCertsService) beanMap.get(CifCertsService.class.getName());
	}

	protected CifCustomerAuthsService customerAuthsService() {
		return (CifCustomerAuthsService) beanMap.get(CifCustomerAuthsService.class.getName());
	}

	protected IEnterpriseService enterpriseService() {
		return (IEnterpriseService) beanMap.get(IEnterpriseService.class.getName());
	}

	protected ISysFileService sysFileService() {
		return (ISysFileService) beanMap.get(ISysFileService.class.getName());
	}

	protected IEnterpriseDepartmentService enterpriseDepartmentService() {
		return (IEnterpriseDepartmentService) beanMap.get(IEnterpriseDepartmentService.class.getName());
	}

	protected IEnterpriseJobService enterpriseJobService() {
		return (IEnterpriseJobService) beanMap.get(IEnterpriseJobService.class.getName());
	}

	protected ISysUserTokenService sysUserTokenService() {
		return (ISysUserTokenService) beanMap.get(ISysUserTokenService.class.getName());
	}

	protected ISysApplicationService sysApplicationService() {
		return (ISysApplicationService) beanMap.get(ISysApplicationService.class.getName());
	}

	protected ISysDicService sysDicService() {
		return (ISysDicService) beanMap.get(ISysDicService.class.getName());
	}

	protected ISysIndustryService sysIndustryService() {
		return (ISysIndustryService) beanMap.get(ISysIndustryService.class.getName());
	}

	protected ISysLogService sysLogService() {
		return (ISysLogService) beanMap.get(ISysLogService.class.getName());
	}

	protected ISysRegionService sysRegionService() {
		return (ISysRegionService) beanMap.get(ISysRegionService.class.getName());
	}

	protected ISysUserService sysUserService() {
		return (ISysUserService) beanMap.get(ISysUserService.class.getName());
	}

	protected ISysResourceService sysResourceService() {
		return (ISysResourceService) beanMap.get(ISysResourceService.class.getName());
	}

	protected ISysRoleService sysRoleService() {
		return (ISysRoleService) beanMap.get(ISysRoleService.class.getName());
	}

	protected ISysRoleResourceService sysRoleResourceService() {
		return (ISysRoleResourceService) beanMap.get(ISysRoleResourceService.class.getName());
	}

	protected ISysUserRoleService sysUserRoleService() {
		return (ISysUserRoleService) beanMap.get(ISysUserRoleService.class.getName());
	}

	protected ISysUserEnterpriseService sysUserEnterpriseService() {
		return (ISysUserEnterpriseService) beanMap.get(ISysUserEnterpriseService.class.getName());
	}

	protected SysApi3rdpartyService sysApi3rdpartyService() {
		return (SysApi3rdpartyService) beanMap.get(SysApi3rdpartyService.class.getName());
	}

}
