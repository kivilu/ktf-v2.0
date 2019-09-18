package com.kivi.dashboard.cif.service;

import com.kivi.dashboard.cif.entity.CustomerAuths;
import com.kivi.dashboard.cif.dto.CustomerAuthsDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 客户认证 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface ICustomerAuthsService extends IService<CustomerAuths> {
 
    /**
     * 根据ID查询DTO
     */
 	CustomerAuthsDTO getDTOById(Long id);   
 	
 	/**
 	 * 新增
 	 */
 	Boolean save(CustomerAuthsDTO customerAuthsDTO);
 	
 	/**
 	 * 修改
 	 */
 	Boolean updateById(CustomerAuthsDTO customerAuthsDTO);
 	
 	/**
 	 * 查询列表
 	 */
 	List<CustomerAuthsDTO> list(CustomerAuthsDTO customerAuthsDTO);
 	
 	/**
	 * 指定列查询列表
	 */
	List<CustomerAuthsDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<CustomerAuthsDTO> listLike(CustomerAuthsDTO applicationDTO);

	/**
	 * 指定列模糊查询
	 */
	List<CustomerAuthsDTO> listLike(Map<String, Object> params, String... columns);
 	
 	/**
 	 * 分页查询
 	 */
 	PageInfoVO<CustomerAuthsDTO> page(Map<String, Object> params);
}
