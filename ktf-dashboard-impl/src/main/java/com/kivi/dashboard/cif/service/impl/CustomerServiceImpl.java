package com.kivi.dashboard.cif.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.cif.dto.CustomerDTO;
import com.kivi.dashboard.cif.entity.Customer;
import com.kivi.dashboard.cif.mapper.CustomerMapper;
import com.kivi.dashboard.cif.service.ICustomerService;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 客户信息 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

	/**
	 * 根据ID查询客户信息
	 */
	@KtfTrace("根据ID查询客户信息")
	@Override
	public CustomerDTO getDTOById(Long id) {
		Customer	entity	= super.getById(id);
		CustomerDTO	dto		= BeanConverter.convert(CustomerDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增客户信息
	 */
	@KtfTrace("新增客户信息")
	@Override
	public Boolean save(CustomerDTO customerDTO) {
		Customer entity = BeanConverter.convert(Customer.class, customerDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改客户信息")
	@Override
	public Boolean updateById(CustomerDTO customerDTO) {
		Customer entity = BeanConverter.convert(Customer.class, customerDTO);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表客户信息")
	@Override
	public List<CustomerDTO> list(CustomerDTO customerDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(customerDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表客户信息")
	@Override
	public List<CustomerDTO> list(Map<String, Object> params, String... columns) {
		QueryWrapper<Customer>	query	= Wrappers.<Customer>query().select(columns).allEq(true, params, false);
		List<Customer>			list	= super.list(query);
		return BeanConverter.convert(CustomerDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询客户信息")
	@Override
	public List<CustomerDTO> listLike(CustomerDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<CustomerDTO> listLike(Map<String, Object> params, String... columns) {
		QueryWrapper<Customer> query = Wrappers.<Customer>query().select(columns);
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		List<Customer> list = super.list(query);
		return BeanConverter.convert(CustomerDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@KtfTrace("分页查询客户信息")
	public PageInfoVO<CustomerDTO> page(Map<String, Object> params) {
		PageParams<CustomerDTO>	pageParams	= new PageParams<>(params);
		Page<Customer>			page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<Customer>	query		= Wrappers.<Customer>query();
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		IPage<Customer>			iPage	= super.page(page, query);

		PageInfoVO<CustomerDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(CustomerDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

}