package com.kivi.dashboard.permission.controller;

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
import com.kivi.dashboard.permission.dto.SysRoleDTO;
import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.properties.KtfSwaggerProperties;
import com.kivi.framework.vo.page.PageInfoVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@ConditionalOnProperty(prefix = KtfSwaggerProperties.PREFIX, value = "enable-permission-api", havingValue = "true",
    matchIfMissing = false)
@Api(value = "SYS角色管理接口", tags = {"SYS角色管理接口"})
@RestController
@RequestMapping("/permission/role")
@Slf4j
public class SysRoleController extends DashboardController {

    @ApiOperation(value = "角色信息", notes = "角色信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("permission/role/info")
    public ResultMap info(@PathVariable("id") Long id) {
        SysRoleDTO role = sysRoleService().getDto(id);

        return ResultMap.ok().data(role);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增角色", notes = "新增角色")
    @RequiresPermissions("permission/role/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody SysRoleDTO dto) {
        try {
            // sysRoleDTO.setCreateUser(ShiroKit.getUser().getLoginName());
            dto.setCreateUserId(ShiroKit.getUser().getId());
            sysRoleService().save(dto);
            return ResultMap.ok("新增成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultMap.error("添加失败，请联系管理员");
        }
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改角色", notes = "修改角色")
    @RequiresPermissions("permission/role/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody SysRoleDTO dto) {
        try {
            dto.setCreateUserId(ShiroKit.getUser().getId());
            sysRoleService().updateById(dto);
            return ResultMap.ok("编辑成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultMap.error("编辑失败，请联系管理员");
        }
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除角色", notes = "删除角色")
    @GetMapping("/delete/{id}")
    @RequiresPermissions("permission/role/delete")
    public ResultMap delete(@PathVariable("id") Long id) {
        try {
            Boolean b = sysRoleService().removeById(id);
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
    @ApiOperation(value = "批量删除角色", notes = "批量删除角色")
    @PostMapping("/delete")
    @RequiresPermissions("permission/role/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids) {
        try {
            sysRoleService().deleteBatch(ids);
            return ResultMap.ok("删除成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultMap.error("批量删除失败，请联系管理员");
        }
    }

    /**
     * 分页查询
     */
    @ApiOperation(value = "角色分页查询", notes = "角色分页查询")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "keyword", dataType = "string", value = "角色名称，可选，模糊匹配", paramType = "query",
            allowEmptyValue = true),
        @ApiImplicitParam(name = "status", dataType = "int", value = "角色状态，可选，0有效，1无效", paramType = "query",
            allowEmptyValue = true),
        @ApiImplicitParam(name = "page", dataType = "int", value = "当前页，可选，默认值：1", paramType = "query",
            allowEmptyValue = true),
        @ApiImplicitParam(name = "limit", dataType = "int", value = "每页大小，可选，默认值：10", paramType = "query",
            allowEmptyValue = true)})
    @RequiresPermissions("permission/role/page")
    @GetMapping("/page")
    public ResultMap page(@ApiIgnore @RequestParam Map<String, Object> params) {
        // 如果不是超级管理员，则只查询自己创建的角色列表
        if (ShiroKit.getUser().getId() != KtfConstant.SUPER_ADMIN) {
            params.put(SysRoleDTO.CREATE_USER_ID, ShiroKit.getUser().getId());
        }
        PageInfoVO<SysRoleDTO> page = sysRoleService().page(params);
        return ResultMap.ok().data(page);
    }

    /**
     * 角色列表
     */
    /*
     * @GetMapping("/select")
     * 
     * @RequiresPermissions("permission/role/select") public ResultMap select() {
     * Map<String, Object> map = new HashMap<>(); // 如果不是超级管理员，则只查询自己所拥有的角色列表 if
     * (ShiroKit.getUser().getId() != KtfConstant.SUPER_ADMIN) {
     * map.put("createUserId", ShiroKit.getUser().getId()); } List<SysRoleDTO> list
     * = sysRoleService().listLike(map, StrKit.emptyArray());
     * 
     * return ResultMap.ok().put("list", list); }
     */
}
