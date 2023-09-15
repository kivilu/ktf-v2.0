package com.kivi.sys.permission.service;

import java.util.Map;

import com.kivi.framework.vo.UserVo;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.enums.KmsUserType;
import com.kivi.sys.permission.dto.SysUserDTO;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface SysUserService {

    /**
     * 根据ID查询DTO
     */
    SysUserDTO getDto(Long id);

    /**
     * 根据用户名查找
     * 
     * @param loginName
     * @return
     */
    UserVo getUserVo(String loginName);

    /**
     * 根据ID查找
     * 
     * @param id
     * @return
     */
    UserVo getUserVo(Long id);

    long count(KmsUserType... types);

    /**
     * 分页查询
     */
    PageInfoVO<SysUserDTO> page(Map<String, Object> params);

    /**
     * 分页查询简单信息
     */
    PageInfoVO<SysUserDTO> pageSimple(Map<String, Object> params);

    /**
     * 新增
     */
    Long save(SysUserDTO dto);

    /**
     * 更新用户
     * 
     * @param userVo
     * @return
     */
    Boolean update(SysUserDTO dto);

    /**
     * 根据用户批量删除
     *
     * @param userIds
     */
    Boolean deleteBatch(Long[] userIds);

    /**
     * 检查用户名是否存在
     */
    Boolean isUserExist(String userName);

    Boolean updateById(SysUserDTO dto);

}
