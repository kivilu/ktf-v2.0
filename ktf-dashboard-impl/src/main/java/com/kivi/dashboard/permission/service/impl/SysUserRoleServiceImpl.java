package com.kivi.dashboard.permission.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.permission.dto.SysUserRoleDTO;
import com.kivi.dashboard.permission.entity.SysRoleResource;
import com.kivi.dashboard.permission.entity.SysUserRole;
import com.kivi.dashboard.permission.mapper.SysUserRoleMapper;
import com.kivi.dashboard.permission.service.SysUserRoleService;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.cache.annotation.KtfCacheEvict;
import com.kivi.framework.cache.constant.KtfCache;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.vo.page.PageInfoVO;
import com.vip.vjtools.vjkit.collection.ListUtil;
import com.vip.vjtools.vjkit.collection.MapUtil;

/**
 * <p>
 * 用户角色 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Primary
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

	/**
	 * 根据ID查询用户角色
	 */
	@KtfTrace("根据ID查询用户角色")
	@Override
	public SysUserRoleDTO getDTOById(Long id) {
		SysUserRole		entity	= super.getById(id);
		SysUserRoleDTO	dto		= BeanConverter.convert(SysUserRoleDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增用户角色
	 */
	@KtfTrace("新增用户角色")
	@Override
	public Boolean save(SysUserRoleDTO sysUserRoleDTO) {
		SysUserRole entity = BeanConverter.convert(SysUserRole.class, sysUserRoleDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfCacheEvict(cacheNames = { KtfCache.SysUser })
	@KtfTrace("修改用户角色")
	@Override
	public Boolean updateById(SysUserRoleDTO sysUserRoleDTO) {
		SysUserRole entity = BeanConverter.convert(SysUserRole.class, sysUserRoleDTO);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表用户角色")
	@Override
	public List<SysUserRoleDTO> list(SysUserRoleDTO sysUserRoleDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(sysUserRoleDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表用户角色")
	@Override
	public List<SysUserRoleDTO> list(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysUserRole>	query	= Wrappers.<SysUserRole>query().select(columns).allEq(true, params, false);
		List<SysUserRole>			list	= super.list(query);
		return BeanConverter.convert(SysUserRoleDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询用户角色")
	@Override
	public List<SysUserRoleDTO> listLike(SysUserRoleDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysUserRoleDTO> listLike(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysUserRole> query = Wrappers.<SysUserRole>query().select(columns);
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

		List<SysUserRole> list = super.list(query);
		return BeanConverter.convert(SysUserRoleDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询用户角色")
	public PageInfoVO<SysUserRoleDTO> page(Map<String, Object> params) {
		PageParams<SysUserRoleDTO>	pageParams	= new PageParams<>(params);
		Page<SysUserRole>			page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<SysUserRole>	query		= Wrappers.<SysUserRole>query();
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

		IPage<SysUserRole>			iPage	= super.page(page, query);

		PageInfoVO<SysUserRoleDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(SysUserRoleDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

	@KtfCacheEvict(cacheNames = { KtfCache.SysUser })
	@Override
	public Boolean deleteBatchByRoleIds(Long[] roleIds) {
		if (roleIds == null)
			return false;

		QueryWrapper<SysUserRole> query = Wrappers.<SysUserRole>query();
		query.in(SysUserRole.DB_ROLE_ID, Arrays.asList(roleIds));
		return super.remove(query);

	}

	@KtfCacheEvict(cacheNames = { KtfCache.SysUser })
	@Override
	public Boolean deleteBatchByUserIds(Long[] userIds) {
		if (userIds == null)
			return false;
		QueryWrapper<SysUserRole> query = Wrappers.<SysUserRole>query();
		query.in(SysUserRole.DB_USER_ID, ListUtil.newArrayList(userIds));

		return super.remove(query);
	}

	@Override
	public List<Long> selectRoleIdListByUserId(Long userId) {
		QueryWrapper<SysUserRole>	query	= Wrappers.<SysUserRole>query().select(SysRoleResource.DB_ROLE_ID)
				.eq(SysUserRole.DB_USER_ID, userId);

		List<SysUserRole>			list	= super.list(query);
		if (ListUtil.isEmpty(list))
			return null;

		return list.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
	}

	@KtfCacheEvict(cacheNames = { KtfCache.SysUser })
	@Override
	public void saveOrUpdateUserRole(Long userId, List<Long> roleIdList) {
		// 先删除用户与角色关系
		Map<String, Object> params = new HashMap<>();
		params.put(SysUserRole.DB_USER_ID, userId);
		this.removeByMap(params);
		if (roleIdList == null || roleIdList.size() == 0) {
			return;
		}
		// 保存用户与角色关系
		List<SysUserRole> list = roleIdList.stream().map(roleId -> {
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setUserId(userId);
			sysUserRole.setRoleId(roleId);
			return sysUserRole;
		}).collect(Collectors.toList());

		this.saveBatch(list);
	}

}
