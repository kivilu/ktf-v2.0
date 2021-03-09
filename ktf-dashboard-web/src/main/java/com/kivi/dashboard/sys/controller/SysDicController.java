package com.kivi.dashboard.sys.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kivi.dashboard.base.DashboardController;
import com.kivi.dashboard.enums.DicEnum;
// import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.sys.dto.SysDicDTO;
import com.kivi.dashboard.sys.entity.SysDic;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.properties.KtfSwaggerProperties;
import com.kivi.framework.vo.page.PageInfoVO;
import com.vip.vjtools.vjkit.collection.ListUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@ConditionalOnProperty(
		prefix = KtfSwaggerProperties.PREFIX,
		value = "enable-dic-api",
		havingValue = "true",
		matchIfMissing = false)
@Api(value = "数据字典管理接口", tags = { "数据字典管理接口" })
@RestController
@RequestMapping("/sys/dic")
@Slf4j
public class SysDicController extends DashboardController {

	@ApiOperation(value = "数据字典信息", notes = "数据字典信息")
	@GetMapping("/info/{id}")
	@RequiresPermissions("sys/dic/info")
	public ResultMap info(@PathVariable("id") Long id) {
		SysDicDTO dto = sysDicService().getDto(id);

		return ResultMap.ok().data(dto);
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
	@GetMapping("/delete/{id}")
	@RequiresPermissions("sys/dic/delete")
	public ResultMap delete(@PathVariable("id") Long id) {
		try {
			Boolean b = sysDicService().removeWithChildren(id);
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

	@ApiOperation(value = "获取系统运行配置", notes = "获取系统运行配置")
	@RequiresPermissions("sys/dic/getSettings")
	@GetMapping("/getSettings")
	@KtfTrace("获取系统运行配置")
	public ResultMap getSettings() {
		Map<String, Object> map = sysDicService().getSettings(DicEnum.STS_VUE.getCode());
		return ResultMap.ok().data(map);
	}

	@ApiOperation(value = "获取系统运行配置", notes = "获取系统运行配置")
	@RequiresPermissions("sys/dic/getSettings")
	@GetMapping("/getSettings/{code}")
	@KtfTrace("获取系统运行配置")
	public ResultMap getSettingsByCode(@PathVariable("code") String code) {
		Map<String, Object> map = sysDicService().getSettings(code);
		return ResultMap.ok().data(map);
	}

	@ApiOperation(value = "分页查询字典数据", notes = "分页查询字典数据")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "keyword",
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
	@RequiresPermissions("sys/dic/page")
	@GetMapping("/page")
	public ResultMap list(@ApiIgnore @RequestParam(required = false) Map<String, Object> params) {
		PageInfoVO<SysDicDTO> page = sysDicService().page(params);
		return ResultMap.ok().data(page);
	}

	/**
	 * 根据ID查询所属字典数据
	 */
	@ApiOperation(value = "根据ID查询所属字典数据", notes = "根据ID查询所属字典数据")
	@GetMapping("/getChildren/{id}")
	@RequiresPermissions("sys/dic/getChildren")
	public ResultMap getChildren(@PathVariable("id") Long id) {
		List<SysDicDTO> list = sysDicService().getChildren(id, false);
		return ResultMap.ok().data(list);
	}

	/**
	 * 字典数据树的一级节点
	 */
	@ApiOperation(value = "字典数据树的一级节点", notes = "字典数据树的一级节点")
	@GetMapping("/tree/top")
	@RequiresPermissions("sys/dic/tree")
	public ResultMap getTreeTops() {
		List<SysDicDTO> list = sysDicService().treeQuery(null);
		return ResultMap.ok().data(list);
	}

	/**
	 * 字典数据树的子节点
	 */
	@ApiOperation(value = "字典数据树的子节点", notes = "字典数据树的子节点")
	@GetMapping("/tree/{pid}")
	@RequiresPermissions("sys/dic/tree")
	public ResultMap getTreeChildren(@PathVariable("pid") Long pid) {
		List<SysDicDTO> list = sysDicService().treeQuery(pid);
		return ResultMap.ok().data(list);
	}

	/**
	 * 字典数据树的父节点
	 */
	@ApiOperation(value = "字典数据树的父节点", notes = "字典数据树的父节点")
	@GetMapping("/tree/parent/{id}")
	@RequiresPermissions("sys/dic/tree")
	public ResultMap getTreeParent(@PathVariable("id") Long id) {
		List<SysDicDTO> list = sysDicService().getParents(id);
		return ResultMap.ok().data(list);
	}

}
