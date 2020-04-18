package com.kivi.dashboard.cif.service;

import com.kivi.dashboard.cif.entity.Customer;
import com.kivi.dashboard.cif.dto.CustomerDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 客户信息 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface ICustomerService extends IService<Customer> {
 
    /**
     * 根据ID查询DTO
     */
 	CustomerDTO getDTOById(Long id);   
 	
 	/**
 	 * 新增
 	 */
 	Boolean save(CustomerDTO customerDTO);
 	
 	/**
 	 * 修改
 	 */
 	Boolean updateById(CustomerDTO customerDTO);
 	
 	/**
 	 * 查询列表
 	 */
 	List<CustomerDTO> list(CustomerDTO customerDTO);
 	
 	/**
	 * 指定列查询列表
	 */
	List<CustomerDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<CustomerDTO> listLike(CustomerDTO applicationDTO);

	/**
	 * 指定列模糊查询
	 */
	List<CustomerDTO> listLike(Map<String, Object> params, String... columns);
 	
 	/**
 	 * 分页查询
 	 */
 	PageInfoVO<CustomerDTO> page(Map<String, Object> params);
}
