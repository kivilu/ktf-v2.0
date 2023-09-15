package com.kivi.sys.sys.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.kivi.framework.models.SelectNode;
import com.kivi.framework.models.SelectTreeNode;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.base.DashboardController;
import com.kivi.sys.sys.dto.SysIndustryDTO;
import com.kivi.sys.sys.dto.SysRegionDTO;
import com.vip.vjtools.vjkit.number.NumberUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 地区信息 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Api(tags = "系统管理—地区信息")
@ApiSupport(order = 32)
@RestController
@RequestMapping("/sys/region")
@Slf4j
public class SysRegionController extends DashboardController {

    @ApiOperation(value = "地区信息信息", notes = "地区信息信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys/region/info")
    public ResultMap info(@PathVariable("id") String id) {
        SysRegionDTO dto = sysRegionService().getDTOById(NumberUtil.toLongObject(id, -1L));
        SysRegionDTO pdto = sysRegionService().getDTOById(dto.getPid());
        if (pdto != null) {
            dto.setName(pdto.getName());
        } else {
            dto.setName("顶级");
        }

        return ResultMap.ok().put("region", dto);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增地区信息", notes = "新增地区信息")
    @RequiresPermissions("sys/region/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody SysRegionDTO sysRegionDTO) {
        try {
            // sysRegionDTO.setCreateUser(ShiroKit.getUser().getLoginName());
            Boolean b = sysRegionService().save(sysRegionDTO);
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
    @ApiOperation(value = "修改地区信息", notes = "修改地区信息")
    @RequiresPermissions("sys/region/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody SysRegionDTO sysRegionDTO) {
        try {
            // sysRegionDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
            Boolean b = sysRegionService().updateById(sysRegionDTO);
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
    @ApiOperation(value = "删除地区信息", notes = "删除地区信息")
    @GetMapping("/delete/{id}")
    @RequiresPermissions("sys/region/delete")
    public ResultMap delete(@PathVariable("id") Long id) {
        try {
            sysRegionService().removeWithChildren(id);
            return ResultMap.ok("删除成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultMap.error("批量删除失败，请联系管理员");
        }
    }

    /**
     * 批量删除
     */
    @ApiOperation(value = "批量删除地区信息", notes = "批量删除地区信息")
    @PostMapping("/delete")
    @RequiresPermissions("sys/region/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids) {
        try {
            List<Long> idList = new ArrayList<Long>();
            Collections.addAll(idList, ids);
            sysRegionService().removeWithChildren(idList);

            return ResultMap.ok("删除成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultMap.error("批量删除失败，请联系管理员");
        }
    }

    /**
     * 分页查询
     */
    @ApiOperation(value = "分页查询地区信息", notes = "分页查询地区信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", dataType = "string", value = "名称，可选，模糊匹配", paramType = "query",
            allowEmptyValue = true),
        @ApiImplicitParam(name = "page", dataType = "integer", value = "当前页，可选，默认值：1", paramType = "query",
            allowEmptyValue = true),
        @ApiImplicitParam(name = "limit", dataType = "integer", value = "每页大小，可选，默认值：10", paramType = "query",
            allowEmptyValue = true)})
    @RequiresPermissions("sys/region/page")
    @GetMapping("/page")
    public ResultMap page(@ApiIgnore @RequestParam(required = false) Map<String, Object> params) {
        PageInfoVO<SysRegionDTO> page = sysRegionService().page(params);

        return ResultMap.ok().data(page);
    }

    @ApiOperation(value = "根据ID查询下级地区", notes = "根据ID查询下级地区")
    @GetMapping("/getChildren/{id}")
    @RequiresPermissions("sys/region/getChildren")
    public ResultMap getChildren(@PathVariable("id") Long id) {
        List<SysRegionDTO> list = sysRegionService().getChildren(id, false);
        return ResultMap.ok().data(list);
    }

    /**
     * 选择地区（添加、修改）
     *
     * @return
     */
    @ApiOperation(value = "选择地区（添加、修改）", notes = "选择地区（添加、修改）")
    @GetMapping("/select/{pid}")
    public ResultMap select(@PathVariable("pid") Long pid) {
        Map<String, Object> params = Maps.newHashMap();
        if (pid != null && 0 != pid) {
            params.put(SysIndustryDTO.PID, pid);
        }
        List<SysRegionDTO> industryList =
            sysRegionService().list(params, SysRegionDTO.ID, SysRegionDTO.PID, SysRegionDTO.CODE, SysRegionDTO.NAME);
        List<SelectTreeNode> treeNodeList = industryList.stream().map(dto -> {
            SelectTreeNode selectTreeNode = new SelectTreeNode();
            selectTreeNode.setId(dto.getId().toString());
            selectTreeNode.setParentId(dto.getPid().toString());
            selectTreeNode.setName(dto.getName());
            selectTreeNode.setCode(dto.getCode());
            return selectTreeNode;
        }).collect(Collectors.toList());

        treeNodeList.add(SelectTreeNode.createParent());
        return ResultMap.ok().data(treeNodeList);
    }

    /**
     * 获取选择行业select树
     *
     * @return
     */
    @ApiOperation(value = "获取选择地区select树", notes = "获取选择地区select树")
    @GetMapping("/selectNode/{pid}")
    public ResultMap selectTree(@PathVariable("pid") Long pid) {
        Map<String, Object> params = new HashMap<>();
        if (pid != null && 0 != pid) {
            params.put(SysIndustryDTO.PID, pid);
        }
        List<SysRegionDTO> regionList = sysRegionService().list(params, SysIndustryDTO.ID, SysIndustryDTO.NAME);
        List<SelectNode> tree = regionList.stream().map(dto -> {
            SelectNode selectNode = new SelectNode();
            selectNode.setLabel(dto.getName());
            selectNode.setValue(dto.getId().toString());
            return selectNode;
        }).collect(Collectors.toList());

        return ResultMap.ok().data(tree);
    }

    @ApiOperation(value = "获取省份select树", notes = "获取选择省份select树")
    @GetMapping("/select/province")
    public ResultMap selectProvice() {
        List<SysRegionDTO> regionList = sysRegionService().listProvice();
        List<SelectTreeNode> treeNodeList = regionList.stream().map(dto -> {
            SelectTreeNode selectTreeNode = new SelectTreeNode();
            selectTreeNode.setId(dto.getId().toString());
            selectTreeNode.setParentId(dto.getPid().toString());
            selectTreeNode.setName(dto.getName());
            selectTreeNode.setCode(dto.getCode());
            return selectTreeNode;
        }).collect(Collectors.toList());

        return ResultMap.ok().data(treeNodeList);
    }

}