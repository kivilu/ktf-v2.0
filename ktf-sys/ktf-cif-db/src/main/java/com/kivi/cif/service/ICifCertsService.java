package com.kivi.cif.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.cif.dto.CifCertsDTO;
import com.kivi.cif.entity.CifCerts;

/**
 * <p>
 * 客户证书 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-10-28
 */
public interface ICifCertsService extends IService<CifCerts>, CifCertsService {

    /**
     * 根据标识和类型获取证书
     * 
     * @param identifier
     * @param type
     * @return
     */
    CifCerts getCifCert(String identifier, String type);

    /**
     * 删除
     * 
     * @param identifier
     * @return
     */
    Boolean removeByIdentifier(String identifier);

    /**
     * 根据ID查询DTO
     */
    CifCertsDTO getDTOById(Long id);

}
