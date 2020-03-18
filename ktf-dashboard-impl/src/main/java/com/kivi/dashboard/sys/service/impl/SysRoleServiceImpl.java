package com.kivi.dashboard.sys.service.impl;

import java.util.Arrays;
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
import com.kivi.dashboard.sys.mapper.SysRoleExMapper;
import com.kivi.dashboard.sys.mapper.SysRoleMapper;
import com.kivi.dashboard.sys.service.ISysRoleResourceService;
import com.kivi.dashboard.sys.service.ISysRoleService;
import com.kivi.dashboard.sys.service.ISysUserRoleService;
import com.kivi.dashboard.sys.service.ISysUserService;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.vo.RoleVo;
import com.kivi.framework.vo.page.PageInfoVO;
import com.vip.vjtools.vjkit.collection.MapUtil;

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
	SysRoleExMapper					sysRoleExMapper;

	@Autowired
	private ISysUserService			sysUserService;

	@Autowired
	private ISysRoleResourceService	roleResourceService;
	@Autowired
	private ISysUserRoleService		userRoleService;

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
		SysRole	entity	= BeanConverter.convert(SysRole.class, sysRoleDTO);
		boolean	ret		= super.save(entity);
		sysRoleDTO.setId(entity.getId());
		return ret;
	}

	@Override
	public Boolean saveByVo(SysRoleDTO sysRoleDTO) {
		this.save(sysRoleDTO);
		// 检查权限是否越权
		checkPrems(sysRoleDTO);
		// 保存角色与菜单关系
		return roleResourceService.saveOrUpdateRoleResource(sysRoleDTO.getId(), sysRoleDTO.getResourceIdList());
	}

	@Override
	public Boolean updateByVo(SysRoleDTO sysRoleDTO) {
		this.updateById(sysRoleDTO);
		// 检查权限是否越权
		checkPrems(sysRoleDTO);
		// 更新角色与菜单关系
		return roleResourceService.saveOrUpdateRoleResource(sysRoleDTO.getId(), sysRoleDTO.getResourceIdList());
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
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
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
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysRole> query = Wrappers.<SysRole>query().select(columns).orderByAsc(SysRole.DB_SEQ);

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

		List<SysRole> list = super.list(query);
		return BeanConverter.convert(SysRoleDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询角色")
	public PageInfoVO<SysRoleDTO> page(Map<String, Object> params) {
		PageParams<SysRoleDTO>	pageParams	= new PageParams<>(params);
		Page<SysRole>			page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<SysRole>	query		= Wrappers.<SysRole>query();
		pageParams.getRequestMap().entrySet().stream().forEach(entry -> {
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

	/**
	 * 批量删除
	 * 
	 * @param roleIds
	 */
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
		List<Long> resourceIdList = sysUserService.selectResourceIdListByUserId(role.getCreateUserId());
		// 判断是否越权
		if (!resourceIdList.containsAll(role.getResourceIdList())) {
			throw new KtfException("新增角色的权限，已超出你的权限范围");
		}
	}

}
