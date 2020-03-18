package com.kivi.dashboard.sys.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.sys.dto.SysRoleDTO;
import com.kivi.dashboard.sys.entity.SysRole;
import com.kivi.dashboard.sys.mapper.SysRoleMapper;
import com.kivi.dashboard.sys.service.ISysRoleService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.vo.RoleVo;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@Service(version = KtfDashboardProperties.DUBBO_VERSION)
public class SysRoleDubboServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

	@Autowired
	ISysRoleService sysRoleService;

	/**
	 * 根据ID查询角色
	 */
	@KtfTrace("根据ID查询角色")
	@Override
	public SysRoleDTO getDTOById(Long id) {
		return sysRoleService.getDTOById(id);
	}

	/**
	 * 新增角色
	 */
	@KtfTrace("新增角色")
	@Override
	public Boolean save(SysRoleDTO sysRoleDTO) {
		return sysRoleService.save(sysRoleDTO);
	}

	@Override
	public Boolean saveByVo(SysRoleDTO sysRoleDTO) {
		// 保存角色与菜单关系
		return sysRoleService.saveByVo(sysRoleDTO);
	}

	@Override
	public Boolean updateByVo(SysRoleDTO sysRoleDTO) {
		return sysRoleService.updateByVo(sysRoleDTO);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改角色")
	@Override
	public Boolean updateById(SysRoleDTO sysRoleDTO) {
		return sysRoleService.updateById(sysRoleDTO);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表角色")
	@Override
	public List<SysRoleDTO> list(SysRoleDTO sysRoleDTO) {
		return sysRoleService.list(sysRoleDTO);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表角色")
	@Override
	public List<SysRoleDTO> list(Map<String, Object> params, String... columns) {
		return sysRoleService.list(params, columns);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询角色")
	@Override
	public List<SysRoleDTO> listLike(SysRoleDTO sysRoleDTO) {
		return sysRoleService.listLike(sysRoleDTO);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysRoleDTO> listLike(Map<String, Object> params, String... columns) {
		return sysRoleService.listLike(params, columns);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询角色")
	public PageInfoVO<SysRoleDTO> page(Map<String, Object> params) {
		return sysRoleService.page(params);

	}

	@Override
	public RoleVo selectByRoleId(Long roleId) {
		return sysRoleService.selectByRoleId(roleId);
	}

	/**
	 * 批量删除
	 * 
	 * @param roleIds
	 */
	@Override
	public void deleteBatch(Long[] roleIds) {
		sysRoleService.deleteBatch(roleIds);
	}

}
