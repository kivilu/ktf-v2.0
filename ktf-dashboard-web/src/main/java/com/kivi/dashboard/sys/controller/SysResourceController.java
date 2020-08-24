package com.kivi.dashboard.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.kivi.dashboard.base.DashboardController;
import com.kivi.dashboard.shiro.ShiroKit;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.sys.dto.SysResourceDTO;
import com.kivi.dashboard.sys.entity.SysResource;
import com.kivi.framework.constant.enums.CommonEnum;
import com.kivi.framework.constant.enums.CommonEnum.MenuType;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.StrKit;
import com.kivi.framework.vo.ResourceVo;
import com.kivi.framework.vo.page.PageInfoVO;
import com.vip.vjtools.vjkit.number.NumberUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 资源 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@Api(value = "资源管理接口", tags = { "资源管理接口" })
@RestController
@RequestMapping("/sys/menu")
@Slf4j
public class SysResourceController extends DashboardController {

	/**
	 * 导航菜单
	 */
	@ApiOperation(value = "导航菜单", notes = "导航菜单")
	@GetMapping("/nav")
	public ResultMap nav() {
		Long				userId		= ShiroKit.getUser().getId();
		List<ResourceVo>	menuList	= sysResourceService().selectUserResourceListByUserId(userId, MenuType.CATALOG,
				MenuType.MENU);
		Set<String>			permissions	= sysUserService().selectUserPermissions(userId);
		return ResultMap.ok().put("menuList", menuList).put("permissions", permissions);
	}

	@ApiOperation(value = "资源信息", notes = "资源信息")
	@GetMapping("/info/{id}")
	@RequiresPermissions("sys/menu/info")
	public ResultMap info(@PathVariable("id") String id) {
		SysResourceDTO dto = sysResourceService().getDTOById(NumberUtil.toLongObject(id, -1L));
		return ResultMap.ok().put("menu", dto);
	}

	/**
	 * 新增
	 */
	@ApiOperation(value = "新增资源", notes = "新增资源")
	@RequiresPermissions("sys/menu/save")
	@PostMapping("/save")
	public ResultMap save(@Valid @RequestBody SysResourceDTO sysResourceDTO) {
		try {
			// 数据校验
			verifyForm(sysResourceDTO);

			Boolean b = sysResourceService().save(sysResourceDTO);
			if (b) {
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
	@RequiresPermissions("sys/menu/update")
	@PostMapping("/update")
	public ResultMap updateById(@RequestBody SysResourceDTO sysResourceDTO) {
		try {
			// 数据校验
			verifyForm(sysResourceDTO);
			Boolean b = sysResourceService().updateById(sysResourceDTO);
			if (b) {
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
	@PostMapping("/delete/{menuId}")
	@RequiresPermissions("sys/menu/delete")
	public ResultMap delete(@PathVariable("menuId") Long menuId) {
		try {
			if (menuId <= 31) {
				return ResultMap.error("系统菜单，不能删除");
			}

			// 判断是否有子菜单或按钮
			List<SysResource> menuList = sysResourceService().selectListByParentId(menuId);
			if (menuList.size() > 0) {
				return ResultMap.error("请先删除子菜单或按钮");
			}

			Boolean b = sysResourceService().removeById(menuId);
			if (b) {
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
	@RequiresPermissions("sys/menu/delete")
	public ResultMap deleteBatchIds(@RequestBody Long[] ids) {
		try {
			Boolean b = sysResourceService().removeByIds(Arrays.asList(ids));
			if (b) {
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
	 * 查询列表
	 */
	@ApiOperation(value = "查询菜单列表", notes = "查询菜单列表")
	@RequiresPermissions("sys/menu/list")
	@GetMapping("/list")
	public ResultMap listAll() {
		List<ResourceVo> list = sysResourceService().selectResourceList(Maps.newHashMap());
		return ResultMap.ok().put("list", list);
	}

	/**
	 * 查询列表
	 */
	@ApiOperation(value = "查询菜单列表", notes = "查询菜单列表")
	@RequiresPermissions("sys/menu/list")
	@GetMapping("/list/{status}")
	public ResultMap menuTree(@PathVariable("status") String status) {
		Map<String, Object> params = Maps.newHashMap();
		if (!StrKit.equalsIgnoreCase("all", status))
			params.put("status", NumberKit.toInt(status));

		List<ResourceVo> list = sysResourceService().selectMenuTreeList(params);
		return ResultMap.ok().put("list", list);
	}

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "修改资源", notes = "修改资源")
	@RequiresPermissions("sys/menu/page")
	@GetMapping("/page")
	public ResultMap page(@RequestParam Map<String, Object> params) {
		PageInfoVO<SysResourceDTO> page = sysResourceService().page(params);

		return ResultMap.ok().put("page", page);
	}

	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@ApiOperation(value = "选择菜单(添加、修改菜单)", notes = "选择菜单(添加、修改菜单)")
	@GetMapping("/select")
	@RequiresPermissions("sys/menu/select")
	public ResultMap select() {
		// 查询列表数据
		List<ResourceVo>	menuList	= sysResourceService().selectNotButtonList();
		// 添加顶级菜单
		ResourceVo			root		= new ResourceVo();
		root.setId(0L);
		root.setName("顶级菜单");
		root.setParentId(0L);
		root.setOpen(true);
		menuList.add(root);
		return ResultMap.ok().put("menuList", menuList);
	}

	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysResourceDTO sysResource) {
		if (StringUtils.isBlank(sysResource.getName())) {
			throw new KtfException("菜单名称不能为空");
		}
		if (sysResource.getParentId() == null) {
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
		if (sysResource.getParentId() != 0) {
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
