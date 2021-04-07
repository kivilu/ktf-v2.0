package com.kivi.dashboard.base;

import java.util.concurrent.ConcurrentHashMap;

import com.kivi.cif.service.CifCertsService;
import com.kivi.cif.service.CifCustomerAuthsService;
import com.kivi.cif.service.CifCustomerService;
import com.kivi.dashboard.org.service.OrgCorpService;
import com.kivi.dashboard.org.service.OrgDeptService;
import com.kivi.dashboard.org.service.OrgTitleService;
import com.kivi.dashboard.permission.service.SysResourceService;
import com.kivi.dashboard.permission.service.SysRoleResourceService;
import com.kivi.dashboard.permission.service.SysRoleService;
import com.kivi.dashboard.permission.service.SysUserOrgService;
import com.kivi.dashboard.permission.service.SysUserRoleService;
import com.kivi.dashboard.permission.service.SysUserService;
import com.kivi.dashboard.permission.service.SysUserTokenService;
import com.kivi.dashboard.sys.service.ISysApplicationService;
import com.kivi.dashboard.sys.service.ISysFileService;
import com.kivi.dashboard.sys.service.ISysIndustryService;
import com.kivi.dashboard.sys.service.ISysLogService;
import com.kivi.dashboard.sys.service.ISysRegionService;
import com.kivi.dashboard.sys.service.SysApi3rdpartyService;
import com.kivi.dashboard.sys.service.SysDicService;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.crypto.sm3.SM3Kit;
import com.kivi.framework.crypto.sm4.SM4Kit;
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

	protected OrgCorpService orgCorpService() {
		return (OrgCorpService) beanMap.get(OrgCorpService.class.getName());
	}

	protected OrgDeptService orgDeptService() {
		return (OrgDeptService) beanMap.get(OrgDeptService.class.getName());
	}

	protected OrgTitleService orgTitleService() {
		return (OrgTitleService) beanMap.get(OrgTitleService.class.getName());
	}

	protected ISysFileService sysFileService() {
		return (ISysFileService) beanMap.get(ISysFileService.class.getName());
	}

	protected SysUserTokenService sysUserTokenService() {
		return (SysUserTokenService) beanMap.get(SysUserTokenService.class.getName());
	}

	protected ISysApplicationService sysApplicationService() {
		return (ISysApplicationService) beanMap.get(ISysApplicationService.class.getName());
	}

	protected SysDicService sysDicService() {
		return (SysDicService) beanMap.get(SysDicService.class.getName());
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

	protected SysUserService sysUserService() {
		return (SysUserService) beanMap.get(SysUserService.class.getName());
	}

	protected SysResourceService sysResourceService() {
		return (SysResourceService) beanMap.get(SysResourceService.class.getName());
	}

	protected SysRoleService sysRoleService() {
		return (SysRoleService) beanMap.get(SysRoleService.class.getName());
	}

	protected SysRoleResourceService sysRoleResourceService() {
		return (SysRoleResourceService) beanMap.get(SysRoleResourceService.class.getName());
	}

	protected SysUserRoleService sysUserRoleService() {
		return (SysUserRoleService) beanMap.get(SysUserRoleService.class.getName());
	}

	protected SysUserOrgService sysUserEnterpriseService() {
		return (SysUserOrgService) beanMap.get(SysUserOrgService.class.getName());
	}

	protected SysApi3rdpartyService sysApi3rdpartyService() {
		return (SysApi3rdpartyService) beanMap.get(SysApi3rdpartyService.class.getName());
	}

	protected String decryptData(String encData, String jwtToken) {
		byte[]	hash	= SM3Kit.sm3(jwtToken);
		byte[]	key		= new byte[16];
		System.arraycopy(hash, 16, key, 0, 16);

		byte[] data = SM4Kit.decryptHex(encData, key);

		return new String(data, KtfConstant.DEFAULT_CHARSET);
	}

}
