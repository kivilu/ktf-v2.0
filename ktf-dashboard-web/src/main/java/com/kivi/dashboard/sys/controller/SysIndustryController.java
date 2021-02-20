package com.kivi.dashboard.sys.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.google.common.collect.Maps;
import com.kivi.dashboard.base.DashboardController;
// import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.sys.dto.SysIndustryDTO;
import com.kivi.dashboard.sys.entity.SysIndustry;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.model.SelectNode;
import com.kivi.framework.model.SelectTreeNode;
import com.kivi.framework.properties.KtfSwaggerProperties;
import com.kivi.framework.vo.page.PageInfoVO;
import com.vip.vjtools.vjkit.number.NumberUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 行业代码 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@ConditionalOnProperty(prefix = KtfSwaggerProperties.PREFIX, value = "enable-sys-api", havingValue = "true",
    matchIfMissing = false)
@Api(tags = {"行业代码"})
@RestController
@RequestMapping("/sys/industry")
@Slf4j
public class SysIndustryController extends DashboardController {

    @ApiOperation(value = "行业代码信息", notes = "行业代码信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys/industry/info")
    public ResultMap info(@PathVariable("id") String id) {
        SysIndustryDTO dto = sysIndustryService().getDTOById(NumberUtil.toLongObject(id, -1L));
        SysIndustryDTO pdto = sysIndustryService().getDTOById(dto.getPid());
        if (pdto != null) {
            dto.setName(pdto.getName());
        } else {
            dto.setName("顶级");
        }

        return ResultMap.ok().put("industry", dto);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增行业代码", notes = "新增行业代码")
    @RequiresPermissions("sys/industry/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody SysIndustryDTO sysIndustryDTO) {
        try {
            // sysIndustryDTO.setCreateUser(ShiroKit.getUser().getLoginName());
            Boolean b = sysIndustryService().save(sysIndustryDTO);
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
    @ApiOperation(value = "修改行业代码", notes = "修改行业代码")
    @RequiresPermissions("sys/industry/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody SysIndustryDTO sysIndustryDTO) {
        try {
            // sysIndustryDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
            Boolean b = sysIndustryService().updateById(sysIndustryDTO);
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
    @ApiOperation(value = "删除行业代码", notes = "删除行业代码")
    @GetMapping("/delete/{id}")
    @RequiresPermissions("sys/industry/delete")
    public ResultMap delete(@PathVariable("id") Long id) {
        try {
            sysIndustryService().removeById(id);
            QueryWrapper<SysIndustry> wrapper = new QueryWrapper<>();
            wrapper.eq(SysIndustry.DB_PID, id);
            sysIndustryService().remove(wrapper);
            return ResultMap.ok("删除成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultMap.error("删除失败，请联系管理员");
        }
    }

    /**
     * 批量删除
     */
    @ApiOperation(value = "批量删除行业代码", notes = "删除行业代码")
    @PostMapping("/delete")
    @RequiresPermissions("sys/industry/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids) {
        try {
            List<Long> idList = new ArrayList<Long>();
            Collections.addAll(idList, ids);
            sysIndustryService().removeByIds(idList);

            QueryWrapper<SysIndustry> wrapper = new QueryWrapper<>();
            wrapper.in(SysIndustry.DB_PID, idList);
            sysIndustryService().remove(wrapper);

            return ResultMap.ok("删除成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultMap.error("批量删除失败，请联系管理员");
        }
    }

    /**
     * 查询列表
     */
    @ApiOperation(value = "查询行业代码列表", notes = "查询行业代码列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", dataType = "string", value = "名称，可选，模糊匹配", paramType = "query",
            allowEmptyValue = true),
        @ApiImplicitParam(name = "page", dataType = "integer", value = "当前页，可选，默认值：1", paramType = "query",
            allowEmptyValue = true),
        @ApiImplicitParam(name = "limit", dataType = "integer", value = "每页大小，可选，默认值：10", paramType = "query",
            allowEmptyValue = true)})
    @RequiresPermissions("sys/industry/list")
    @GetMapping("/list")
    public ResultMap list(@RequestParam(required = false) Map<String, Object> params) {
        List<SysIndustryDTO> list = sysIndustryService().listLike(params);
        return ResultMap.ok().data(list);
    }

    /**
     * 分页查询
     */
    @ApiOperation(value = "分页查询行业代码", notes = "分页查询行业代码")
    @RequiresPermissions("sys/sysIndustry/page")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", dataType = "string", value = "名称，可选，模糊匹配", paramType = "query",
            allowEmptyValue = true),
        @ApiImplicitParam(name = "page", dataType = "int", value = "当前页，可选，默认值：1", paramType = "query",
            allowEmptyValue = true),
        @ApiImplicitParam(name = "limit", dataType = "int", value = "每页大小，可选，默认值：10", paramType = "query",
            allowEmptyValue = true)})
    @GetMapping("/page")
    public ResultMap page(@ApiIgnore @RequestParam(required = false) Map<String, Object> params) {
        PageInfoVO<SysIndustryDTO> page = sysIndustryService().page(params);

        return ResultMap.ok().data(page);
    }

    /**
     * 选择行业（添加、修改）
     *
     * @return
     */
    @ApiOperation(value = "选择行业（添加、修改）", notes = "选择行业（添加、修改）")
    @GetMapping("/select/{pid}")
    public ResultMap select(@PathVariable("pid") Long pid) {
        Map<String, Object> params = Maps.newHashMap();
        if (pid != null && 0 != pid) {
            params.put(SysIndustryDTO.PID, pid);
        }
        List<SysIndustryDTO> industryList = sysIndustryService().list(params, SysIndustryDTO.ID, SysIndustryDTO.PID,
            SysIndustryDTO.CODE, SysIndustryDTO.NAME);
        List<SelectTreeNode> treeNodeList = industryList.stream().map(dto -> {
            SelectTreeNode selectTreeNode = new SelectTreeNode();
            selectTreeNode.setId(dto.getId().toString());
            selectTreeNode.setParentId(dto.getPid().toString());
            selectTreeNode.setName(dto.getName());
            selectTreeNode.setCode(dto.getCode());
            return selectTreeNode;
        }).collect(Collectors.toList());

        treeNodeList.add(SelectTreeNode.createParent());
        return ResultMap.ok().put("list", treeNodeList);
    }

    /**
     * 获取选择行业select树
     *
     * @return
     */
    @ApiOperation(value = "获取选择行业select树", notes = "获取选择行业select树")
    @GetMapping("/selectNode/{pid}")
    public Object selectTree(@PathVariable("pid") Long pid) {
        Map<String, Object> params = new HashMap<>();
        if (pid != null && 0 != pid) {
            params.put(SysIndustryDTO.PID, pid);
        }
        List<SysIndustryDTO> industryList = sysIndustryService().list(params, SysIndustryDTO.ID, SysIndustryDTO.NAME);
        List<SelectNode> tree = industryList.stream().map(dto -> {
            SelectNode selectNode = new SelectNode();
            selectNode.setLabel(dto.getName());
            selectNode.setValue(dto.getId().toString());
            return selectNode;
        }).collect(Collectors.toList());

        return ResultMap.ok().put("list", tree);
    }

}
