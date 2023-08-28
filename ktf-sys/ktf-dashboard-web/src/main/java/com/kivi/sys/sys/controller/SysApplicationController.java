package com.kivi.sys.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.google.common.collect.Maps;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.base.DashboardController;
import com.kivi.sys.sys.dto.SysApplicationDTO;
import com.vip.vjtools.vjkit.number.NumberUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 系统应用 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Api(tags = { "系统管理—应用管理" })
@ApiSupport(order = 33)
@RestController
@RequestMapping("/sys/application")
@Slf4j
public class SysApplicationController extends DashboardController {

	@ApiOperation(value = "系统应用信息", notes = "系统应用信息")
	@GetMapping("/info/{id}")
	@RequiresPermissions("sys/application/info")
	public ResultMap info(@PathVariable("id") String id) {
		SysApplicationDTO dto = sysApplicationService().getDTOById(NumberUtil.toLongObject(id, -1L));
		return ResultMap.ok().put("application", dto);
	}

	/**
	 * 新增
	 */
	@ApiOperation(value = "新增系统应用", notes = "新增系统应用")
	@RequiresPermissions("sys/application/save")
	@PostMapping("/save")
	public ResultMap save(@Valid @RequestBody SysApplicationDTO sysApplicationDTO) {
		try {
			// sysApplicationDTO.setCreateUser(ShiroKit.getUser().getLoginName());
			Boolean b = sysApplicationService().save(sysApplicationDTO);
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
	@ApiOperation(value = "修改系统应用", notes = "修改系统应用")
	@RequiresPermissions("sys/application/update")
	@PostMapping("/update")
	public ResultMap updateById(@RequestBody SysApplicationDTO sysApplicationDTO) {
		try {
			// sysApplicationDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
			Boolean b = sysApplicationService().updateById(sysApplicationDTO);
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
	@ApiOperation(value = "删除系统应用", notes = "删除系统应用")
	@GetMapping("/delete/{id}")
	@RequiresPermissions("sys/application/delete")
	public ResultMap delete(@PathVariable("id") Long id) {
		try {
			Boolean b = sysApplicationService().removeById(id);
			if (b) {
				return ResultMap.ok("删除成功！");
			} else {
				return ResultMap.ok("删除失败！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultMap.error("删除失败，请联系管理员");
		}
	}

	/**
	 * 批量删除
	 */
	@ApiOperation(value = "批量删除系统应用", notes = "删除系统应用")
	@PostMapping("/delete")
	@RequiresPermissions("sys/application/delete")
	public ResultMap deleteBatchIds(@RequestBody Long[] ids) {
		try {
			Boolean b = sysApplicationService().removeByIds(Arrays.asList(ids));
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
	 * 查询选择列表
	 */
	@ApiOperation(value = "查询选择列表", notes = "查询选择列表")
	@GetMapping("/select")
	public ResultMap select() {
		List<SysApplicationDTO> list = sysApplicationService().listLike(Maps.newHashMap(), SysApplicationDTO.ID,
				SysApplicationDTO.CODE, SysApplicationDTO.NAME);

		return ResultMap.ok().put("list", list);
	}

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "分页查询系统应用", notes = "分页查询修改系统应用")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "name",
					dataType = "string",
					value = "名称，可选，模糊匹配",
					paramType = "query",
					allowEmptyValue = true),
			@ApiImplicitParam(
					name = "page",
					dataType = "integer",
					value = "当前页，可选，默认值：1",
					paramType = "query",
					allowEmptyValue = true),
			@ApiImplicitParam(
					name = "limit",
					dataType = "integer",
					value = "每页大小，可选，默认值：10",
					paramType = "query",
					allowEmptyValue = true) })
	@RequiresPermissions("sys/application/page")
	@GetMapping("/page")
	public ResultMap page(@ApiIgnore @RequestParam(required = false) Map<String, Object> params) {
		PageInfoVO<SysApplicationDTO> page = sysApplicationService().page(params);

		return ResultMap.ok().data(page);
	}

}
