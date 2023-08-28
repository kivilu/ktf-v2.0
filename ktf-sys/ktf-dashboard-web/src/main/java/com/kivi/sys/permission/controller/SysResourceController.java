package com.kivi.sys.permission.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kivi.framework.constant.enums.CommonEnum;
import com.kivi.framework.constant.enums.CommonEnum.MenuType;
import com.kivi.framework.constant.enums.KtfStatus;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.properties.KtfSwaggerProperties;
import com.kivi.framework.util.kit.StrKit;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.base.DashboardController;
import com.kivi.sys.permission.dto.SysResourceDTO;
import com.kivi.sys.permission.entity.SysResource;
import com.kivi.sys.shiro.ShiroKit;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 资源 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@ConditionalOnProperty(
		prefix = KtfSwaggerProperties.PREFIX,
		value = "enable-permission-api",
		havingValue = "true",
		matchIfMissing = false)
@Api(value = "SYS资源管理接口", tags = { "SYS资源管理接口" })
@RestController
@RequestMapping("/permission/menu")
@Slf4j
public class SysResourceController extends DashboardController {

	@Autowired
	private KtfDashboardProperties ktfDashboardProperties;

	/**
	 * 导航菜单
	 */
	@ApiOperation(value = "导航菜单", notes = "导航菜单")
	@GetMapping("/nav")
	public ResultMap nav() {
		Long					userId		= ShiroKit.getUser().getId();
		List<SysResourceDTO>	menuList	= sysResourceService().selectResources(userId, MenuType.CATALOG,
				MenuType.MENU);
		return ResultMap.ok().data(menuList);
	}

	@ApiOperation(value = "资源信息", notes = "资源信息")
	@GetMapping("/info/{id}")
	@RequiresPermissions("permission/menu/info")
	public ResultMap info(@PathVariable("id") Long id) {
		SysResourceDTO dto = sysResourceService().getDto(id);
		return ResultMap.ok().data(dto);
	}

