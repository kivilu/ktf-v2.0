package com.kivi.dashboard.permission.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.permission.dto.SysUserRoleDTO;
import com.kivi.dashboard.permission.entity.SysUserRole;
import com.kivi.dashboard.permission.mapper.SysUserRoleMapper;
import com.kivi.dashboard.permission.service.SysUserRoleService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 用户角色 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@DubboService(version = KtfDashboardProperties.DUBBO_VERSION)
public class SysUserRoleDubboServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>
		implements SysUserRoleService {

	@Autowired
	private SysUserRoleService iSysUserRoleService;

	/**
	 * 根据ID查询用户角色
	 */
	@KtfTrace("根据ID查询用户角色")
	@Override
	public SysUserRoleDTO getDTOById(Long id) {
		return iSysUserRoleService.getDTOById(id);
	}

	/**
	 * 新增用户角色
	 */
	@KtfTrace("新增用户角色")
	@Override
	public Boolean save(SysUserRoleDTO sysUserRoleDTO) {
		return iSysUserRoleService.save(sysUserRoleDTO);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改用户角色")
	@Override
	public Boolean updateById(SysUserRoleDTO sysUserRoleDTO) {
		return iSysUserRoleService.updateById(sysUserRoleDTO);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表用户角色")
	@Override
	public List<SysUserRoleDTO> list(SysUserRoleDTO sysUserRoleDTO) {
		return iSysUserRoleService.list(sysUserRoleDTO);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表用户角色")
	@Override
	public List<SysUserRoleDTO> list(Map<String, Object> params, String... columns) {
		return iSysUserRoleService.list(params, columns);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询用户角色")
	@Override
	public List<SysUserRoleDTO> listLike(SysUserRoleDTO sysUserRoleDTO) {
		return iSysUserRoleService.listLike(sysUserRoleDTO);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysUserRoleDTO> listLike(Map<String, Object> params, String... columns) {
		return iSysUserRoleService.listLike(params, columns);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询用户角色")
	public PageInfoVO<SysUserRoleDTO> page(Map<String, Object> params) {
		return iSysUserRoleService.page(params);

	}

	@Override
	public Boolean deleteBatchByRoleIds(Long[] roleIds) {
		return iSysUserRoleService.deleteBatchByRoleIds(roleIds);

	}

	@Override
	public Boolean deleteBatchByUserIds(Long[] userIds) {
		return iSysUserRoleService.deleteBatchByUserIds(userIds);
	}

	@Override
	public List<Long> selectRoleIdListByUserId(Long userId) {
		return iSysUserRoleService.selectRoleIdListByUserId(userId);
	}

	@Override
	public void saveOrUpdateUserRole(Long userId, List<Long> roleIdList) {
		iSysUserRoleService.saveOrUpdateUserRole(userId, roleIdList);
	}

}
