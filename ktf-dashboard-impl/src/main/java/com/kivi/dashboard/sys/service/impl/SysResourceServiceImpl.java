package com.kivi.dashboard.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.sys.dto.SysResourceDTO;
import com.kivi.dashboard.sys.entity.SysResource;
import com.kivi.dashboard.sys.mapper.SysResourceExMapper;
import com.kivi.dashboard.sys.mapper.SysResourceMapper;
import com.kivi.dashboard.sys.service.ISysResourceService;
import com.kivi.dashboard.sys.service.ISysUserService;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.constant.enums.CommonEnum;
import com.kivi.framework.constant.enums.KtfStatus;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.vo.ResourceVo;
import com.kivi.framework.vo.page.PageInfoVO;
import com.vip.vjtools.vjkit.collection.MapUtil;

/**
 * <p>
 * 资源 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@Primary
@Service
@Transactional(rollbackFor = Exception.class)
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements ISysResourceService {
	@Autowired
	private ISysUserService		sysUserService;

	@Autowired
	private SysResourceExMapper	sysResourceExMapper;

	/**
	 * 根据ID查询资源
	 */
	@KtfTrace("根据ID查询资源")
	@Override
	public SysResourceDTO getDTOById(Long id) {
		SysResource		entity	= super.getById(id);
		SysResourceDTO	dto		= BeanConverter.convert(SysResourceDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增资源
	 */
	@KtfTrace("新增资源")
	@Override
	public Boolean save(SysResourceDTO sysResourceDTO) {
		SysResource entity = BeanConverter.convert(SysResource.class, sysResourceDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改资源")
	@Override
	public Boolean updateById(SysResourceDTO sysResourceDTO) {
		SysResource entity = BeanConverter.convert(SysResource.class, sysResourceDTO);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表资源")
	@Override
	public List<SysResourceDTO> list(SysResourceDTO sysResourceDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(sysResourceDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表资源")
	@Override
	public List<SysResourceDTO> list(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysResource>	query	= Wrappers.<SysResource>query().select(columns).allEq(true, params, false);
		List<SysResource>			list	= super.list(query);
		return BeanConverter.convert(SysResourceDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询资源")
	@Override
	public List<SysResourceDTO> listLike(SysResourceDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysResourceDTO> listLike(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysResource> query = Wrappers.<SysResource>query().select(columns);
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

		List<SysResource> list = super.list(query);
		return BeanConverter.convert(SysResourceDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询资源")
	public PageInfoVO<SysResourceDTO> page(Map<String, Object> params) {
		PageParams<SysResourceDTO>	pageParams	= new PageParams<>(params);
		Page<SysResource>			page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<SysResource>	query		= Wrappers.<SysResource>query();
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

		IPage<SysResource>			iPage	= super.page(page, query);

		PageInfoVO<SysResourceDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(SysResourceDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

	@KtfTrace("根据用户ID查询对应的资源列表")
	@Override
	public List<ResourceVo> selectUserResourceListByUserId(Long userId) {
		// 系统管理员，拥有最高权限
		if (userId == KtfConstant.SUPER_ADMIN) {
			return selectMenuList(null);
		}
		// 用户菜单列表
		List<Long>			menuIdList	= sysUserService.selectResourceIdListByUserId(userId);
		List<ResourceVo>	menuList	= selectMenuList(menuIdList);

		return menuList;
	}

	@KtfTrace("查询指定ParentId的菜单列表")
	@Override
	public List<SysResource> selectListByParentId(Long parentId, List<Long> menuIdList) {
		List<SysResource> menuList = selectListByParentId(parentId);
		if (menuIdList == null) {
			return menuList;
		}

		List<SysResource> userMenuList = new ArrayList<>();
		for (SysResource menu : menuList) {
			if (menuIdList.contains(menu.getId())) {
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@KtfTrace("根据ParentId查询菜单")
	@Override
	public List<SysResource> selectListByParentId(Long parentId) {
		QueryWrapper<SysResource> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(SysResource.DB_PARENT_ID, parentId).eq(SysResource.DB_STATUS, KtfStatus.ENABLED.code)
				.orderByAsc(SysResource.DB_SEQ);
		return this.baseMapper.selectList(queryWrapper);
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<ResourceVo> selectMenuList(List<Long> menuIdList) {
		// 查询根菜单列表
		List<SysResource>	menuList	= selectListByParentId(0L, menuIdList);

		List<ResourceVo>	result		= BeanConverter.convert(ResourceVo.class, menuList);
		// 递归获取子菜单
		selectMenuTreeList(result, menuIdList);

		return result;
	}

	/**
	 * 递归
	 */
	private List<ResourceVo> selectMenuTreeList(List<ResourceVo> menuList, List<Long> menuIdList) {
		List<ResourceVo> subMenuList = new ArrayList<>();
		for (ResourceVo entity : menuList) {
			// 目录
			if (entity.getResourceType() == CommonEnum.MenuType.CATALOG.getValue()) {
				List<SysResource> list = selectListByParentId(entity.getId(), menuIdList);
				entity.setList(selectMenuTreeList(BeanConverter.convert(ResourceVo.class, list), menuIdList));
			}
			subMenuList.add(entity);
		}
		return subMenuList;
	}

	@KtfTrace("根据条件查询菜单列表")
	@Override
	public List<ResourceVo> selectResourceList(Map<String, Object> params) {
		return this.sysResourceExMapper.selectResourceList(params);
	}

	@Override
	public List<ResourceVo> selectNotButtonList() {
		QueryWrapper<SysResource> queryWrapper = new QueryWrapper<>();
		queryWrapper.ne(SysResource.DB_RESOURCE_TYPE, CommonEnum.MenuType.BUTTON.getValue())
				.eq(SysResource.DB_STATUS, KtfStatus.ENABLED.code).orderByAsc(SysResource.DB_SEQ);
		List<SysResource> list = this.baseMapper.selectList(queryWrapper);

		return BeanConverter.convert(ResourceVo.class, list);
	}

}