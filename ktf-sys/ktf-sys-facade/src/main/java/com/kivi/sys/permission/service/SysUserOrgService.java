package com.kivi.sys.permission.service;

import java.util.List;

import com.kivi.sys.permission.dto.SysUserOrgDTO;

/**
 * <p>
 * 监管用户与企业关联 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface SysUserOrgService {

    /**
     * 根据ID查找所属企业ID
     * 
     * @param userId
     * @return
     */
    List<Long> selectOrgIdByUserId(Long userId);

    /**
     * 保存或修改用户所监管的企业关系
     *
     * @param userId
     * @param enterpriseIdList
     */
    void saveOrUpdateUserOrg(Long userId, List<Long> orgIds);

    /**
     * 根据用户批量删除
     *
     * @param userIds
     */
    Boolean deleteBatchByUserIds(Long[] userIds);

    /**
     * 根据企业批量删除
     *
     * @param enterpriseIds
     */
    Boolean deleteBatchByOrgIds(Long[] orgIds);

    /**
     * 根据ID查询DTO
     */
    SysUserOrgDTO getDto(Long id);

    /**
     * 新增
     */
    // Boolean save(SysUserOrgDTO dto);

    /**
     * 修改
     */
    // Boolean updateById(SysUserOrgDTO dto);

    /**
     * 查询列表
     */
    // List<SysUserOrgDTO> list(SysUserOrgDTO dto);

    /**
     * 指定列查询列表
     */
    // List<SysUserOrgDTO> list(Map<String, Object> params, String... columns);

    /**
     * 模糊查询
     */
    // List<SysUserOrgDTO> listLike(SysUserOrgDTO applicationDTO);

    /**
     * 指定列模糊查询
     */
    // List<SysUserOrgDTO> listLike(Map<String, Object> params, String... columns);

    /**
     * 分页查询
     */
    // PageInfoVO<SysUserOrgDTO> page(Map<String, Object> params);

}
