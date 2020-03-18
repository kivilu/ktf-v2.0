package com.kivi.dashboard.enterprise.service.impl;

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
import com.kivi.dashboard.enterprise.dto.EnterpriseDTO;
import com.kivi.dashboard.enterprise.entity.Enterprise;
import com.kivi.dashboard.enterprise.mapper.EnterpriseExMapper;
import com.kivi.dashboard.enterprise.mapper.EnterpriseMapper;
import com.kivi.dashboard.enterprise.service.IEnterpriseService;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 企业信息 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class EnterpriseServiceImpl extends ServiceImpl<EnterpriseMapper, Enterprise> implements IEnterpriseService {

	@Autowired
	private EnterpriseExMapper enterpriseExMapper;

	/**
	 * 根据ID查询企业信息
	 */
	@KtfTrace("根据ID查询企业信息")
	@Override
	public EnterpriseDTO getDTOById(Long id) {
		Enterprise		entity	= super.getById(id);
		EnterpriseDTO	dto		= BeanConverter.convert(EnterpriseDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增企业信息
	 */
	@KtfTrace("新增企业信息")
	@Override
	public Enterprise save(EnterpriseDTO enterpriseDTO) {
		Enterprise entity = BeanConverter.convert(Enterprise.class, enterpriseDTO);

		super.save(entity);

		return entity;
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改企业信息")
	@Override
	public Boolean updateById(EnterpriseDTO enterpriseDTO) {
		Enterprise entity = BeanConverter.convert(Enterprise.class, enterpriseDTO);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表企业信息")
	@Override
	public List<EnterpriseDTO> list(EnterpriseDTO enterpriseDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(enterpriseDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表企业信息")
	@Override
	public List<EnterpriseDTO> list(Map<String, Object> params, String... columns) {
		QueryWrapper<Enterprise>	query	= Wrappers.<Enterprise>query().select(columns).allEq(true, params, false);
		List<Enterprise>			list	= super.list(query);
		return BeanConverter.convert(EnterpriseDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询企业信息")
	@Override
	public List<EnterpriseDTO> listLike(EnterpriseDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<EnterpriseDTO> listLike(Map<String, Object> params, String... columns) {
		QueryWrapper<Enterprise> query = Wrappers.<Enterprise>query().select(columns);
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		List<Enterprise> list = super.list(query);
		return BeanConverter.convert(EnterpriseDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询企业信息")
	public PageInfoVO<EnterpriseDTO> page(Map<String, Object> params) {
		PageParams<EnterpriseDTO>	pageParams	= new PageParams<>(params);
		Page<Enterprise>			page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<Enterprise>	query		= Wrappers.<Enterprise>query();
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		IPage<Enterprise>			iPage	= super.page(page, query);

		PageInfoVO<EnterpriseDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(EnterpriseDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

	@Override
	public List<Map<String, Object>> selectNames() {
		QueryWrapper<Enterprise>	query	= Wrappers.<Enterprise>query().select(Enterprise.DB_ID,
				Enterprise.DB_ENTERPRISE_NAME);
		List<Enterprise>			list	= super.list(query);
		return BeanConverter.beansToMap(list);
	}

	@Override
	public List<Map<String, Object>> select(Map<String, Object> params) {
		return enterpriseExMapper.selectEnterpriseList(params);
	}

	@Override
	public PageInfoVO<Map<String, Object>> selectByPage(Map<String, Object> params) {
		PageParams<Map<String, Object>>	pageParams	= new PageParams<>(params);
		Page<Map<String, Object>>		page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());
		IPage<Map<String, Object>>		iPage		= enterpriseExMapper.selectEnterprisePage(page, params);

		PageInfoVO<Map<String, Object>>	pageVo		= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(iPage.getRecords());
		pageVo.compute();

		return pageVo;
	}

}
