package com.kivi.cif.dubbo.service.impl;

import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.cif.dto.CifCustomerDTO;
import com.kivi.cif.entity.CifCustomer;
import com.kivi.cif.mapper.CifCustomerMapper;
import com.kivi.cif.properties.CifProperties;
import com.kivi.cif.service.CifCustomerService;
import com.kivi.framework.vo.page.PageInfoVO;

@DubboService(version = CifProperties.DUBBO_VERSION)
public class CifCustomerDubboServiceImpl extends ServiceImpl<CifCustomerMapper, CifCustomer>
		implements CifCustomerService {
	@Autowired
	private CifCustomerService cifCustomerService;

	@Override
	public CifCustomer getByPhoneNumber(String regPhoneNumber) {
		return cifCustomerService.getByPhoneNumber(regPhoneNumber);
	}

	@Override
	public CifCustomerDTO getDTOById(Long id) {
		return cifCustomerService.getDTOById(id);
	}

	@Override
	public Boolean save(CifCustomerDTO cifCustomerDTO) {
		return cifCustomerService.save(cifCustomerDTO);
	}

	@Override
	public Boolean updateById(CifCustomerDTO cifCustomerDTO) {
		return cifCustomerService.updateById(cifCustomerDTO);
	}

	@Override
	public PageInfoVO<CifCustomerDTO> page(Map<String, Object> params) {
		return cifCustomerService.page(params);
	}

	@Override
	public CifCustomer getByCustomerId(String customerId) {
		return cifCustomerService.getByCustomerId(customerId);
	}

}
