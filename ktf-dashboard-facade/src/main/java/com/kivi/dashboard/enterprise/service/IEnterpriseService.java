package com.kivi.dashboard.enterprise.service;

import com.kivi.dashboard.enterprise.entity.Enterprise;
import com.kivi.dashboard.enterprise.dto.EnterpriseDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 企业信息 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface IEnterpriseService extends IService<Enterprise> {
 
    /**
     * 根据ID查询DTO
     */
 	EnterpriseDTO getDTOById(Long id);   
 	
 	/**
 	 * 新增
 	 */
 	Boolean save(EnterpriseDTO enterpriseDTO);
 	
 	/**
 	 * 修改
 	 */
 	Boolean updateById(EnterpriseDTO enterpriseDTO);
 	
 	/**
 	 * 查询列表
 	 */
 	List<EnterpriseDTO> list(EnterpriseDTO enterpriseDTO);
 	
 	/**
	 * 指定列查询列表
	 */
	List<EnterpriseDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<EnterpriseDTO> listLike(EnterpriseDTO applicationDTO);

	/**
	 * 指定列模糊查询
	 */
	List<EnterpriseDTO> listLike(Map<String, Object> params, String... columns);
 	
 	/**
 	 * 分页查询
 	 */
 	PageInfoVO<EnterpriseDTO> page(Map<String, Object> params);
}