	/**
	 * 新增
	 */
	@ApiOperation(value = "新增资源", notes = "新增资源")
	@RequiresPermissions("permission/menu/save")
	@PostMapping("/save")
	public ResultMap save(@Valid @RequestBody SysResourceDTO dto) {
		try {
			// 数据校验
			verifyForm(dto);

			Boolean b = sysResourceService().save(dto);
			if (b) {
				// 清除缓存权限
				ShiroKit.clearAuth();
				return ResultMap.ok("新增成功！");
			} else {
				return ResultMap.ok("新增失败！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultMap.error("添加失败，请联系管理员");
		}
	}

	/**
	 * 修改
	 */
	@ApiOperation(value = "修改资源", notes = "修改资源")
	@PostMapping("/update")
	@RequiresPermissions("permission/menu/update")
	public ResultMap updateById(@RequestBody SysResourceDTO dto) {
		try {
			// 数据校验
			verifyForm(dto);
			Boolean b = sysResourceService().updateById(dto);
			if (b) {
				// 清除缓存权限
				ShiroKit.clearAuth();
				return ResultMap.ok("编辑成功！");
			} else {
				return ResultMap.ok("编辑失败！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultMap.error("编辑失败，请联系管理员");
		}
	}

	/**
	 * 删除
	 */
	@ApiOperation(value = "批量删除资源", notes = "删除资源")
	@GetMapping("/delete/{menuId}")
	@RequiresPermissions("permission/menu/delete")
	public ResultMap delete(@PathVariable("menuId") Long menuId) {
		try {
			if (menuId <= ktfDashboardProperties.getMaxReserveMenuId()) {
				return ResultMap.error("系统菜单，不能删除");
			}

			Boolean b = sysResourceService().removeById(menuId);
			if (b) {
				// 清除缓存权限
				ShiroKit.clearAuth();
				return ResultMap.ok("删除成功！");
			} else {
				return ResultMap.ok("删除失败！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultMap.error("批量删除失败，请联系管理员");
		}
	}

	/**
	 * 批量删除
	 */
	@ApiOperation(value = "批量删除资源", notes = "删除资源")
	@PostMapping("/delete")
	@RequiresPermissions("permission/menu/delete")
	public ResultMap deleteBatchIds(@RequestBody Long[] ids) {
		try {
			Boolean b = sysResourceService().removeByIds(Arrays.asList(ids));
			if (b) {
				// 清除缓存权限
				ShiroKit.clearAuth();
				return ResultMap.ok("删除成功！");
			} else {
				return ResultMap.ok("删除失败！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultMap.error("批量删除失败，请联系管理员");
		}
	}

	/**
	 * 根据ID查询所属字典数据
	 */
	@ApiOperation(value = "根据ID查询所属菜单", notes = "根据ID查询所属菜单")
	@GetMapping("/getChildren/{id}/{isMenu}")
	@RequiresPermissions("permission/menu/getChildren")
	public ResultMap getChildren(@PathVariable("id") Long id, @PathVariable("isMenu") Boolean isMenu) {
		List<SysResourceDTO> list = sysResourceService().getChildren(id, isMenu);
		return ResultMap.ok().data(list);
	}

	/**
	 * 查询顶级菜单
	 */
	@ApiOperation(value = "查询顶级菜单", notes = "查询顶级菜单")
	@ApiImplicitParams({ @ApiImplicitParam(
			name = SysResourceDTO.RESOURCE_TYPE,
			dataType = "integer",
			value = "资源类别(0：菜单，1：按钮)",
			paramType = "query",
			allowEmptyValue = true) })
	@RequiresPermissions("permission/menu/tops")
	@GetMapping("/tops")
	public ResultMap tops(@ApiIgnore @RequestParam Map<String, Object> params) {
		PageInfoVO<SysResourceDTO> page = sysResourceService().tops(params);

		return ResultMap.ok().data(page);
	}

	/**
	 * 查询列表
	 */

	@ApiOperation(value = "查询菜单列表", notes = "查询菜单列表")
	@ApiImplicitParams({ @ApiImplicitParam(
			name = "resourceTypes",
			dataType = "string",
			value = "资源类型，多个类型使用逗号分隔",
			paramType = "query",
			allowEmptyValue = true) })
	@RequiresPermissions("permission/menu/list")
	@GetMapping("/list")
	public ResultMap list(@ApiIgnore @RequestParam Map<String, Object> params) {
		if (params != null) {
			String types = (String) params.remove("resourceTypes");
			if (StrKit.isNotBlank(types)) {
				params.put("resourceTypes", StrKit.split(types, ','));
			}

		} else {
			params = new HashMap<>();
		}
		params.put(SysResourceDTO.STATUS, KtfStatus.ENABLED.code);
		List<SysResourceDTO> list = sysResourceService().selectMenutList(params);
		return ResultMap.ok().data(list);
	}

	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysResourceDTO sysResource) {
		if (StringUtils.isBlank(sysResource.getName())) {
			throw new KtfException("菜单名称不能为空");
		}
		if (sysResource.getResourceType() != CommonEnum.MenuType.CATALOG.getValue()
				&& sysResource.getParentId() == null) {
			throw new KtfException("上级菜单不能为空");
		}
		// 菜单
		if (sysResource.getResourceType() == CommonEnum.MenuType.MENU.getValue()) {
			if (StringUtils.isBlank(sysResource.getUrl())) {
				throw new KtfException("菜单URL不能为空");
			}
		}
		// 上级菜单类型
		int parentType = CommonEnum.MenuType.CATALOG.getValue();
		if (sysResource.getParentId() != null && sysResource.getParentId() != 0) {
			SysResource parentMenu = sysResourceService().getById(sysResource.getParentId());
			parentType = parentMenu.getResourceType();
		}
		// 目录、菜单
		if (sysResource.getResourceType() == CommonEnum.MenuType.CATALOG.getValue()
				|| sysResource.getResourceType() == CommonEnum.MenuType.MENU.getValue()) {
			if (parentType != CommonEnum.MenuType.CATALOG.getValue()) {
				throw new KtfException("上级菜单只能为目录类型");
			}
			return;
		}

		// 按钮
		if (sysResource.getResourceType() == CommonEnum.MenuType.BUTTON.getValue()) {
			if (parentType != CommonEnum.MenuType.MENU.getValue()) {
				throw new KtfException("上级菜单只能为菜单类型");
			}
			return;
		}
	}

}
