package com.kivi.dashboard.sys.service.impl;

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
import com.kivi.dashboard.sys.dto.SysRoleDTO;
import com.kivi.dashboard.sys.entity.SysRole;
import com.kivi.dashboard.sys.entity.vo.RoleVo;
import com.kivi.dashboard.sys.mapper.SysRoleExMapper;
import com.kivi.dashboard.sys.mapper.SysRoleMapper;
import com.kivi.dashboard.sys.service.ISysRoleService;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

	@Autowired
	SysRoleExMapper sysRoleExMapper;

	/**
	 * 根据ID查询角色
	 */
	@KtfTrace("根据ID查询角色")
	@Override
	public SysRoleDTO getDTOById(Long id) {
		SysRole		entity	= super.getById(id);
		SysRoleDTO	dto		= BeanConverter.convert(SysRoleDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增角色
	 */
	@KtfTrace("新增角色")
	@Override
	public Boolean save(SysRoleDTO sysRoleDTO) {
		SysRole entity = BeanConverter.convert(SysRole.class, sysRoleDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改角色")
	@Override
	public Boolean updateById(SysRoleDTO sysRoleDTO) {
		SysRole entity = BeanConverter.convert(SysRole.class, sysRoleDTO);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表角色")
	@Override
	public List<SysRoleDTO> list(SysRoleDTO sysRoleDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(sysRoleDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表角色")
	@Override
	public List<SysRoleDTO> list(Map<String, Object> params, String... columns) {
		QueryWrapper<SysRole>	query	= Wrappers.<SysRole>query().select(columns).allEq(true, params, false);
		List<SysRole>			list	= super.list(query);
		return BeanConverter.convert(SysRoleDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询角色")
	@Override
	public List<SysRoleDTO> listLike(SysRoleDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysRoleDTO> listLike(Map<String, Object> params, String... columns) {
		QueryWrapper<SysRole> query = Wrappers.<SysRole>query().select(columns);
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		List<SysRole> list = super.list(query);
		return BeanConverter.convert(SysRoleDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@KtfTrace("分页查询角色")
	public PageInfoVO<SysRoleDTO> page(Map<String, Object> params) {
		PageParams<SysRoleDTO>	pageParams	= new PageParams<>(params);
		Page<SysRole>			page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<SysRole>	query		= Wrappers.<SysRole>query();
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		IPage<SysRole>			iPage	= super.page(page, query);

		PageInfoVO<SysRoleDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(SysRoleDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

	@Override
	public RoleVo selectByRoleId(Long roleId) {
		return sysRoleExMapper.selectByRoleId(roleId);
	}

}
