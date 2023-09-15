package com.kivi.cif.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.cif.dto.CifCustomerAuthsDTO;
import com.kivi.cif.entity.CifCustomerAuths;

/**
 * <p>
 * 客户验证 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-10-28
 */
public interface ICifCustomerAuthsService extends IService<CifCustomerAuths>, CifCustomerAuthsService {

    /**
     * 根据唯一键查询记录
     * 
     * @see CifCustomerAuthsDao.getCifCustomerAuths
     * @param cifAuthDTO
     * @return
     */
    CifCustomerAuths getCifCustomerAuths(final CifCustomerAuthsDTO cifAuthDTO);

    /**
     * 根据唯一键查询记录
     * 
     * @param bizCode
     * @param identityType
     * @param identifier
     * @param userType
     * @return
     */
    CifCustomerAuths getCifCustomerAuths(Long appid, String identityType, String identifier, String userType);

    /**
     * 更新
     * 
     * @param condEntity
     * @param updaeEntity
     * @return
     */
    Boolean updateByEntity(CifCustomerAuths condEntity, CifCustomerAuths updaeEntity);

}
