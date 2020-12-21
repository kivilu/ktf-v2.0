package com.kivi.dashboard.permission.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.permission.dto.SysRoleDTO;
import com.kivi.dashboard.permission.entity.SysRole;
import com.kivi.dashboard.permission.mapper.SysResourceExMapper;
import com.kivi.dashboard.permission.mapper.SysRoleExMapper;
import com.kivi.dashboard.permission.mapper.SysRoleMapper;
import com.kivi.dashboard.permission.service.SysRoleResourceService;
import com.kivi.dashboard.permission.service.SysRoleService;
import com.kivi.dashboard.permission.service.SysUserRoleService;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.cache.annotation.KtfCacheEvict;
import com.kivi.framework.cache.constant.KtfCache;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.constant.enums.KtfStatus;
import com.kivi.framework.converter.BeanConverter;
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
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

	@Autowired
	SysRoleExMapper					sysRoleExMapper;

	@Autowired
	SysResourceExMapper				sysResourceExMapper;

	@Autowired
	private SysRoleResourceService	roleResourceService;
	@Autowired
	private SysUserRoleService		userRoleService;

	/**
	 * 根据ID查询角色
	 */
	@KtfTrace("根据ID查询角色")
	@Override
	public SysRoleDTO getDto(Long id) {
		SysRoleDTO result = sysRoleExMapper.selectByRoleId(id);
		if (id.longValue() == KtfConstant.SUPER_ADMIN) {
			List<Long> resourceIds = sysResourceExMapper.selectResourceIds(new HashMap<String, Object>());
			result.setResourceIds(resourceIds);
		}

		return result;
	}

	/**
	 * 新增角色
	 */
	@KtfTrace("新增角色")
	@Override
	public Boolean save(SysRoleDTO dto) {
		// 检查权限是否越权
		checkPrems(dto);

		SysRole	entity	= BeanConverter.convert(SysRole.class, dto);
		boolean	ret		= super.save(entity);
		if (ret) {
			// 保存角色与菜单关系
			ret = roleResourceService.saveOrUpdateRoleResource(entity.getId(), dto.getResourceIds());
		}
		return ret;
	}

	/**
	 * 修改
	 */
	@KtfCacheEvict(cacheNames = { KtfCache.SysUser })
	@KtfTrace("修改角色")
	@Override
	public Boolean updateById(SysRoleDTO dto) {
		// 检查权限是否越权
		checkPrems(dto);
		SysRole	entity	= BeanConverter.convert(SysRole.class, dto);
		boolean	ret		= super.updateById(entity);

		if (ret) {
			// 保存角色与菜单关系
			ret = roleResourceService.saveOrUpdateRoleResource(entity.getId(), dto.getResourceIds());
		}
		return ret;
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询角色")
	public PageInfoVO<SysRoleDTO> page(Map<String, Object> params) {
		if (params == null) {
			params = new HashMap<>();
		}

		if (!params.containsKey(SysRoleDTO.STATUS))
			params.put(SysRoleDTO.STATUS, KtfStatus.ENABLED.code);

		PageParams<SysRoleDTO>	pageParams	= new PageParams<>(params);
		Page<SysRoleDTO>		page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		IPage<SysRoleDTO>		iPage		= sysRoleExMapper.selectRoles(page, pageParams.getRequestMap());

		PageInfoVO<SysRoleDTO>	pageVo		= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(iPage.getRecords());
		pageVo.compute();

		return pageVo;

	}

	/**
	 * 批量删除
	 * 
	 * @param roleIds
	 */
	@KtfCacheEvict(cacheNames = { KtfCache.SysUser })
	@Override
	public void deleteBatch(Long[] roleIds) {
		// 删除角色
		this.removeByIds(Arrays.asList(roleIds));

		// 删除角色与菜单关联
		roleResourceService.deleteBatchByRoleIds(roleIds);

		// 删除角色与用户关联
		userRoleService.deleteBatchByRoleIds(roleIds);
	}

	/**
	 * 检查权限是否越权
	 */
	private void checkPrems(SysRoleDTO role) {
		// 如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
		if (role.getCreateUserId() == KtfConstant.SUPER_ADMIN) {
			return;
		}
		// 查询用户所拥有的菜单列表
		// List<Long> resourceIdList =
		// sysResourceService.selectResourceIdListByUserId(role.getCreateUserId());
		// 判断是否越权
		// if (!resourceIdList.containsAll(role.getResourceIdList())) {
		// throw new KtfException("新增角色的权限，已超出你的权限范围");
		// }
	}

	/**
	 * 指定列查询列表
	 */
	/*
	 * @KtfTrace("指定列查询列表角色")
	 * 
	 * @Override public List<SysRoleDTO> list(Map<String, Object> params, String...
	 * columns) { if (params != null) params.remove(KtfConstant.URL_TIMESTAMP);
	 * QueryWrapper<SysRole> query =
	 * Wrappers.<SysRole>query().select(columns).allEq(true, params, false);
	 * List<SysRole> list = super.list(query); return
	 * BeanConverter.convert(SysRoleDTO.class, list); }
	 */

	/**
	 * 模糊查询
	 */
	/*
	 * @KtfTrace("模糊查询角色")
	 * 
	 * @Override public List<SysRoleDTO> listLike(SysRoleDTO applicationDTO) {
	 * Map<String, Object> params = BeanConverter.beanToMap(applicationDTO); return
	 * listLike(params, new String[0]); }
	 */

	/**
	 * 指定列模糊查询
	 */
	/*
	 * @Override public List<SysRoleDTO> listLike(Map<String, Object> params,
	 * String... columns) { if (params != null)
	 * params.remove(KtfConstant.URL_TIMESTAMP); QueryWrapper<SysRole> query =
	 * Wrappers.<SysRole>query().select(columns).orderByAsc(SysRole.DB_SEQ);
	 * 
	 * if (MapUtil.isNotEmpty(params)) { params.entrySet().stream().forEach(entry ->
	 * { if (ObjectKit.isNotEmpty(entry.getValue())) { if
	 * (NumberKit.isNumberic(entry.getValue())) query.eq(entry.getKey(),
	 * entry.getValue()); else query.like(entry.getKey(), entry.getValue()); } }); }
	 * 
	 * List<SysRole> list = super.list(query); return
	 * BeanConverter.convert(SysRoleDTO.class, list); }
	 */

	/*
	 * @Override public RoleVo selectByRoleId(Long roleId) { return
	 * sysRoleExMapper.selectByRoleId(roleId); }
	 */

}
