package com.kivi.sys.permission.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfSysProperties;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.permission.dto.SysRoleDTO;
import com.kivi.sys.permission.entity.SysRole;
import com.kivi.sys.permission.mapper.SysRoleMapper;
import com.kivi.sys.permission.service.SysRoleService;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@DubboService(version = KtfSysProperties.DUBBO_VERSION)
public class SysRoleDubboServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    SysRoleService sysRoleService;

    /**
     * 根据ID查询角色
     */
    @KtfTrace("根据ID查询角色")
    @Override
    public SysRoleDTO getDto(Long id) {
        return sysRoleService.getDto(id);
    }

    /**
     * 新增角色
     */
    @KtfTrace("新增角色")
    @Override
    public Boolean save(SysRoleDTO sysRoleDTO) {
        return sysRoleService.save(sysRoleDTO);
    }

    /**
     * 修改
     */
    @KtfTrace("修改角色")
    @Override
    public Boolean updateById(SysRoleDTO sysRoleDTO) {
        return sysRoleService.updateById(sysRoleDTO);
    }

    /**
     * 分页查询
     */
    @Override
    @KtfTrace("分页查询角色")
    public PageInfoVO<SysRoleDTO> page(Map<String, Object> params) {
        return sysRoleService.page(params);

    }

    /**
     * 批量删除
     * 
     * @param roleIds
     */
    @Override
    public void deleteBatch(Long[] roleIds) {
        sysRoleService.deleteBatch(roleIds);
    }

    @Override
    public List<SysRoleDTO> listLike(Map<String, Object> params, String... columns) {
        return sysRoleService.listLike(params, columns);
    }

    @Override
    public Boolean removeById(Long id) {
        return sysRoleService.removeById(id);
    }

    @Override
    public Boolean removeByIds(List<Long> ids) {
        return sysRoleService.removeByIds(ids);
    }

}
