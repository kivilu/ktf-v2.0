package com.kivi.dashboard.sys.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.web.bind.annotation.*;

import com.kivi.dashboard.sys.service.ISysUserRoleService;
import com.kivi.dashboard.model.ResultMap;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.sys.dto.SysUserRoleDTO;
import java.util.*;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.kivi.framework.web.controller.BaseController;

import com.kivi.framework.vo.page.PageInfoVO;
import com.vip.vjtools.vjkit.number.NumberUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户角色 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
 
 
@Api(tags = {"用户角色"})
@RestController
@RequestMapping("/sys/sysUserRole")
@Slf4j
public class SysUserRoleController extends BaseController {
	
    
    @Autowired
    private ISysUserRoleService iSysUserRoleService;

	@ApiOperation(value = "用户角色信息", notes = "用户角色信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys/sysUserRole/info")
    public ResultMap info(@PathVariable("id") String id) {
    	SysUserRoleDTO dto = iSysUserRoleService.getDTOById(NumberUtil.toLongObject(id, -1L));
        return ResultMap.ok().put("SysUserRole", dto);
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增用户角色", notes = "新增用户角色")
    @RequiresPermissions("sys/sysUserRole/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody SysUserRoleDTO sysUserRoleDTO){
    	try {
    		//sysUserRoleDTO.setCreateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysUserRoleService.save(sysUserRoleDTO);
	        if (b) {
	            return ResultMap.ok("新增成功！");
	        } else {
	            return ResultMap.ok("新增失败！");
	        }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResultMap.error("添加失败，请联系管理员");
        }
    }
    
    /**
    * 修改
    */
    @ApiOperation(value = "修改用户角色", notes = "修改用户角色")
    @RequiresPermissions("sys/sysUserRole/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody SysUserRoleDTO sysUserRoleDTO){
     	try {
     		//sysUserRoleDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysUserRoleService.updateById(sysUserRoleDTO);
	        if (b) {
	            return ResultMap.ok("编辑成功！");
	        } else {
	            return ResultMap.ok("编辑失败！");
	        }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResultMap.error("编辑失败，请联系管理员");
        }
    }
    
   /**
    * 删除
    */
    @ApiOperation(value = "批量删除用户角色", notes = "删除用户角色")
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys/sysUserRole/delete")
    public ResultMap delete(@PathVariable("id") Long id){
    	try {
	        Boolean b = iSysUserRoleService.removeById(id);
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
    @ApiOperation(value = "批量删除用户角色", notes = "删除用户角色")
    @PostMapping("/delete")
    @RequiresPermissions("sys/sysUserRole/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids){
	    try {
	        Boolean b = iSysUserRoleService.removeByIds(Arrays.asList(ids));
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
    @ApiOperation(value = "修改用户角色", notes = "修改用户角色")
    @RequiresPermissions("sys/sysUserRole/list")
    @GetMapping("/list")
    public ResultMap list(@RequestBody SysUserRoleDTO sysUserRoleDTO ){
        List<SysUserRoleDTO> list =  iSysUserRoleService.list(sysUserRoleDTO);
        return ResultMap.ok().put("list", list);
    }

    /**
    * 分页查询
    */
    @ApiOperation(value = "修改用户角色", notes = "修改用户角色")
    @RequiresPermissions("sys/sysUserRole/page")
    @GetMapping("/page")
    public ResultMap page(@RequestParam Map<String, Object> params){
        PageInfoVO<SysUserRoleDTO> page = iSysUserRoleService.page(params);
    	
        return ResultMap.ok().put("page", page);
    }


}

