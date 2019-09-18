package com.kivi.dashboard.sys.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.web.bind.annotation.*;

import com.kivi.dashboard.sys.service.ISysUserService;
import com.kivi.dashboard.model.ResultMap;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.sys.dto.SysUserDTO;
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
 * 用户 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
 
 
@Api(tags = {"用户"})
@RestController
@RequestMapping("/sys/sysUser")
@Slf4j
public class SysUserController extends BaseController {
	
    
    @Autowired
    private ISysUserService iSysUserService;

	@ApiOperation(value = "用户信息", notes = "用户信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys/sysUser/info")
    public ResultMap info(@PathVariable("id") String id) {
    	SysUserDTO dto = iSysUserService.getDTOById(NumberUtil.toLongObject(id, -1L));
        return ResultMap.ok().put("SysUser", dto);
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增用户", notes = "新增用户")
    @RequiresPermissions("sys/sysUser/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody SysUserDTO sysUserDTO){
    	try {
    		//sysUserDTO.setCreateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysUserService.save(sysUserDTO);
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
    @ApiOperation(value = "修改用户", notes = "修改用户")
    @RequiresPermissions("sys/sysUser/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody SysUserDTO sysUserDTO){
     	try {
     		//sysUserDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysUserService.updateById(sysUserDTO);
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
    @ApiOperation(value = "批量删除用户", notes = "删除用户")
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys/sysUser/delete")
    public ResultMap delete(@PathVariable("id") Long id){
    	try {
	        Boolean b = iSysUserService.removeById(id);
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
    @ApiOperation(value = "批量删除用户", notes = "删除用户")
    @PostMapping("/delete")
    @RequiresPermissions("sys/sysUser/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids){
	    try {
	        Boolean b = iSysUserService.removeByIds(Arrays.asList(ids));
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
    @ApiOperation(value = "修改用户", notes = "修改用户")
    @RequiresPermissions("sys/sysUser/list")
    @GetMapping("/list")
    public ResultMap list(@RequestBody SysUserDTO sysUserDTO ){
        List<SysUserDTO> list =  iSysUserService.list(sysUserDTO);
        return ResultMap.ok().put("list", list);
    }

    /**
    * 分页查询
    */
    @ApiOperation(value = "修改用户", notes = "修改用户")
    @RequiresPermissions("sys/sysUser/page")
    @GetMapping("/page")
    public ResultMap page(@RequestParam Map<String, Object> params){
        PageInfoVO<SysUserDTO> page = iSysUserService.page(params);
    	
        return ResultMap.ok().put("page", page);
    }


}

