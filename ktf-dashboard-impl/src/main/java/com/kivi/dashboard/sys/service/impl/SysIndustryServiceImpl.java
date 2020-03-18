package com.kivi.dashboard.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.sys.dto.SysIndustryDTO;
import com.kivi.dashboard.sys.entity.SysIndustry;
import com.kivi.dashboard.sys.mapper.SysIndustryMapper;
import com.kivi.dashboard.sys.service.ISysIndustryService;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.vo.page.PageInfoVO;
import com.vip.vjtools.vjkit.collection.MapUtil;

/**
 * <p>
 * 行业代码 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class SysIndustryServiceImpl extends ServiceImpl<SysIndustryMapper, SysIndustry> implements ISysIndustryService {

	/**
	 * 根据ID查询行业代码
	 */
	@KtfTrace("根据ID查询行业代码")
	@Override
	public SysIndustryDTO getDTOById(Long id) {
		SysIndustry		entity	= super.getById(id);
		SysIndustryDTO	dto		= BeanConverter.convert(SysIndustryDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增行业代码
	 */
	@KtfTrace("新增行业代码")
	@Override
	public Boolean save(SysIndustryDTO sysIndustryDTO) {
		SysIndustry entity = BeanConverter.convert(SysIndustry.class, sysIndustryDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改行业代码")
	@Override
	public Boolean updateById(SysIndustryDTO sysIndustryDTO) {
		SysIndustry entity = BeanConverter.convert(SysIndustry.class, sysIndustryDTO);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表行业代码")
	@Override
	public List<SysIndustryDTO> list(SysIndustryDTO sysIndustryDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(sysIndustryDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表行业代码")
	@Override
	public List<SysIndustryDTO> list(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysIndustry>	query	= Wrappers.<SysIndustry>query().select(columns).allEq(true, params, false);
		List<SysIndustry>			list	= super.list(query);
		return BeanConverter.convert(SysIndustryDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询行业代码")
	@Override
	public List<SysIndustryDTO> listLike(SysIndustryDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysIndustryDTO> listLike(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysIndustry> query = Wrappers.<SysIndustry>query().select(columns);
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

		List<SysIndustry> list = super.list(query);
		return BeanConverter.convert(SysIndustryDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@KtfTrace("分页查询行业代码")
	public PageInfoVO<SysIndustryDTO> page(Map<String, Object> params) {
		PageParams<SysIndustryDTO>	pageParams	= new PageParams<>(params);
		Page<SysIndustry>			page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<SysIndustry>	query		= Wrappers.<SysIndustry>query();
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

		IPage<SysIndustry>			iPage	= super.page(page, query);

		PageInfoVO<SysIndustryDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(SysIndustryDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

}
