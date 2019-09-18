package com.kivi.dashboard.sys.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.web.bind.annotation.*;

import com.kivi.dashboard.sys.service.ISysLogService;
import com.kivi.dashboard.model.ResultMap;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.sys.dto.SysLogDTO;
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
 * 系统日志 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
 
 
@Api(tags = {"系统日志"})
@RestController
@RequestMapping("/sys/sysLog")
@Slf4j
public class SysLogController extends BaseController {
	
    
    @Autowired
    private ISysLogService iSysLogService;

	@ApiOperation(value = "系统日志信息", notes = "系统日志信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys/sysLog/info")
    public ResultMap info(@PathVariable("id") String id) {
    	SysLogDTO dto = iSysLogService.getDTOById(NumberUtil.toLongObject(id, -1L));
        return ResultMap.ok().put("SysLog", dto);
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增系统日志", notes = "新增系统日志")
    @RequiresPermissions("sys/sysLog/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody SysLogDTO sysLogDTO){
    	try {
    		//sysLogDTO.setCreateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysLogService.save(sysLogDTO);
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
    @ApiOperation(value = "修改系统日志", notes = "修改系统日志")
    @RequiresPermissions("sys/sysLog/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody SysLogDTO sysLogDTO){
     	try {
     		//sysLogDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysLogService.updateById(sysLogDTO);
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
    @ApiOperation(value = "批量删除系统日志", notes = "删除系统日志")
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys/sysLog/delete")
    public ResultMap delete(@PathVariable("id") Long id){
    	try {
	        Boolean b = iSysLogService.removeById(id);
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
    @ApiOperation(value = "批量删除系统日志", notes = "删除系统日志")
    @PostMapping("/delete")
    @RequiresPermissions("sys/sysLog/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids){
	    try {
	        Boolean b = iSysLogService.removeByIds(Arrays.asList(ids));
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
    @ApiOperation(value = "修改系统日志", notes = "修改系统日志")
    @RequiresPermissions("sys/sysLog/list")
    @GetMapping("/list")
    public ResultMap list(@RequestBody SysLogDTO sysLogDTO ){
        List<SysLogDTO> list =  iSysLogService.list(sysLogDTO);
        return ResultMap.ok().put("list", list);
    }

    /**
    * 分页查询
    */
    @ApiOperation(value = "修改系统日志", notes = "修改系统日志")
    @RequiresPermissions("sys/sysLog/page")
    @GetMapping("/page")
    public ResultMap page(@RequestParam Map<String, Object> params){
        PageInfoVO<SysLogDTO> page = iSysLogService.page(params);
    	
        return ResultMap.ok().put("page", page);
    }


}

