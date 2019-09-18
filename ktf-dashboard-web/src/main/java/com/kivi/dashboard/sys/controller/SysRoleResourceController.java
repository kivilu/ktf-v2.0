package com.kivi.dashboard.sys.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.web.bind.annotation.*;

import com.kivi.dashboard.sys.service.ISysRoleResourceService;
import com.kivi.dashboard.model.ResultMap;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.sys.dto.SysRoleResourceDTO;
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
 * 角色资源 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
 
 
@Api(tags = {"角色资源"})
@RestController
@RequestMapping("/sys/sysRoleResource")
@Slf4j
public class SysRoleResourceController extends BaseController {
	
    
    @Autowired
    private ISysRoleResourceService iSysRoleResourceService;

	@ApiOperation(value = "角色资源信息", notes = "角色资源信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys/sysRoleResource/info")
    public ResultMap info(@PathVariable("id") String id) {
    	SysRoleResourceDTO dto = iSysRoleResourceService.getDTOById(NumberUtil.toLongObject(id, -1L));
        return ResultMap.ok().put("SysRoleResource", dto);
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增角色资源", notes = "新增角色资源")
    @RequiresPermissions("sys/sysRoleResource/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody SysRoleResourceDTO sysRoleResourceDTO){
    	try {
    		//sysRoleResourceDTO.setCreateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysRoleResourceService.save(sysRoleResourceDTO);
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
    @ApiOperation(value = "修改角色资源", notes = "修改角色资源")
    @RequiresPermissions("sys/sysRoleResource/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody SysRoleResourceDTO sysRoleResourceDTO){
     	try {
     		//sysRoleResourceDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysRoleResourceService.updateById(sysRoleResourceDTO);
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
    @ApiOperation(value = "批量删除角色资源", notes = "删除角色资源")
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys/sysRoleResource/delete")
    public ResultMap delete(@PathVariable("id") Long id){
    	try {
	        Boolean b = iSysRoleResourceService.removeById(id);
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
    @ApiOperation(value = "批量删除角色资源", notes = "删除角色资源")
    @PostMapping("/delete")
    @RequiresPermissions("sys/sysRoleResource/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids){
	    try {
	        Boolean b = iSysRoleResourceService.removeByIds(Arrays.asList(ids));
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
    @ApiOperation(value = "修改角色资源", notes = "修改角色资源")
    @RequiresPermissions("sys/sysRoleResource/list")
    @GetMapping("/list")
    public ResultMap list(@RequestBody SysRoleResourceDTO sysRoleResourceDTO ){
        List<SysRoleResourceDTO> list =  iSysRoleResourceService.list(sysRoleResourceDTO);
        return ResultMap.ok().put("list", list);
    }

    /**
    * 分页查询
    */
    @ApiOperation(value = "修改角色资源", notes = "修改角色资源")
    @RequiresPermissions("sys/sysRoleResource/page")
    @GetMapping("/page")
    public ResultMap page(@RequestParam Map<String, Object> params){
        PageInfoVO<SysRoleResourceDTO> page = iSysRoleResourceService.page(params);
    	
        return ResultMap.ok().put("page", page);
    }


}

