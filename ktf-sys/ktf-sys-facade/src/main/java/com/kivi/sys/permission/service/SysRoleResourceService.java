package com.kivi.sys.permission.service;

import java.util.List;
import java.util.Map;

import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.permission.dto.SysRoleResourceDTO;

/**
 * <p>
 * 角色资源 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface SysRoleResourceService {

    /**
     * 根据ID查询DTO
     */
    SysRoleResourceDTO getDTOById(Long id);

    /**
     * 新增
     */
    Boolean save(SysRoleResourceDTO sysRoleResourceDTO);

    /**
     * 修改
     */
    Boolean updateById(SysRoleResourceDTO sysRoleResourceDTO);

    /**
     * 查询列表
     */
    List<SysRoleResourceDTO> list(SysRoleResourceDTO sysRoleResourceDTO);

    /**
     * 指定列查询列表
     */
    List<SysRoleResourceDTO> list(Map<String, Object> params, String... columns);

    /**
     * 模糊查询
     */
    List<SysRoleResourceDTO> listLike(SysRoleResourceDTO applicationDTO);

    /**
     * 指定列模糊查询
     */
    List<SysRoleResourceDTO> listLike(Map<String, Object> params, String... columns);

    /**
     * 分页查询
     */
    PageInfoVO<SysRoleResourceDTO> page(Map<String, Object> params);

    /**
     * 根据角色查找菜单ID集合
     *
     * @param roleId
     * @return
     */
    List<Long> selectResourceIdListByRoleId(Long roleId);

    /**
     * 查询选中node
     * 
     * @param roleId
     * @return
     */
    List<SysRoleResourceDTO> selectResourceNodeListByRoleId(Long roleId);

    /**
     * 更新角色与菜单关系
     * 
     * @param roleId
     * @param resourceIdList
     */
    Boolean saveOrUpdateRoleResource(Long roleId, List<Long> resourceIdList);

    /**
     * 根据角色批量删除
     *
     * @param roleIds
     */
    int deleteBatchByRoleIds(Long[] roleIds);

}
