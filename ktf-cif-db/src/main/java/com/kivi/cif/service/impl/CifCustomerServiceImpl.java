package com.kivi.cif.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.cif.dto.CifCustomerDTO;
import com.kivi.cif.entity.CifCustomer;
import com.kivi.cif.mapper.CifCustomerMapper;
import com.kivi.cif.service.CifCustomerService;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.cache.constant.KtfCache;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.vo.page.PageInfoVO;
import com.vip.vjtools.vjkit.collection.MapUtil;

/**
 * <p>
 * 客户信息 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-10-28
 */
@Primary
@CacheConfig(cacheNames = KtfCache.CifCustomer)
@Service
@Transactional(rollbackFor = Exception.class)
public class CifCustomerServiceImpl extends ServiceImpl<CifCustomerMapper, CifCustomer> implements CifCustomerService {

	/**
	 * 根据ID查询客户信息
	 */
	@Cacheable(key = "caches[0].name+'DTO_'+#id", unless = "#result == null")
	@KtfTrace("根据ID查询客户信息")
	@Override
	public CifCustomerDTO getDTOById(Long id) {
		CifCustomer entity = super.getById(id);
		if (entity == null)
			return null;
		CifCustomerDTO dto = BeanConverter.convert(CifCustomerDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	@Cacheable(key = "caches[0].name+'_'+#id", unless = "#result == null")
	@Override
	public CifCustomer getById(Serializable id) {
		return super.getById(id);
	}

	/**
	 * 新增客户信息
	 */
	@KtfTrace("新增客户信息")
	@Override
	public Boolean save(CifCustomerDTO cifCustomerDTO) {
		CifCustomer entity = BeanConverter.convert(CifCustomer.class, cifCustomerDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@CacheEvict()
	@KtfTrace("修改客户信息")
	@Override
	public Boolean updateById(CifCustomerDTO cifCustomerDTO) {
		CifCustomer entity = BeanConverter.convert(CifCustomer.class, cifCustomerDTO);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表客户信息")
	@Override
	public List<CifCustomerDTO> list(CifCustomerDTO cifCustomerDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(cifCustomerDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表客户信息")
	@Override
	public List<CifCustomerDTO> list(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<CifCustomer>	query	= Wrappers.<CifCustomer>query().select(columns).allEq(true, params, false);
		List<CifCustomer>			list	= super.list(query);
		return BeanConverter.convert(CifCustomerDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询客户信息")
	@Override
	public List<CifCustomerDTO> listLike(CifCustomerDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<CifCustomerDTO> listLike(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<CifCustomer> query = Wrappers.<CifCustomer>query().select(columns);
		if (MapUtil.isNotEmpty(params)) {
			params.entrySet().stream().forEach(entry -> {
				if (ObjectKit.isNotEmpty(entry.getValue())) {
					if (NumberKit.isNumberic(entry.getValue()))
						query.eq(entry.getKey(), entry.getValue());
					else
						query.like(entry.getKey(), entry.getValue());
				}
			});
		}

		List<CifCustomer> list = super.list(query);
		return BeanConverter.convert(CifCustomerDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询客户信息")
	public PageInfoVO<CifCustomerDTO> page(Map<String, Object> params) {
		PageParams<CifCustomerDTO>	pageParams	= new PageParams<>(params);
		Page<CifCustomer>			page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<CifCustomer>	query		= Wrappers.<CifCustomer>query();
		if (MapUtil.isNotEmpty(pageParams.getRequestMap())) {
			pageParams.getRequestMap().entrySet().stream().forEach(entry -> {
				if (ObjectKit.isNotEmpty(entry.getValue())) {
					if (NumberKit.isNumberic(entry.getValue()))
						query.eq(entry.getKey(), entry.getValue());
					else
						query.like(entry.getKey(), entry.getValue());
				}
			});
		}

		IPage<CifCustomer>			iPage	= super.page(page, query);

		PageInfoVO<CifCustomerDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(CifCustomerDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

	@Cacheable(key = "caches[0].name+'_'+#regPhoneNumber", unless = "#result == null")
	@Override
	public CifCustomer getByPhoneNumber(String regPhoneNumber) {
		LambdaQueryWrapper<CifCustomer> queryWrapper = Wrappers.<CifCustomer>lambdaQuery();
		queryWrapper.eq(CifCustomer::getRegPhoneNumber, regPhoneNumber);

		return super.getOne(queryWrapper);
	}

}
