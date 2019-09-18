package com.kivi.dashboard.sys.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.web.bind.annotation.*;

import com.kivi.dashboard.sys.service.ISysApplicationService;
import com.kivi.dashboard.model.ResultMap;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.sys.dto.SysApplicationDTO;
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
 * 系统应用 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
 
 
@Api(tags = {"系统应用"})
@RestController
@RequestMapping("/sys/sysApplication")
@Slf4j
public class SysApplicationController extends BaseController {
	
    
    @Autowired
    private ISysApplicationService iSysApplicationService;

	@ApiOperation(value = "系统应用信息", notes = "系统应用信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys/sysApplication/info")
    public ResultMap info(@PathVariable("id") String id) {
    	SysApplicationDTO dto = iSysApplicationService.getDTOById(NumberUtil.toLongObject(id, -1L));
        return ResultMap.ok().put("SysApplication", dto);
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增系统应用", notes = "新增系统应用")
    @RequiresPermissions("sys/sysApplication/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody SysApplicationDTO sysApplicationDTO){
    	try {
    		//sysApplicationDTO.setCreateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysApplicationService.save(sysApplicationDTO);
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
    @ApiOperation(value = "修改系统应用", notes = "修改系统应用")
    @RequiresPermissions("sys/sysApplication/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody SysApplicationDTO sysApplicationDTO){
     	try {
     		//sysApplicationDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysApplicationService.updateById(sysApplicationDTO);
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
    @ApiOperation(value = "批量删除系统应用", notes = "删除系统应用")
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys/sysApplication/delete")
    public ResultMap delete(@PathVariable("id") Long id){
    	try {
	        Boolean b = iSysApplicationService.removeById(id);
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
    @ApiOperation(value = "批量删除系统应用", notes = "删除系统应用")
    @PostMapping("/delete")
    @RequiresPermissions("sys/sysApplication/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids){
	    try {
	        Boolean b = iSysApplicationService.removeByIds(Arrays.asList(ids));
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
    @ApiOperation(value = "修改系统应用", notes = "修改系统应用")
    @RequiresPermissions("sys/sysApplication/list")
    @GetMapping("/list")
    public ResultMap list(@RequestBody SysApplicationDTO sysApplicationDTO ){
        List<SysApplicationDTO> list =  iSysApplicationService.list(sysApplicationDTO);
        return ResultMap.ok().put("list", list);
    }

    /**
    * 分页查询
    */
    @ApiOperation(value = "修改系统应用", notes = "修改系统应用")
    @RequiresPermissions("sys/sysApplication/page")
    @GetMapping("/page")
    public ResultMap page(@RequestParam Map<String, Object> params){
        PageInfoVO<SysApplicationDTO> page = iSysApplicationService.page(params);
    	
        return ResultMap.ok().put("page", page);
    }


}

