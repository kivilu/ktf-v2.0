package com.kivi.dashboard.enterprise.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kivi.dashboard.base.DashboardController;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.enterprise.dto.EnterpriseDepartmentDTO;
import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.shiro.ShiroUser;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.model.SelectTreeNode;
import com.vip.vjtools.vjkit.number.NumberUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
//@ConditionalOnMissingBean(name = { "ktfDubboProperties" })
@Api(tags = { "企业部门" })
@RestController
@RequestMapping("/enterprise/enterpriseDepartment")
@Slf4j
public class EnterpriseDepartmentController extends DashboardController {

	@ApiOperation(value = "企业部门信息", notes = "企业部门信息")
	@GetMapping("/info/{id}")
	@RequiresPermissions("enterprise/enterpriseDepartment/info")
	public ResultMap info(@PathVariable("id") String id) {
		EnterpriseDepartmentDTO dto = enterpriseDepartmentService().getDTOById(NumberUtil.toLongObject(id, -1L));
		return ResultMap.ok().put("enterpriseDepartment", dto);
	}

	/**
	 * 新增
	 */
	@ApiOperation(value = "新增企业部门", notes = "新增企业部门")
	@RequiresPermissions("enterprise/enterpriseDepartment/save")
	@PostMapping("/save")
	public ResultMap save(@Valid @RequestBody EnterpriseDepartmentDTO enterpriseDepartmentDTO) {
		try {
			// enterpriseDepartmentDTO.setCreateUser(ShiroKit.getUser().getLoginName());
			Boolean b = enterpriseDepartmentService().save(enterpriseDepartmentDTO);
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
	@RequiresPermissions("enterprise/enterpriseDepartment/update")
	@PostMapping("/update")
	public ResultMap updateById(@RequestBody EnterpriseDepartmentDTO enterpriseDepartmentDTO) {
		try {
			// enterpriseDepartmentDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
			Boolean b = enterpriseDepartmentService().updateById(enterpriseDepartmentDTO);
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
	 * 批量删除
	 */
	@ApiOperation(value = "批量删除企业部门", notes = "删除企业部门")
	@PostMapping("/delete")
	@RequiresPermissions("enterprise/enterpriseDepartment/delete")
	public ResultMap deleteBatchIds(@RequestBody Long[] ids) {
		try {
			Boolean b = enterpriseDepartmentService().removeByIds(Arrays.asList(ids));
			b = enterpriseDepartmentService().removeByParentIds(ids);
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
	@ApiOperation(value = "修改企业部门", notes = "修改企业部门")
	@RequiresPermissions("enterprise/enterpriseDepartment/list")
	@GetMapping("/list")
	public ResultMap list(@RequestParam(required = false) Map<String, Object> params) {
		ShiroUser shiroUser = ShiroKit.getUser();
		// 不是管理员
		if (shiroUser.getUserType() != 0) {
			params.put("userId", ShiroKit.getUser().getId());
		}

		List<Map<String, Object>> list = enterpriseDepartmentService().selectTreeGrid(params);
		return ResultMap.ok().put("list", list);
	}

	/**
	 * 企业部门树形选择
	 *
	 * @param enterpriseId
	 * @return
	 */
	@ApiOperation(value = "企业部门树形选择", notes = "企业部门树形选择")
	@ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true, dataType = "String", paramType = "query")
	@GetMapping("/getDeptSelectTree")
	public ResultMap getDeptSelectTree(@RequestParam String enterpriseId) {
		try {
			List<SelectTreeNode>	treeNodeList	= Lists.newArrayList();
			Map<String, Object>		params			= Maps.newHashMap();
			if (StringUtils.isNotBlank(enterpriseId)) {
				params.put("enterpriseId", enterpriseId);
			}
			List<EnterpriseDepartmentDTO> departmentList = enterpriseDepartmentService()
					.selectEnterpriseDepartmentList(params);
			if (!departmentList.isEmpty()) {
				departmentList.forEach(dept -> {
					SelectTreeNode selectTreeNode = new SelectTreeNode();
					selectTreeNode.setId(dept.getId().toString());
					selectTreeNode.setName(dept.getDepartmentName());
					selectTreeNode.setParentId(dept.getParentId().toString());
					treeNodeList.add(selectTreeNode);
				});
			}
			treeNodeList.add(SelectTreeNode.createParent());
			return ResultMap.ok().put("list", treeNodeList);
		} catch (Exception e) {
			log.error("企业部门树形选择异常", e);
			return ResultMap.error("运行异常，请联系管理员");
		}
	}

}
