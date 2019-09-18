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
import com.kivi.dashboard.enterprise.dto.EnterpriseDepartmentDTO;
import com.kivi.dashboard.enterprise.entity.EnterpriseDepartment;
import com.kivi.dashboard.enterprise.mapper.EnterpriseDepartmentMapper;
import com.kivi.dashboard.enterprise.service.IEnterpriseDepartmentService;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 企业部门 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EnterpriseDepartmentServiceImpl extends ServiceImpl<EnterpriseDepartmentMapper, EnterpriseDepartment>
		implements IEnterpriseDepartmentService {

	/**
	 * 根据ID查询企业部门
	 */
	@KtfTrace("根据ID查询企业部门")
	@Override
	public EnterpriseDepartmentDTO getDTOById(Long id) {
		EnterpriseDepartment	entity	= super.getById(id);
		EnterpriseDepartmentDTO	dto		= BeanConverter.convert(EnterpriseDepartmentDTO.class, entity,
				BeanConverter.long2String, BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增企业部门
	 */
	@KtfTrace("新增企业部门")
	@Override
	public Boolean save(EnterpriseDepartmentDTO enterpriseDepartmentDTO) {
		EnterpriseDepartment entity = BeanConverter.convert(EnterpriseDepartment.class, enterpriseDepartmentDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改企业部门")
	@Override
	public Boolean updateById(EnterpriseDepartmentDTO enterpriseDepartmentDTO) {
		EnterpriseDepartment entity = BeanConverter.convert(EnterpriseDepartment.class, enterpriseDepartmentDTO);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表企业部门")
	@Override
	public List<EnterpriseDepartmentDTO> list(EnterpriseDepartmentDTO enterpriseDepartmentDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(enterpriseDepartmentDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表企业部门")
	@Override
	public List<EnterpriseDepartmentDTO> list(Map<String, Object> params, String... columns) {
		QueryWrapper<EnterpriseDepartment>	query	= Wrappers.<EnterpriseDepartment>query().select(columns).allEq(true,
				params, false);
		List<EnterpriseDepartment>			list	= super.list(query);
		return BeanConverter.convert(EnterpriseDepartmentDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询企业部门")
	@Override
	public List<EnterpriseDepartmentDTO> listLike(EnterpriseDepartmentDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<EnterpriseDepartmentDTO> listLike(Map<String, Object> params, String... columns) {
		QueryWrapper<EnterpriseDepartment> query = Wrappers.<EnterpriseDepartment>query().select(columns);
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		List<EnterpriseDepartment> list = super.list(query);
		return BeanConverter.convert(EnterpriseDepartmentDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@KtfTrace("分页查询企业部门")
	public PageInfoVO<EnterpriseDepartmentDTO> page(Map<String, Object> params) {
		PageParams<EnterpriseDepartmentDTO>	pageParams	= new PageParams<>(params);
		Page<EnterpriseDepartment>			page		= new Page<>(pageParams.getCurrPage(),
				pageParams.getPageSize());

		QueryWrapper<EnterpriseDepartment>	query		= Wrappers.<EnterpriseDepartment>query();
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		IPage<EnterpriseDepartment>			iPage	= super.page(page, query);

		PageInfoVO<EnterpriseDepartmentDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(EnterpriseDepartmentDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

}
