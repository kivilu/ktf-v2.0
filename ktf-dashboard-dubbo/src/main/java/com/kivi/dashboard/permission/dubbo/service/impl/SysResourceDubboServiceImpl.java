package com.kivi.dashboard.permission.dubbo.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.permission.dto.SysResourceDTO;
import com.kivi.dashboard.permission.entity.SysResource;
import com.kivi.dashboard.permission.mapper.SysResourceMapper;
import com.kivi.dashboard.permission.service.SysResourceService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.constant.enums.CommonEnum.MenuType;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 资源 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@DubboService(version = KtfDashboardProperties.DUBBO_VERSION)
public class SysResourceDubboServiceImpl extends ServiceImpl<SysResourceMapper, SysResource>
		implements SysResourceService {
	@Autowired
	private SysResourceService sysResourceService;

	/**
	 * 根据ID查询资源
	 */
	@KtfTrace("根据ID查询资源")
	@Override
	public SysResourceDTO getDto(Long id) {
		return sysResourceService.getDto(id);
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
		return sysResourceService.updateById(sysResourceDTO);
	}

	@Override
	public List<SysResourceDTO> selectResources(Long userId, MenuType... types) {
		return sysResourceService.selectResources(userId, types);
	}

	@Override
	public List<SysResourceDTO> getChildren(Long id, Boolean isMenu) {
		return sysResourceService.getChildren(id, isMenu);
	}

	@Override
	public PageInfoVO<SysResourceDTO> tops(Map<String, Object> params) {
		return sysResourceService.tops(params);
	}

	@Override
	public Set<String> getPermissions(List<Long> roleIds) {
		return sysResourceService.getPermissions(roleIds);
	}

	@Override
	public List<SysResourceDTO> selectMenutList(Map<String, Object> params) {
		return sysResourceService.selectMenutList(params);
	}

}
