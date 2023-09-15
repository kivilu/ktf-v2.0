package com.kivi.sys.permission.controller;

import java.util.HashMap;
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
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.base.DashboardController;
import com.kivi.sys.permission.dto.SysRoleDTO;
import com.kivi.sys.shiro.ShiroKit;

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
@Api(tags = "权限管理—角色管理")
@ApiSupport(order = 12)
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
            // 清除缓存权限
            ShiroKit.clearAuth();
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
        @ApiImplicitParam(name = "status", dataType = "integer", value = "角色状态，可选，0有效，1无效", paramType = "query",
            allowEmptyValue = true),
        @ApiImplicitParam(name = "page", dataType = "integer", value = "当前页，可选，默认值：1", paramType = "query",
            allowEmptyValue = true),
        @ApiImplicitParam(name = "limit", dataType = "integer", value = "每页大小，可选，默认值：10", paramType = "query",
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

    @ApiOperation(value = "角色列表查询", notes = "角色列表查询")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", dataType = "string", value = "角色名称，可选，模糊匹配", paramType = "query",
            allowEmptyValue = true),
        @ApiImplicitParam(name = "status", dataType = "integer", value = "角色状态，可选，0有效，1无效", paramType = "query",
            allowEmptyValue = true),
        @ApiImplicitParam(name = SysRoleDTO.SUB_ROLE_IDS, dataType = "string", value = "下属角色ID列表，ID之间使用逗号分隔",
            paramType = "query", allowEmptyValue = true)})
    @GetMapping("/list")
    @RequiresPermissions("permission/role/list")
    public ResultMap list(@ApiIgnore @RequestParam Map<String, Object> params) {
        if (params == null)
            params = new HashMap<>();
        // 如果不是超级管理员，则只查询自己创建的角色列表
        if (ShiroKit.getUser().getId() != KtfConstant.SUPER_ADMIN) {
            // SysRole optRole = sysRoleService().getById(ShiroKit.getUser().getRoleId());
            // params.put(SysRoleDTO.SUB_ROLE_IDS, optRole.getSubRoleIds());
        }
        List<SysRoleDTO> list = sysRoleService().listLike(params, SysRoleDTO.ID, SysRoleDTO.NAME, SysRoleDTO.TYPE);
        return ResultMap.ok().data(list);
    }

    @ApiOperation(value = "角色列表查询", notes = "角色列表查询")
    @ApiImplicitParams({
        @ApiImplicitParam(name = SysRoleDTO.NAME, dataType = "string", value = "角色名称，可选，模糊匹配", paramType = "query",
            allowEmptyValue = true),
        @ApiImplicitParam(name = SysRoleDTO.STATUS, dataType = "integer", value = "角色状态，可选，0有效，1无效",
            paramType = "query", allowEmptyValue = true),
        @ApiImplicitParam(name = SysRoleDTO.SUB_ROLE_IDS, dataType = "string", value = "下属角色ID列表，ID之间使用逗号分隔",
            paramType = "query", allowEmptyValue = true)})
    @GetMapping("/list/{orgId}")
    @RequiresPermissions("permission/role/list")
    public ResultMap listByOrgId(@PathVariable("orgId") Long orgId,
        @ApiIgnore @RequestParam Map<String, Object> params) {
        if (params == null)
            params = new HashMap<>();
        params.put(SysRoleDTO.ORG_ID, orgId);

        // 如果不是超级管理员，则只查询自己创建的角色列表
        // if (ShiroKit.getUser().getId() != KtfConstant.SUPER_ADMIN) {
        // params.put(SysRoleDTO.CREATE_USER_ID, ShiroKit.getUser().getId());
        // }
        List<SysRoleDTO> list = sysRoleService().listLike(params, SysRoleDTO.ID, SysRoleDTO.NAME, SysRoleDTO.TYPE);
        return ResultMap.ok().data(list);
    }

}
