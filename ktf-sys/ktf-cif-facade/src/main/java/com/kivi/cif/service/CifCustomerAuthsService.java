package com.kivi.cif.service;

import com.kivi.cif.dto.CifCustomerAuthsDTO;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.form.LoginForm;
import com.kivi.framework.vo.UserVo;

public interface CifCustomerAuthsService {

    /**
     * 验证用户的有效性
     * 
     * @param userVo
     * @return 验证方式
     */
    Integer auth(LoginForm form, UserVo userVo) throws KtfException;

    /**
     * 根据ID查询DTO
     */
    CifCustomerAuthsDTO getDto(Long id);

    /**
     * 修改
     */
    Long save(CifCustomerAuthsDTO dto);

    /**
     * 修改
     */
    Boolean updateById(CifCustomerAuthsDTO dto);

    /**
     * 修改用户密码
     * 
     * @param userVo 用户信息， id和password属性必须
     * @param newPassword 新密码，若未null，则修改为默认密码
     * @return
     */
    Boolean updateCredential(UserVo userVo, String newPassword) throws KtfException;

    /**
     * 重置用户密码
     * 
     * @param userVo 用户信息
     * @return
     */
    Boolean resetCredential(UserVo userVo) throws KtfException;

}
