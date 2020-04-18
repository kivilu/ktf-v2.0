package com.kivi.cif.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.cif.dto.CifCustomerAuthsDTO;
import com.kivi.cif.entity.CifCustomerAuths;
import com.kivi.cif.mapper.CifCustomerAuthsMapper;
import com.kivi.cif.service.CifCustomerAuthsService;
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
 * 客户验证 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-10-28
 */
@Primary
@CacheConfig(cacheNames = KtfCache.CifCustomerAuths)
@Service
@Transactional(rollbackFor = Exception.class)
public class CifCustomerAuthsServiceImpl extends ServiceImpl<CifCustomerAuthsMapper, CifCustomerAuths>
		implements CifCustomerAuthsService {

	@Cacheable(key = "caches[0].name+'_'+#bizCode+#identityType+#identifier+#userType", unless = "#result == null")
	@Override
	public CifCustomerAuths
			getCifCustomerAuths(String bizCode, String identityType, String identifier, String userType) {

		LambdaQueryWrapper<CifCustomerAuths> queryWrapper = Wrappers.<CifCustomerAuths>lambdaQuery();
		queryWrapper.eq(CifCustomerAuths::getIdentifier, identifier).eq(CifCustomerAuths::getIdentityType, identityType)
				.eq(CifCustomerAuths::getBizCode, bizCode).eq(CifCustomerAuths::getUserType, userType);

		return super.getOne(queryWrapper);
	}

	@Override
	@Cacheable(
			key = "caches[0].name+'_'+#cifAuthDTO.bizCode+#cifAuthDTO.identityType+#cifAuthDTO.identifier+#cifAuthDTO.userType",
			unless = "#result == null")
	public CifCustomerAuths getCifCustomerAuths(final CifCustomerAuthsDTO cifAuthDTO) {
		CifCustomerAuths entity = getCifCustomerAuths(cifAuthDTO.getBizCode(), cifAuthDTO.getIdentityType(),
				cifAuthDTO.getIdentifier(), cifAuthDTO.getUserType());

		return entity;
	}

	/**
	 * 根据ID查询客户验证
	 */
	@Cacheable(key = "caches[0].name+'DTO_'+#id", unless = "#result == null")
	@KtfTrace("根据ID查询客户验证")
	@Override
	public CifCustomerAuthsDTO getDTOById(Long id) {
		CifCustomerAuths entity = super.getById(id);
		if (entity == null)
			return null;
		CifCustomerAuthsDTO dto = BeanConverter.convert(CifCustomerAuthsDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	@Cacheable(key = "caches[0].name+'_'+#id", unless = "#result == null")
	@Override
	public CifCustomerAuths getById(Serializable id) {
		return super.getById(id);
	}

	/**
	 * 新增客户验证
	 */
	@KtfTrace("新增客户验证")
	@Override
	public Boolean save(CifCustomerAuthsDTO cifCustomerAuthsDTO) {
		CifCustomerAuths entity = BeanConverter.convert(CifCustomerAuths.class, cifCustomerAuthsDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改客户验证")
	@Caching(evict = { @CacheEvict(allEntries = true) })

	@Override
	public Boolean updateById(CifCustomerAuthsDTO cifCustomerAuthsDTO) {
		CifCustomerAuths entity = BeanConverter.convert(CifCustomerAuths.class, cifCustomerAuthsDTO);
		return super.updateById(entity);
	}

	@CacheEvict(allEntries = true)
	@Override
	public boolean removeById(Serializable id) {
		return super.removeById(id);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表客户验证")
	@Override
	public List<CifCustomerAuthsDTO> list(CifCustomerAuthsDTO cifCustomerAuthsDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(cifCustomerAuthsDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表客户验证")
	@Override
	public List<CifCustomerAuthsDTO> list(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<CifCustomerAuths>	query	= Wrappers.<CifCustomerAuths>query().select(columns).allEq(true, params,
				false);
		List<CifCustomerAuths>			list	= super.list(query);
		return BeanConverter.convert(CifCustomerAuthsDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询客户验证")
	@Override
	public List<CifCustomerAuthsDTO> listLike(CifCustomerAuthsDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<CifCustomerAuthsDTO> listLike(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<CifCustomerAuths> query = Wrappers.<CifCustomerAuths>query().select(columns);
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

		List<CifCustomerAuths> list = super.list(query);
		return BeanConverter.convert(CifCustomerAuthsDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询客户验证")
	public PageInfoVO<CifCustomerAuthsDTO> page(Map<String, Object> params) {
		PageParams<CifCustomerAuthsDTO>	pageParams	= new PageParams<>(params);
		Page<CifCustomerAuths>			page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<CifCustomerAuths>	query		= Wrappers.<CifCustomerAuths>query();
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

		IPage<CifCustomerAuths>			iPage	= super.page(page, query);

		PageInfoVO<CifCustomerAuthsDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(CifCustomerAuthsDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

	@Override
	public Boolean updateByEntity(CifCustomerAuths condEntity, CifCustomerAuths updaeEntity) {
		QueryWrapper<CifCustomerAuths> query = Wrappers.<CifCustomerAuths>query(condEntity);
		return super.update(query);
	}

}
