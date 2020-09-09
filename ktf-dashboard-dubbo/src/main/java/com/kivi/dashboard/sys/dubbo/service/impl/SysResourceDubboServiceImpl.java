package com.kivi.dashboard.sys.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.sys.dto.SysResourceDTO;
import com.kivi.dashboard.sys.entity.SysResource;
import com.kivi.dashboard.sys.mapper.SysResourceMapper;
import com.kivi.dashboard.sys.service.ISysResourceService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.constant.enums.CommonEnum.MenuType;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.vo.ResourceVo;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 资源 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Service(version = KtfDashboardProperties.DUBBO_VERSION)
public class SysResourceDubboServiceImpl extends ServiceImpl<SysResourceMapper, SysResource>
		implements ISysResourceService {
	@Autowired
	private ISysResourceService sysResourceService;

	/**
	 * 根据ID查询资源
	 */
	@KtfTrace("根据ID查询资源")
	@Override
	public SysResourceDTO getDTOById(Long id) {
		return sysResourceService.getDTOById(id);
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

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表资源")
	@Override
	public List<SysResource> list(Map<String, Object> params, String... columns) {
		return sysResourceService.list(params, columns);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询资源")
	public PageInfoVO<SysResourceDTO> page(Map<String, Object> params) {
		return sysResourceService.page(params);

	}

	@KtfTrace("根据用户ID查询对应的资源列表")
	@Override
	public List<ResourceVo> selectUserResourceListByUserId(Long userId, MenuType... types) {
		return sysResourceService.selectUserResourceListByUserId(userId, types);
	}

	@KtfTrace("查询指定ParentId的菜单列表")
	@Override
	public List<SysResource> selectListByParentId(Long parentId, List<Long> menuIdList) {
		return sysResourceService.selectListByParentId(parentId, menuIdList);
	}

	@KtfTrace("根据ParentId查询菜单")
	@Override
	public List<SysResource> selectListByParentId(Long parentId) {
		return sysResourceService.selectListByParentId(parentId);
	}

	@KtfTrace("根据条件查询菜单列表")
	@Override
	public List<ResourceVo> selectResourceList(Map<String, Object> params) {
		return this.sysResourceService.selectResourceList(params);
	}

	@Override
	public List<ResourceVo> selectNotButtonList() {
		return sysResourceService.selectNotButtonList();
	}

	@Override
	public List<Long> selectResourceIdListByUserId(Long userId) {
		return sysResourceService.selectResourceIdListByUserId(userId);
	}

	@Override
	public List<ResourceVo> selectMenuTreeList(Map<String, Object> params) {
		return sysResourceService.selectMenuTreeList(params);
	}

}
