package com.kivi.dashboard.org.controller;

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

import com.kivi.dashboard.base.DashboardController;
import com.kivi.dashboard.org.dto.OrgDeptDTO;
import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.shiro.ShiroUser;
import com.kivi.framework.constant.enums.UserType;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.vo.page.PageInfoVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 企业部门 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Api(tags = { "企业部门" })
@RestController
@RequestMapping("/org/dept")
@Slf4j
public class OrgDeptController extends DashboardController {

	@ApiOperation(value = "企业部门信息", notes = "企业部门信息")
	@GetMapping("/info/{id}")
	@RequiresPermissions("org/dept/info")
	public ResultMap info(@PathVariable("id") Long id) {
		OrgDeptDTO dto = orgDeptService().getDto(id);
		return ResultMap.ok().data(dto);
	}

	/**
	 * 新增
	 */
	@ApiOperation(value = "新增企业部门", notes = "新增企业部门")
	@RequiresPermissions("org/dept/save")
	@PostMapping("/save")
	public ResultMap save(@Valid @RequestBody OrgDeptDTO dto) {
		try {
			Boolean b = orgDeptService().save(dto);
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
	@ApiOperation(value = "修改企业部门", notes = "修改企业部门")
	@RequiresPermissions("org/dept/update")
	@PostMapping("/update")
	public ResultMap updateById(@RequestBody OrgDeptDTO dto) {
		try {
			Boolean b = orgDeptService().updateById(dto);
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
	@ApiOperation(value = "删除企业部门", notes = "删除企业部门")
	@GetMapping("/delete/{id}")
	@RequiresPermissions("org/dept/delete")
	public ResultMap delete(@PathVariable("id") Long id) {
		try {
			Boolean b = orgDeptService().removeRecursion(id);
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
	@ApiOperation(value = "批量删除企业部门", notes = "删除企业部门")
	@PostMapping("/delete")
	@RequiresPermissions("org/dept/delete")
	public ResultMap deleteBatchIds(@RequestBody Long[] ids) {
		try {
			Boolean b = orgDeptService().removeRecursion(ids);
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
	 * 根据ID查询下级部门
	 */
	@ApiOperation(value = "根据ID查询下级部门", notes = "根据ID查询下级部门")
	@GetMapping("/getChildren/{id}")
	@RequiresPermissions("org/dept/getChildren")
	public ResultMap getChildren(@PathVariable("id") Long id) {
		List<OrgDeptDTO> list = orgDeptService().getChildren(id);
		return ResultMap.ok().data(list);
	}

	/**
	 * 根据企业ID查询部门
	 */
	@ApiOperation(value = "查询企业部门", notes = "查询企业部门")
	@GetMapping("/listByCorp/{corpId}")
	@RequiresPermissions("org/dept/listByCorp")
	public ResultMap listByCorp(@PathVariable("corpId") Long corpId) {
		List<OrgDeptDTO> list = orgDeptService().listByCorp(corpId);
		return ResultMap.ok().data(list);
	}

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@RequiresPermissions("org/dept/page")
	@GetMapping("/page")
	public ResultMap page(@RequestParam(required = false) Map<String, Object> params) {
		ShiroUser shiroUser = ShiroKit.getUser();
		// 不是管理员
		if (UserType.isSYS(shiroUser.getUserType())) {
			params.put(OrgDeptDTO.CORP_ID, ShiroKit.getUser().getId());
		}

		PageInfoVO<OrgDeptDTO> page = orgDeptService().page(params);
		return ResultMap.ok().data(page);
	}

}
