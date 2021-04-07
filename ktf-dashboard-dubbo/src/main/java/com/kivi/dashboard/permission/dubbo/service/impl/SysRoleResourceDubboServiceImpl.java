package com.kivi.dashboard.permission.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.permission.dto.SysRoleResourceDTO;
import com.kivi.dashboard.permission.entity.SysRoleResource;
import com.kivi.dashboard.permission.mapper.SysRoleResourceMapper;
import com.kivi.dashboard.permission.service.SysRoleResourceService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 角色资源 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@DubboService(version = KtfDashboardProperties.DUBBO_VERSION)
public class SysRoleResourceDubboServiceImpl extends ServiceImpl<SysRoleResourceMapper, SysRoleResource>
		implements SysRoleResourceService {
	@Autowired
	private SysRoleResourceService sysRoleResourceService;

	/**
	 * 根据ID查询角色资源
	 */
	@KtfTrace("根据ID查询角色资源")
	@Override
	public SysRoleResourceDTO getDTOById(Long id) {

		return sysRoleResourceService.getDTOById(id);
	}

	/**
	 * 新增角色资源
	 */
	@KtfTrace("新增角色资源")
	@Override
	public Boolean save(SysRoleResourceDTO sysRoleResourceDTO) {
		return sysRoleResourceService.save(sysRoleResourceDTO);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改角色资源")
	@Override
	public Boolean updateById(SysRoleResourceDTO sysRoleResourceDTO) {
		return sysRoleResourceService.updateById(sysRoleResourceDTO);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表角色资源")
	@Override
	public List<SysRoleResourceDTO> list(SysRoleResourceDTO sysRoleResourceDTO) {
		return sysRoleResourceService.list(sysRoleResourceDTO);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表角色资源")
	@Override
	public List<SysRoleResourceDTO> list(Map<String, Object> params, String... columns) {
		return sysRoleResourceService.list(params, columns);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询角色资源")
	@Override
	public List<SysRoleResourceDTO> listLike(SysRoleResourceDTO sysRoleResourceDTO) {
		return sysRoleResourceService.listLike(sysRoleResourceDTO);
	}

	/**
	 * 指定列模糊查询
	 */
	@KtfTrace("指定列模糊查询")
	@Override
	public List<SysRoleResourceDTO> listLike(Map<String, Object> params, String... columns) {
		return sysRoleResourceService.listLike(params, columns);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询角色资源")
	public PageInfoVO<SysRoleResourceDTO> page(Map<String, Object> params) {
		return sysRoleResourceService.page(params);

	}

	@Override
	public List<Long> selectResourceIdListByRoleId(Long roleId) {
		return sysRoleResourceService.selectResourceIdListByRoleId(roleId);
	}

	@Override
	public List<SysRoleResourceDTO> selectResourceNodeListByRoleId(Long roleId) {
		return sysRoleResourceService.selectResourceNodeListByRoleId(roleId);
	}

	@Override
	public Boolean saveOrUpdateRoleResource(Long roleId, List<Long> resourceIdList) {
		return sysRoleResourceService.saveOrUpdateRoleResource(roleId, resourceIdList);
	}

	@Override
	public int deleteBatchByRoleIds(Long[] roleIds) {
		return sysRoleResourceService.deleteBatchByRoleIds(roleIds);
	}

}
