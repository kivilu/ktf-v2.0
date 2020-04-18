package com.kivi.cif.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.cif.dto.CifCustomerDTO;
import com.kivi.cif.entity.CifCustomer;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 客户信息 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-10-28
 */
public interface CifCustomerService extends IService<CifCustomer> {

	/**
	 * 根据注册手机号查询客户信息
	 * 
	 * @param regPhoneNumber
	 * @return
	 */
	CifCustomer getByPhoneNumber(String regPhoneNumber);

	/**
	 * 根据ID查询DTO
	 */
	CifCustomerDTO getDTOById(Long id);

	/**
	 * 新增
	 */
	Boolean save(CifCustomerDTO cifCustomerDTO);

	/**
	 * 修改
	 */
	Boolean updateById(CifCustomerDTO cifCustomerDTO);

	/**
	 * 查询列表
	 */
	List<CifCustomerDTO> list(CifCustomerDTO cifCustomerDTO);

	/**
	 * 指定列查询列表
	 */
	List<CifCustomerDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<CifCustomerDTO> listLike(CifCustomerDTO cifCustomerDTO);

	/**
	 * 指定列模糊查询
	 */
	List<CifCustomerDTO> listLike(Map<String, Object> params, String... columns);

	/**
	 * 分页查询
	 */
	PageInfoVO<CifCustomerDTO> page(Map<String, Object> params);
}
