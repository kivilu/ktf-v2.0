package com.kivi.sys.base;

import java.util.concurrent.ConcurrentHashMap;

import com.kivi.cif.service.CifCertsService;
import com.kivi.cif.service.CifCustomerAuthsService;
import com.kivi.cif.service.CustomerService;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.crypto.sm3.SM3Kit;
import com.kivi.framework.crypto.sm4.SM4Kit;
import com.kivi.framework.web.controller.BaseController;
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

public abstract class DashboardController extends BaseController {

    private static ConcurrentHashMap<String, Object> beanMap = new ConcurrentHashMap<>();

    public static void registBean(String className, Object bean) {
        beanMap.put(className, bean);
    }

    protected CustomerService customerService() {
        return (CustomerService)beanMap.get(CustomerService.class.getName());
    }

    protected CifCertsService cifCertsService() {
        return (CifCertsService)beanMap.get(CifCertsService.class.getName());
    }

    protected CifCustomerAuthsService customerAuthsService() {
        return (CifCustomerAuthsService)beanMap.get(CifCustomerAuthsService.class.getName());
    }

    protected OrgCorpService orgCorpService() {
        return (OrgCorpService)beanMap.get(OrgCorpService.class.getName());
    }

    protected OrgDeptService orgDeptService() {
        return (OrgDeptService)beanMap.get(OrgDeptService.class.getName());
    }

    protected OrgTitleService orgTitleService() {
        return (OrgTitleService)beanMap.get(OrgTitleService.class.getName());
    }

    protected SysFileService sysFileService() {
        return (SysFileService)beanMap.get(SysFileService.class.getName());
    }

    protected SysUserTokenService sysUserTokenService() {
        return (SysUserTokenService)beanMap.get(SysUserTokenService.class.getName());
    }

    protected SysApplicationService sysApplicationService() {
        return (SysApplicationService)beanMap.get(SysApplicationService.class.getName());
    }

    protected SysDicService sysDicService() {
        return (SysDicService)beanMap.get(SysDicService.class.getName());
    }

    protected SysIndustryService sysIndustryService() {
        return (SysIndustryService)beanMap.get(SysIndustryService.class.getName());
    }

    protected SysLogService sysLogService() {
        return (SysLogService)beanMap.get(SysLogService.class.getName());
    }

    protected SysRegionService sysRegionService() {
        return (SysRegionService)beanMap.get(SysRegionService.class.getName());
    }

    protected SysUserService sysUserService() {
        return (SysUserService)beanMap.get(SysUserService.class.getName());
    }

    protected SysResourceService sysResourceService() {
        return (SysResourceService)beanMap.get(SysResourceService.class.getName());
    }

    protected SysRoleService sysRoleService() {
        return (SysRoleService)beanMap.get(SysRoleService.class.getName());
    }

    protected SysRoleResourceService sysRoleResourceService() {
        return (SysRoleResourceService)beanMap.get(SysRoleResourceService.class.getName());
    }

    protected SysUserRoleService sysUserRoleService() {
        return (SysUserRoleService)beanMap.get(SysUserRoleService.class.getName());
    }

    protected SysUserOrgService sysUserEnterpriseService() {
        return (SysUserOrgService)beanMap.get(SysUserOrgService.class.getName());
    }

    protected SysApi3rdpartyService sysApi3rdpartyService() {
        return (SysApi3rdpartyService)beanMap.get(SysApi3rdpartyService.class.getName());
    }

    protected String decryptData(String encData, String jwtToken) {
        byte[] hash = SM3Kit.sm3(jwtToken);
        byte[] key = new byte[16];
        System.arraycopy(hash, 16, key, 0, 16);

        byte[] data = SM4Kit.decryptHex(encData, key);

        return new String(data, KtfConstant.DEFAULT_CHARSET);
    }

}
