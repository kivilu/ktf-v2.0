package com.kivi.dashboard.sys.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.cache.annotation.KtfCacheEvict;
import com.kivi.framework.cache.constant.KtfCache;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.constant.enums.CommonEnum;
import com.kivi.framework.constant.enums.CommonEnum.MenuType;
import com.kivi.framework.constant.enums.KtfStatus;
import com.kivi.framework.constant.enums.KtfYesNo;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.CollectionKit;
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
	private SysResourceExMapper sysResourceExMapper;

	/**
	 * 根据ID查询资源
	 */
	@Cacheable(value = KtfCache.SysResource, key = "caches[0].name+'.dto.'+#id", unless = "#result == null")
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
	@KtfCacheEvict(cacheNames = { KtfCache.SysResource, KtfCache.SysUser })
	@KtfTrace("新增资源")
	@Override
	public Boolean save(SysResourceDTO sysResourceDTO) {
		SysResource entity = BeanConverter.convert(SysResource.class, sysResourceDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfCacheEvict(cacheNames = { KtfCache.SysResource, KtfCache.SysUser })

	@KtfTrace("修改资源")
	@Override
	public Boolean updateById(SysResourceDTO sysResourceDTO) {
		SysResource entity = BeanConverter.convert(SysResource.class, sysResourceDTO);
		return super.updateById(entity);
	}

	/**
	 * 根据ID删除资源
	 */
	@KtfCacheEvict(cacheNames = { KtfCache.SysResource, KtfCache.SysUser })
	@KtfTrace("删除资源")
	@Override
	public boolean removeById(Serializable id) {
		return super.removeById(id);
	}

	/**
	 * 根据IDs删除资源
	 */
	@KtfCacheEvict(cacheNames = { KtfCache.SysResource, KtfCache.SysUser })
	@KtfTrace("删除资源")
	@Override
	public boolean removeByIds(Collection<? extends Serializable> idList) {
		return super.removeByIds(idList);
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

	@Cacheable(
			value = KtfCache.SysResource,
			key = "caches[0].name+'.list.'+#userId+#types?.hashCode()",
			unless = "#result == null")
	@KtfTrace("根据用户ID查询对应的资源列表")
	@Override
	public List<ResourceVo> selectUserResourceListByUserId(Long userId, MenuType... types) {
		// 系统管理员，拥有最高权限
		if (userId == KtfConstant.SUPER_ADMIN) {
			return selectMenuList(null);
		}
		// 用户菜单列表
		Map<String, Object> params = new HashMap<>();

		if (userId != null && KtfConstant.SUPER_ADMIN != userId.longValue())
			params.put("userId", userId);

		if (types != null && types.length > 0)
			params.put("resourceTypes",
					Arrays.asList(types).stream().map(MenuType::getValue).collect(Collectors.toList()));

		params.put(SysResourceDTO.STATUS, KtfStatus.ENABLED.code);
		params.put(SysResourceDTO.HIDDEN, KtfYesNo.NO.bool);
		List<SysResourceDTO>	sysMenus	= sysResourceExMapper.selectResourceListByUserId(params);

		Map<Long, ResourceVo>	menuMap		= new HashMap<>();
		List<ResourceVo>		menuList	= sysMenus.stream()
				.filter(dto -> dto.getResourceType() == MenuType.CATALOG.getValue()).map(dto -> {
														ResourceVo pMenu = new ResourceVo();
														pMenu.setId(dto.getId());
														pMenu.setParentId(dto.getParentId());
														pMenu.setName(dto.getName());
														pMenu.setIcon(dto.getIcon());
														pMenu.setResourceType(CommonEnum.MenuType.CATALOG.getValue());
														pMenu.setUrl("");
														pMenu.setList(CollectionKit.newArrayList());

														menuMap.put(pMenu.getId(), pMenu);

														return pMenu;
													})
				.collect(Collectors.toList());

		sysMenus.stream().filter(dto -> dto.getResourceType() == MenuType.MENU.getValue()).forEach(child -> {
			ResourceVo menu = new ResourceVo();
			menu.setId(child.getId());
			menu.setParentId(child.getParentId());
			menu.setName(child.getName());
			menu.setIcon(child.getIcon());
			menu.setResourceType(CommonEnum.MenuType.MENU.getValue());
			menu.setUrl(child.getUrl());
			ResourceVo pMenu = menuMap.get(child.getParentId());
			if (pMenu != null)
				pMenu.getList().add(menu);
		});

		return menuList.stream().filter(menu -> !menu.getList().isEmpty()).collect(Collectors.toList());
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表用户角色")
	@Override
	public List<SysResourceDTO> list(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysResource>	query	= Wrappers.<SysResource>query().select(columns).allEq(true, params, false);
		List<SysResource>			list	= super.list(query);
		return BeanConverter.convert(SysResourceDTO.class, list);
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
		queryWrapper
				.select(SysResource.DB_ID, SysResource.DB_PARENT_ID, SysResource.DB_NAME, SysResource.DB_URL,
						SysResource.DB_ICON, SysResource.DB_RESOURCE_TYPE)
				.eq(SysResource.DB_PARENT_ID, parentId).eq(SysResource.DB_STATUS, KtfStatus.ENABLED.code)
				.eq(SysResource.DB_HIDDEN, 0).orderByAsc(SysResource.DB_SEQ);
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

		//

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
	public List<ResourceVo> selectMenuTreeList(Map<String, Object> params) {
		return this.sysResourceExMapper.selectMenuTreeList(params);
	}

	@Override
	public List<ResourceVo> selectNotButtonList() {
		QueryWrapper<SysResource> queryWrapper = new QueryWrapper<>();
		queryWrapper.ne(SysResource.DB_RESOURCE_TYPE, CommonEnum.MenuType.BUTTON.getValue())
				.eq(SysResource.DB_STATUS, KtfStatus.ENABLED.code).orderByAsc(SysResource.DB_SEQ);
		List<SysResource> list = this.baseMapper.selectList(queryWrapper);

		return BeanConverter.convert(ResourceVo.class, list);
	}

	@Cacheable(value = KtfCache.SysResource, key = "caches[0].name+'.ids.'+#userId", unless = "#result == null")
	@Override
	public List<Long> selectResourceIdListByUserId(Long userId) {
		return sysResourceExMapper.selectResourceIdListByUserId(userId);
	}

}
