package com.kivi.dashboard.org.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kivi.dashboard.base.DashboardController;
import com.kivi.dashboard.org.dto.OrgDeptDTO;
import com.kivi.dashboard.org.dto.OrgTitleDTO;
import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.shiro.ShiroUser;
import com.kivi.framework.constant.enums.UserType;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.properties.KtfSwaggerProperties;
import com.kivi.framework.vo.page.PageInfoVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 企业职务配置 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@ConditionalOnProperty(
		prefix = KtfSwaggerProperties.PREFIX,
		value = "enable-org-api",
		havingValue = "true",
		matchIfMissing = false)
@Api(tags = { "企业职务配置" })
@RestController
@RequestMapping("/org/title")
@Slf4j
public class OrgTitleController extends DashboardController {

	@ApiOperation(value = "企业职务配置信息", notes = "企业职务配置信息")
	@GetMapping("/info/{id}")
	@RequiresPermissions("org/title/info")
	public ResultMap info(@PathVariable("id") Long id) {
		OrgTitleDTO dto = orgTitleService().getDto(id);
		return ResultMap.ok().data(dto);
	}

	/**
	 * 新增
	 */
	@ApiOperation(value = "新增企业职务配置", notes = "新增企业职务配置")
	@RequiresPermissions("org/title/save")
	@PostMapping("/save")
	public ResultMap save(@Valid @RequestBody OrgTitleDTO dto) {
		try {
			// dto.setCreateUser(ShiroKit.getUser().getLoginName());
			Boolean b = orgTitleService().save(dto);
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
	@ApiOperation(value = "修改企业职务配置", notes = "修改企业职务配置")
	@RequiresPermissions("org/title/update")
	@PostMapping("/update")
	public ResultMap updateById(@RequestBody OrgTitleDTO dto) {
		try {
			// dto.setUpdateUser(ShiroKit.getUser().getLoginName());
			Boolean b = orgTitleService().updateById(dto);
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
	@ApiOperation(value = "删除企业职务配置", notes = "删除企业职务配置")
	@PostMapping("/delete/{id}")
	@RequiresPermissions("org/title/delete")
	public ResultMap delete(@PathVariable("id") Long id) {
		try {
			Boolean b = orgTitleService().removeById(id);
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
	@ApiOperation(value = "批量删除企业职务配置", notes = "批量删除企业职务配置")
	@PostMapping("/delete")
	@RequiresPermissions("org/title/delete")
	public ResultMap deleteBatchIds(@RequestBody Long[] ids) {
		try {
			Boolean b = orgTitleService().removeByIds(Arrays.asList(ids));
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
	@ApiOperation(value = "查询列表", notes = "查询列表")
	@RequiresPermissions("org/title/list")
	@GetMapping("/list")
	public ResultMap list(@RequestParam(required = false) Map<String, Object> params) {
		List<OrgTitleDTO> list = orgTitleService().list(params);
		return ResultMap.ok().data(list);
	}

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@RequiresPermissions("org/title/page")
	@GetMapping("/page")
	public ResultMap page(@RequestParam(required = false) Map<String, Object> params) {
		ShiroUser shiroUser = ShiroKit.getUser();
		// 不是管理员
		if (UserType.isSYS(shiroUser.getUserType())) {
			params.put(OrgDeptDTO.CORP_ID, ShiroKit.getUser().getId());
		}

		PageInfoVO<OrgTitleDTO> page = orgTitleService().page(params);
		return ResultMap.ok().data(page);
	}

}
