package com.kivi.dashboard.permission.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.permission.dto.SysRoleResourceDTO;
import com.kivi.dashboard.permission.entity.SysRoleResource;
import com.kivi.dashboard.permission.mapper.SysRoleResourceExMapper;
import com.kivi.dashboard.permission.mapper.SysRoleResourceMapper;
import com.kivi.dashboard.permission.service.SysRoleResourceService;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.vo.page.PageInfoVO;
import com.vip.vjtools.vjkit.collection.ListUtil;
import com.vip.vjtools.vjkit.collection.MapUtil;

/**
 * <p>
 * 角色资源 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Primary
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleResourceServiceImpl extends ServiceImpl<SysRoleResourceMapper, SysRoleResource>
		implements SysRoleResourceService {
	@Autowired
	private SysRoleResourceExMapper sysRoleResourceExMapper;

	/**
	 * 根据ID查询角色资源
	 */
	@KtfTrace("根据ID查询角色资源")
	@Override
	public SysRoleResourceDTO getDTOById(Long id) {
		SysRoleResource		entity	= super.getById(id);
		SysRoleResourceDTO	dto		= BeanConverter.convert(SysRoleResourceDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增角色资源
	 */
	@KtfTrace("新增角色资源")
	@Override
	public Boolean save(SysRoleResourceDTO sysRoleResourceDTO) {
		SysRoleResource entity = BeanConverter.convert(SysRoleResource.class, sysRoleResourceDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改角色资源")
	@Override
	public Boolean updateById(SysRoleResourceDTO sysRoleResourceDTO) {
		SysRoleResource entity = BeanConverter.convert(SysRoleResource.class, sysRoleResourceDTO);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表角色资源")
	@Override
	public List<SysRoleResourceDTO> list(SysRoleResourceDTO sysRoleResourceDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(sysRoleResourceDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表角色资源")
	@Override
	public List<SysRoleResourceDTO> list(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysRoleResource>	query	= Wrappers.<SysRoleResource>query().select(columns).allEq(true, params,
				false);
		List<SysRoleResource>			list	= super.list(query);
		return BeanConverter.convert(SysRoleResourceDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询角色资源")
	@Override
	public List<SysRoleResourceDTO> listLike(SysRoleResourceDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@KtfTrace("指定列模糊查询")
	@Override
	public List<SysRoleResourceDTO> listLike(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysRoleResource> query = Wrappers.<SysRoleResource>query().select(columns);
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

		List<SysRoleResource> list = super.list(query);
		return BeanConverter.convert(SysRoleResourceDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询角色资源")
	public PageInfoVO<SysRoleResourceDTO> page(Map<String, Object> params) {
		PageParams<SysRoleResourceDTO>	pageParams	= new PageParams<>(params);
		Page<SysRoleResource>			page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<SysRoleResource>	query		= Wrappers.<SysRoleResource>query();
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

		IPage<SysRoleResource>			iPage	= super.page(page, query);

		PageInfoVO<SysRoleResourceDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(SysRoleResourceDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

	@Override
	public List<Long> selectResourceIdListByRoleId(Long roleId) {
		QueryWrapper<SysRoleResource>	query	= Wrappers.<SysRoleResource>query()
				.select(SysRoleResource.DB_RESOURCE_ID).eq(SysRoleResource.DB_ROLE_ID, roleId);

		List<SysRoleResource>			list	= super.list(query);
		if (ListUtil.isEmpty(list))
			return null;

		return list.stream().map(SysRoleResource::getResourceId).collect(Collectors.toList());
	}

	@Override
	public List<SysRoleResourceDTO> selectResourceNodeListByRoleId(Long roleId) {
		return sysRoleResourceExMapper.selectResourceNodeListByRoleId(roleId);
	}

	@Override
	public Boolean saveOrUpdateRoleResource(Long roleId, List<Long> resourceIdList) {
		// 先删除角色与菜单关系
		Map<String, Object> params = new HashMap<>();
		params.put(SysRoleResource.DB_ROLE_ID, roleId);
		this.removeByMap(params);
		if (resourceIdList.isEmpty() || resourceIdList.size() == 0) {
			return false;
		}
		// 保存角色与菜单关系
		List<SysRoleResource> list = new ArrayList<>(resourceIdList.size());
		for (Long resourceId : resourceIdList) {
			SysRoleResource sysRoleResource = new SysRoleResource();
			sysRoleResource.setRoleId(roleId);
			sysRoleResource.setResourceId(resourceId);
			list.add(sysRoleResource);
		}
		return this.saveBatch(list);

	}

	@Override
	public int deleteBatchByRoleIds(Long[] roleIds) {
		if (roleIds == null)
			return -1;

		QueryWrapper<SysRoleResource> query = Wrappers.<SysRoleResource>query();
		query.in(SysRoleResource.DB_ROLE_ID, Arrays.asList(roleIds));
		return this.baseMapper.delete(query);
	}

}
