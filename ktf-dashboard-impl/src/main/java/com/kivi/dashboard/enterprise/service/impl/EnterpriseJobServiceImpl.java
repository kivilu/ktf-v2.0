package com.kivi.dashboard.enterprise.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.enterprise.dto.EnterpriseJobDTO;
import com.kivi.dashboard.enterprise.entity.EnterpriseJob;
import com.kivi.dashboard.enterprise.mapper.EnterpriseJobMapper;
import com.kivi.dashboard.enterprise.service.IEnterpriseJobService;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 企业职务配置 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EnterpriseJobServiceImpl extends ServiceImpl<EnterpriseJobMapper, EnterpriseJob>
		implements IEnterpriseJobService {

	/**
	 * 根据ID查询企业职务配置
	 */
	@KtfTrace("根据ID查询企业职务配置")
	@Override
	public EnterpriseJobDTO getDTOById(Long id) {
		EnterpriseJob		entity	= super.getById(id);
		EnterpriseJobDTO	dto		= BeanConverter.convert(EnterpriseJobDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增企业职务配置
	 */
	@KtfTrace("新增企业职务配置")
	@Override
	public Boolean save(EnterpriseJobDTO enterpriseJobDTO) {
		EnterpriseJob entity = BeanConverter.convert(EnterpriseJob.class, enterpriseJobDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改企业职务配置")
	@Override
	public Boolean updateById(EnterpriseJobDTO enterpriseJobDTO) {
		EnterpriseJob entity = BeanConverter.convert(EnterpriseJob.class, enterpriseJobDTO);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表企业职务配置")
	@Override
	public List<EnterpriseJobDTO> list(EnterpriseJobDTO enterpriseJobDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(enterpriseJobDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表企业职务配置")
	@Override
	public List<EnterpriseJobDTO> list(Map<String, Object> params, String... columns) {
		QueryWrapper<EnterpriseJob>	query	= Wrappers.<EnterpriseJob>query().select(columns).allEq(true, params,
				false);
		List<EnterpriseJob>			list	= super.list(query);
		return BeanConverter.convert(EnterpriseJobDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询企业职务配置")
	@Override
	public List<EnterpriseJobDTO> listLike(EnterpriseJobDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<EnterpriseJobDTO> listLike(Map<String, Object> params, String... columns) {
		QueryWrapper<EnterpriseJob> query = Wrappers.<EnterpriseJob>query().select(columns);
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		List<EnterpriseJob> list = super.list(query);
		return BeanConverter.convert(EnterpriseJobDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@KtfTrace("分页查询企业职务配置")
	public PageInfoVO<EnterpriseJobDTO> page(Map<String, Object> params) {
		PageParams<EnterpriseJobDTO>	pageParams	= new PageParams<>(params);
		Page<EnterpriseJob>				page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<EnterpriseJob>		query		= Wrappers.<EnterpriseJob>query();
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		IPage<EnterpriseJob>			iPage	= super.page(page, query);

		PageInfoVO<EnterpriseJobDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(EnterpriseJobDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

}
