package com.kivi.dashboard.permission.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.permission.entity.SysRoleResource;
import com.kivi.dashboard.permission.entity.SysUserRole;
import com.kivi.dashboard.permission.mapper.SysUserRoleMapper;
import com.kivi.dashboard.permission.service.SysUserRoleService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.cache.annotation.KtfCacheEvict;
import com.kivi.framework.cache.constant.KtfCache;
import com.vip.vjtools.vjkit.collection.ListUtil;

/**
 * <p>
 * 用户角色 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Primary
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @KtfCacheEvict(cacheNames = {KtfCache.SysUser})
    @Override
    public Boolean deleteBatchByRoleIds(Long[] roleIds) {
        if (roleIds == null)
            return false;

        QueryWrapper<SysUserRole> query = Wrappers.<SysUserRole>query();
        query.in(SysUserRole.DB_ROLE_ID, Arrays.asList(roleIds));
        return super.remove(query);

    }

    @KtfCacheEvict(cacheNames = {KtfCache.SysUser})
    @Override
    public Boolean deleteBatchByUserIds(Long[] userIds) {
        if (userIds == null)
            return false;
        QueryWrapper<SysUserRole> query = Wrappers.<SysUserRole>query();
        query.in(SysUserRole.DB_USER_ID, ListUtil.newArrayList(userIds));

        return super.remove(query);
    }

    @Override
    public List<Long> selectRoleIdListByUserId(Long userId) {
        QueryWrapper<SysUserRole> query =
            Wrappers.<SysUserRole>query().select(SysRoleResource.DB_ROLE_ID).eq(SysUserRole.DB_USER_ID, userId);

        List<SysUserRole> list = super.list(query);
        if (ListUtil.isEmpty(list))
            return null;

        return list.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
    }

    @KtfCacheEvict(cacheNames = {KtfCache.SysUser})
    @KtfTrace("修改或更新用户角色")
    @Override
    public void saveOrUpdateUserRole(Long userId, List<Long> roleIdList) {
        // 先删除用户与角色关系
        Map<String, Object> params = new HashMap<>();
        params.put(SysUserRole.DB_USER_ID, userId);
        this.removeByMap(params);
        if (roleIdList == null || roleIdList.size() == 0) {
            return;
        }
        // 保存用户与角色关系
        List<SysUserRole> list = roleIdList.stream().map(roleId -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            return sysUserRole;
        }).collect(Collectors.toList());

        this.saveBatch(list);
    }

    @KtfCacheEvict(cacheNames = {KtfCache.SysUser, KtfCache.SysResource})
    @KtfTrace("根据角色ID修改")
    @Override
    public Boolean updateByRoleId(List<Long> userIds, Long newRoleId) {
        LambdaUpdateWrapper<SysUserRole> updateWrapper = Wrappers.<SysUserRole>lambdaUpdate();
        updateWrapper.in(SysUserRole::getUserId, userIds);

        SysUserRole entity = new SysUserRole();
        entity.setRoleId(newRoleId);
        return super.update(entity, updateWrapper);
    }

}
