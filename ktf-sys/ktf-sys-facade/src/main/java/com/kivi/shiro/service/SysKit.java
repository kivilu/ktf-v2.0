package com.kivi.shiro.service;

import java.util.List;
import java.util.Set;

import com.kivi.cif.auth.CifAuthentication;
import com.kivi.cif.service.CifCustomerAuthsService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.component.SpringContextHolder;
import com.kivi.framework.vo.UserVo;
import com.kivi.sys.permission.service.SysResourceService;
import com.kivi.sys.permission.service.SysUserOrgService;
import com.kivi.sys.permission.service.SysUserService;
import com.kivi.sys.sys.service.SysDicService;
import com.kivi.sys.sys.service.SysLogService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public class SysKit {

    private SysUserService userService;
    private SysResourceService resourceService;
    private SysUserOrgService userOrgService;
    private CifCustomerAuthsService cifAuthService;
    private SysDicService sysDicService;
    private SysLogService logService;
    private CifAuthentication cifAuthentication;

    public UserVo getUserByLoginName(String loginName) {
        return userService.getUserVo(loginName);
    }

    public UserVo getUserById(Long userId) {
        return userService.getUserVo(userId);
    }

    @KtfTrace("获取角色权限")
    public Set<String> getPermissions(List<Long> roleIds) {
        return resourceService.getPermissions(roleIds);
    }

    public CifCustomerAuthsService cifAuthService() {
        return this.cifAuthService;
    }

    public static SysKit me() {
        return SpringContextHolder.getBeanNoAssert(SysKit.class);
    }
}
