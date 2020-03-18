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
import com.kivi.dashboard.cif.dto.CustomerAuthsDTO;
import com.kivi.dashboard.cif.entity.CustomerAuths;
import com.kivi.dashboard.cif.mapper.CustomerAuthsMapper;
import com.kivi.dashboard.cif.service.ICustomerAuthsService;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 客户认证 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerAuthsServiceImpl extends ServiceImpl<CustomerAuthsMapper, CustomerAuths>
		implements ICustomerAuthsService {

	/**
	 * 根据ID查询客户认证
	 */
	@KtfTrace("根据ID查询客户认证")
	@Override
	public CustomerAuthsDTO getDTOById(Long id) {
		CustomerAuths		entity	= super.getById(id);
		CustomerAuthsDTO	dto		= BeanConverter.convert(CustomerAuthsDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增客户认证
	 */
	@KtfTrace("新增客户认证")
	@Override
	public Boolean save(CustomerAuthsDTO customerAuthsDTO) {
		CustomerAuths entity = BeanConverter.convert(CustomerAuths.class, customerAuthsDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改客户认证")
	@Override
	public Boolean updateById(CustomerAuthsDTO customerAuthsDTO) {
		CustomerAuths entity = BeanConverter.convert(CustomerAuths.class, customerAuthsDTO);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表客户认证")
	@Override
	public List<CustomerAuthsDTO> list(CustomerAuthsDTO customerAuthsDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(customerAuthsDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表客户认证")
	@Override
	public List<CustomerAuthsDTO> list(Map<String, Object> params, String... columns) {
		QueryWrapper<CustomerAuths>	query	= Wrappers.<CustomerAuths>query().select(columns).allEq(true, params,
				false);
		List<CustomerAuths>			list	= super.list(query);
		return BeanConverter.convert(CustomerAuthsDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询客户认证")
	@Override
	public List<CustomerAuthsDTO> listLike(CustomerAuthsDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<CustomerAuthsDTO> listLike(Map<String, Object> params, String... columns) {
		QueryWrapper<CustomerAuths> query = Wrappers.<CustomerAuths>query().select(columns);
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		List<CustomerAuths> list = super.list(query);
		return BeanConverter.convert(CustomerAuthsDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@KtfTrace("分页查询客户认证")
	public PageInfoVO<CustomerAuthsDTO> page(Map<String, Object> params) {
		PageParams<CustomerAuthsDTO>	pageParams	= new PageParams<>(params);
		Page<CustomerAuths>				page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<CustomerAuths>		query		= Wrappers.<CustomerAuths>query();
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		IPage<CustomerAuths>			iPage	= super.page(page, query);

		PageInfoVO<CustomerAuthsDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(CustomerAuthsDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

}