package com.kivi.cif.dubbo.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.kivi.cif.dto.CifCustomerAuthsDTO;
import com.kivi.cif.properties.CifProperties;
import com.kivi.cif.service.CifCustomerAuthsService;
import com.kivi.cif.service.ICifCustomerAuthsService;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.form.LoginForm;
import com.kivi.framework.vo.UserVo;

@DubboService(version = CifProperties.DUBBO_VERSION)
public class DubboCifCustomerAuthsServiceImpl implements CifCustomerAuthsService {

    @Autowired
    private ICifCustomerAuthsService cifCustomerAuthsService;

    @Override
    public CifCustomerAuthsDTO getDto(Long id) {
        return cifCustomerAuthsService.getDto(id);
    }

    @Override
    public Boolean updateById(CifCustomerAuthsDTO cifCustomerAuthsDTO) {
        return cifCustomerAuthsService.updateById(cifCustomerAuthsDTO);
    }

    @Override
    public Long save(CifCustomerAuthsDTO dto) {
        return cifCustomerAuthsService.save(dto);
    }

    @Override
    public Integer auth(LoginForm form, UserVo userVo) {
        return cifCustomerAuthsService.auth(form, userVo);
    }

    @Override
    public Boolean updateCredential(UserVo userVo, String newPassword) throws KtfException {
        return cifCustomerAuthsService.updateCredential(userVo, newPassword);
    }

    @Override
    public Boolean resetCredential(UserVo userVo) throws KtfException {
        return cifCustomerAuthsService.resetCredential(userVo);
    }

}
