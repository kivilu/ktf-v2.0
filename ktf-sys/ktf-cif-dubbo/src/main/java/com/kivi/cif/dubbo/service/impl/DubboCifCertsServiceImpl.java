package com.kivi.cif.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.kivi.cif.dto.CifCertsDTO;
import com.kivi.cif.properties.CifProperties;
import com.kivi.cif.service.CifCertsService;
import com.kivi.cif.service.ICifCertsService;
import com.kivi.framework.vo.page.PageInfoVO;

@DubboService(version = CifProperties.DUBBO_VERSION)
public class DubboCifCertsServiceImpl implements CifCertsService {

    @Autowired
    private ICifCertsService cifCertsService;

    @Override
    public CifCertsDTO getDTOById(Long id) {
        return cifCertsService.getDTOById(id);
    }

    @Override
    public Boolean save(CifCertsDTO cifCertsDTO) {
        return cifCertsService.save(cifCertsDTO);
    }

    @Override
    public Boolean updateById(CifCertsDTO cifCertsDTO) {
        return cifCertsService.updateById(cifCertsDTO);
    }

    @Override
    public List<CifCertsDTO> list(CifCertsDTO cifCertsDTO) {
        return cifCertsService.list(cifCertsDTO);
    }

    @Override
    public List<CifCertsDTO> list(Map<String, Object> params, String... columns) {
        return cifCertsService.list(params, columns);
    }

    @Override
    public List<CifCertsDTO> listLike(CifCertsDTO cifCertsDTO) {
        return cifCertsService.listLike(cifCertsDTO);
    }

    @Override
    public List<CifCertsDTO> listLike(Map<String, Object> params, String... columns) {
        return cifCertsService.listLike(params, columns);
    }

    @Override
    public PageInfoVO<CifCertsDTO> page(Map<String, Object> params) {
        return cifCertsService.page(params);
    }

    @Override
    public Boolean removeByIdentifier(String identifier) {
        return cifCertsService.removeByIdentifier(identifier);
    }

    @Override
    public boolean saveBatch(List<CifCertsDTO> list) {
        return cifCertsService.saveBatch(list);
    }

    @Override
    public Boolean removeById(Long id) {
        return cifCertsService.removeById(id);
    }

    @Override
    public Boolean removeByIds(List<Long> ids) {
        return cifCertsService.removeByIds(ids);
    }

}
