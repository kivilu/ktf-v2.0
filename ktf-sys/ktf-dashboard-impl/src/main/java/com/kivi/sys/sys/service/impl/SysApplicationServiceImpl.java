package com.kivi.sys.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.properties.KtfCommonProperties;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.sys.dto.SysApplicationDTO;
import com.kivi.sys.sys.entity.SysApplication;
import com.kivi.sys.sys.mapper.SysApplicationMapper;
import com.kivi.sys.sys.service.ISysApplicationService;
import com.vip.vjtools.vjkit.collection.MapUtil;

/**
 * <p>
 * 系统应用 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class SysApplicationServiceImpl extends ServiceImpl<SysApplicationMapper, SysApplication>
		implements ISysApplicationService {

	@Autowired
	KtfCommonProperties ktfCommonProperties;

	/**
	 * 根据ID查询系统应用
	 */
	@KtfTrace("根据ID查询系统应用")
	@Override
	public SysApplicationDTO getDTOById(Long id) {
		SysApplication		entity	= super.getById(id);
		SysApplicationDTO	dto		= BeanConverter.convert(SysApplicationDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增系统应用
	 */
	@KtfTrace("新增系统应用")
	@Override
	public Boolean save(SysApplicationDTO sysApplicationDTO) {
		SysApplication entity = BeanConverter.convert(SysApplication.class, sysApplicationDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改系统应用")
	@Override
	public Boolean updateById(SysApplicationDTO sysApplicationDTO) {
		SysApplication entity = BeanConverter.convert(SysApplication.class, sysApplicationDTO);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表系统应用")
	@Override
	public List<SysApplicationDTO> list(SysApplicationDTO sysApplicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(sysApplicationDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表系统应用")
	@Override
	public List<SysApplicationDTO> list(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysApplication>	query	= Wrappers.<SysApplication>query().select(columns).allEq(true, params,
				false);
		List<SysApplication>			list	= super.list(query);
		return BeanConverter.convert(SysApplicationDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询系统应用")
	@Override
	public List<SysApplicationDTO> listLike(SysApplicationDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysApplicationDTO> listLike(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysApplication> query = Wrappers.<SysApplication>query().select(columns);
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

		List<SysApplication> list = super.list(query);
		return BeanConverter.convert(SysApplicationDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询系统应用")
	public PageInfoVO<SysApplicationDTO> page(Map<String, Object> params) {
		PageParams<SysApplicationDTO>	pageParams	= new PageParams<>(params);
		Page<SysApplication>			page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<SysApplication>	query		= Wrappers.<SysApplication>query();
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

		IPage<SysApplication>			iPage	= super.page(page, query);

		PageInfoVO<SysApplicationDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(SysApplicationDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

	@Override
	public Long getOrCreate(String code) {
		QueryWrapper<SysApplication>	query	= Wrappers.<SysApplication>query().eq(SysApplication.DB_CODE, code);
		SysApplication					entity	= super.getOne(query);
		if (entity == null) {
			entity = new SysApplication();
			entity.setCode(code);
			entity.setName(ktfCommonProperties.getBzApplicatonName());
			entity.insert();
		}
		return entity.getId();
	}

}
