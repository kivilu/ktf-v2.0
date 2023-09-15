package com.kivi.sys.permission.service;

import java.util.List;
import java.util.Map;

import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.permission.dto.SysRoleDTO;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface SysRoleService {

    /**
     * 根据ID查询DTO
     */
    SysRoleDTO getDto(Long id);

    /**
     * 新增
     */
    Boolean save(SysRoleDTO dto);

    /**
     * 修改
     */
    Boolean updateById(SysRoleDTO dto);

    /**
     * 分页查询
     */
    PageInfoVO<SysRoleDTO> page(Map<String, Object> params);

    /**
     * 批量删除
     * 
     * @param roleIds
     */
    void deleteBatch(Long[] roleIds);

    /**
     * 指定列模糊查询
     */
    List<SysRoleDTO> listLike(Map<String, Object> params, String... columns);

    Boolean removeById(Long id);

    Boolean removeByIds(List<Long> ids);

}
