package com.kivi.cif.dubbo.service.impl;

import java.io.Serializable;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.cif.dto.CifCustomerAuthsDTO;
import com.kivi.cif.entity.CifCustomerAuths;
import com.kivi.cif.mapper.CifCustomerAuthsMapper;
import com.kivi.cif.properties.CifProperties;
import com.kivi.cif.service.CifCustomerAuthsService;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.form.LoginForm;
import com.kivi.framework.vo.UserVo;

@DubboService(version = CifProperties.DUBBO_VERSION)
public class DubboCifCustomerAuthsServiceImpl extends ServiceImpl<CifCustomerAuthsMapper, CifCustomerAuths>
    implements CifCustomerAuthsService {

    @Autowired
    private CifCustomerAuthsService cifCustomerAuthsService;

    @Override
    public CifCustomerAuths getCifCustomerAuths(CifCustomerAuthsDTO cifAuthDTO) {
        return cifCustomerAuthsService.getCifCustomerAuths(cifAuthDTO);
    }

    @Override
    public CifCustomerAuths getCifCustomerAuths(Long applicationId, String identityType, String identifier,
        String userType) {
        return cifCustomerAuthsService.getCifCustomerAuths(applicationId, identityType, identifier, userType);
    }

    @Override
    public CifCustomerAuthsDTO getDto(Long id) {
        return cifCustomerAuthsService.getDto(id);
    }

    @Override
    public CifCustomerAuths getById(Serializable id) {
        return cifCustomerAuthsService.getById(id);
    }

    @Override
    public boolean save(CifCustomerAuths cifCustomerAuths) {
        return cifCustomerAuthsService.save(cifCustomerAuths);
    }

    @Override
    public Boolean updateById(CifCustomerAuthsDTO cifCustomerAuthsDTO) {
        return cifCustomerAuthsService.updateById(cifCustomerAuthsDTO);
    }

    @Override
    public Boolean updateByEntity(CifCustomerAuths condEntity, CifCustomerAuths updaeEntity) {
        return cifCustomerAuthsService.updateByEntity(condEntity, updaeEntity);
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
