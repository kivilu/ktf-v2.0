package com.kivi.dashboard.sys.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kivi.dashboard.base.DashboardController;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.sys.dto.SysDicDTO;
import com.kivi.dashboard.sys.entity.SysDic;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.model.SelectNode;
import com.kivi.framework.model.SelectTreeNode;
import com.kivi.framework.util.kit.StrKit;
import com.kivi.framework.vo.page.PageInfoVO;
import com.vip.vjtools.vjkit.collection.ListUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@Api(value = "数据字典管理接口", tags = { "数据字典管理接口" })
@RestController
@RequestMapping("/sys/dic")
@Slf4j
public class SysDicController extends DashboardController {

	@ApiOperation(value = "数据字典信息", notes = "数据字典信息")
	@GetMapping("/info/{id}")
	@RequiresPermissions("sys/dic/info")
	public ResultMap info(@PathVariable("id") Long id) {
		SysDicDTO dto = sysDicService().getDTOById(id);

		return ResultMap.ok().put("dic", dto);
	}

	/**
	 * 新增
	 */
	@ApiOperation(value = "新增数据字典", notes = "新增数据字典")
	@RequiresPermissions("sys/dic/save")
	@PostMapping("/save")
	public ResultMap save(@Valid @RequestBody SysDicDTO sysDicDTO) {
		try {
			// sysDicDTO.setCreateUser(ShiroKit.getUser().getLoginName());
			Boolean b = sysDicService().save(sysDicDTO);
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
	@ApiOperation(value = "修改数据字典", notes = "修改数据字典")
	@RequiresPermissions("sys/dic/update")
	@PostMapping("/update")
	public ResultMap updateById(@RequestBody SysDicDTO sysDicDTO) {
		try {
			// sysDicDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
			Boolean b = sysDicService().updateById(sysDicDTO);
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
	@ApiOperation(value = "删除数据字典", notes = "删除数据字典")
	@PostMapping("/delete/{id}")
	@RequiresPermissions("sys/dic/delete")
	public ResultMap delete(@PathVariable("id") Long id) {
		try {
			Boolean b = sysDicService().deleteWithChild(id);
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
	@ApiOperation(value = "批量删除数据字典", notes = "删除数据字典")
	@PostMapping("/delete")
	@RequiresPermissions("sys/dic/delete")
	public ResultMap deleteBatchIds(@RequestBody Long[] ids) {
		try {
			List<Long> idList = new ArrayList<Long>();
			Collections.addAll(idList, ids);
			if (ListUtil.isEmpty(idList)) {
				sysDicService().removeByIds(Arrays.asList(ids));

				idList.stream().forEach(id -> {
					QueryWrapper<SysDic> wrapper = new QueryWrapper<>();
					wrapper.eq(SysDic.DB_PARENT_ID, id);
					sysDicService().remove(wrapper);
				});

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
	@RequiresPermissions("sys/dic/list")
	@GetMapping("/list")
	public ResultMap
			list(@RequestParam(required = false) String dicName, @RequestParam(required = false) String dicCode) {

		Map<String, Object> par = new HashMap<>();
		if (StringUtils.isNotBlank(dicName)) {
			par.put(SysDicDTO.VAR_NAME, StringUtils.deleteWhitespace(dicName));
		}
		if (StringUtils.isNotBlank(dicCode)) {
			par.put(SysDicDTO.VAR_CODE, StringUtils.deleteWhitespace(dicCode));
		}

		List<SysDicDTO> list = sysDicService().list(par, StrKit.emptyArray());
		return ResultMap.ok().put("list", list);
	}

	/**
	 * 选择字典（添加、修改）
	 *
	 * @return
	 */
	@ApiOperation(value = "选择字典（添加、修改）", notes = "选择字典（添加、修改）")
	@GetMapping("/select/{parentId}")
	public ResultMap select(@PathVariable("parentId") Long parentId) {
		Map<String, Object> params = Maps.newHashMap();
		if (parentId != null && 0 != parentId) {
			params.put(SysDicDTO.PARENT_ID, parentId);
		}
		List<SysDicDTO>			dicList			= sysDicService().list(params, SysDic.DB_ID, SysDic.DB_PARENT_ID,
				SysDic.DB_VAR_CODE, SysDic.DB_VAR_NAME);
		List<SelectTreeNode>	treeNodeList	= Lists.newArrayList();
		if (!dicList.isEmpty()) {
			dicList.forEach(dic -> {
				SelectTreeNode selectTreeNode = new SelectTreeNode();
				selectTreeNode.setId(dic.getId().toString());
				selectTreeNode.setParentId(dic.getParentId().toString());
				selectTreeNode.setName(dic.getVarName());
				selectTreeNode.setCode(dic.getVarCode());
				treeNodeList.add(selectTreeNode);
			});
		}
		treeNodeList.add(SelectTreeNode.createParent());
		return ResultMap.ok().put("dicList", treeNodeList);
	}

	/**
	 * 获取数据字典select树
	 *
	 * @return
	 */
	@GetMapping("/selectNode/{pid}")
	public Object selectTree(@PathVariable("parentId") Long parentId) {
		List<SelectNode>	tree	= new ArrayList<>();
		Map<String, Object>	params	= new HashMap<>();
		params.put(SysDicDTO.PARENT_ID, parentId);
		List<SysDicDTO> list = sysDicService().list(params, SysDic.DB_ID, SysDic.DB_VAR_NAME);
		if (ListUtil.isNotEmpty(list)) {
			list.stream().map(dicDTO -> {
				SelectNode selectNode = new SelectNode();
				selectNode.setLabel(dicDTO.getVarName());
				selectNode.setValue(dicDTO.getId().toString());
				return selectNode;
			}).collect(Collectors.toList());

		}
		return ResultMap.ok().put("list", tree);
	}

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "分页查询数据字典", notes = "分页查询数据字典")
	@RequiresPermissions("sys/dic/page")
	@GetMapping("/page")
	public ResultMap page(@RequestParam(required = false) Map<String, Object> params) {
		PageInfoVO<SysDicDTO> page = sysDicService().page(params);

		return ResultMap.ok().put("page", page);
	}

	@ApiOperation(value = "seftTestPreset", notes = "获取自检预置数据")
	@RequiresPermissions("sys/dic/seftTestPreset")
	@GetMapping("/seftTestPreset")
	public ResultMap getSeftTestPreset() {
		Map<String, Object> params = new HashMap<>();
		params.put(SysDicDTO.VAR_CODE, "SELF_TEST_PRESET");
		List<SysDicDTO> list = sysDicService().getChildren(params);
		return ResultMap.ok().put("data", list);
	}

	@ApiOperation(value = "getChildren", notes = "查询下级数据")
	@RequiresPermissions("sys/dic/getChildren")
	@GetMapping("/getChildren/{id}")
	public ResultMap getChildren(@PathVariable("id") Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put(SysDicDTO.ID, id);
		List<SysDicDTO> list = sysDicService().getChildren(params);
		return ResultMap.ok().put("data", list);
	}

	@ApiOperation(value = "获取系统运行配置", notes = "获取系统运行配置")
	@RequiresPermissions("sys/dic/getSettings")
	@GetMapping("/getSettings")
	@KtfTrace("获取系统运行配置")
	public ResultMap getSettings() {
		Map<String, Object> map = sysDicService().getSettings("VUE_SETTINGS");
		return ResultMap.ok().put("data", map);
	}

}
