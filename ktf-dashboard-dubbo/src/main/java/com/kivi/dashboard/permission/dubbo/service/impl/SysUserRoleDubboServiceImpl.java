package com.kivi.dashboard.permission.dubbo.service.impl;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.permission.entity.SysUserRole;
import com.kivi.dashboard.permission.mapper.SysUserRoleMapper;
import com.kivi.dashboard.permission.service.SysUserRoleService;
import com.kivi.framework.properties.KtfDashboardProperties;

/**
 * <p>
 * 用户角色 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@DubboService(version = KtfDashboardProperties.DUBBO_VERSION)
public class SysUserRoleDubboServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>
    implements SysUserRoleService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public Boolean deleteBatchByRoleIds(Long[] roleIds) {
        return sysUserRoleService.deleteBatchByRoleIds(roleIds);

    }

    @Override
    public Boolean deleteBatchByUserIds(Long[] userIds) {
        return sysUserRoleService.deleteBatchByUserIds(userIds);
    }

    @Override
    public List<Long> selectRoleIdListByUserId(Long userId) {
        return sysUserRoleService.selectRoleIdListByUserId(userId);
    }

    @Override
    public void saveOrUpdateUserRole(Long userId, List<Long> roleIdList) {
        sysUserRoleService.saveOrUpdateUserRole(userId, roleIdList);
    }

    @Override
    public Boolean updateByRoleId(List<Long> userIds, Long newRoleId) {
        return sysUserRoleService.updateByRoleId(userIds, newRoleId);
    }

}
