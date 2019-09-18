package com.kivi.dashboard.sys.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.web.bind.annotation.*;

import com.kivi.dashboard.sys.service.ISysRegionService;
import com.kivi.dashboard.model.ResultMap;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.sys.dto.SysRegionDTO;
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
 * 地区信息 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
 
 
@Api(tags = {"地区信息"})
@RestController
@RequestMapping("/sys/sysRegion")
@Slf4j
public class SysRegionController extends BaseController {
	
    
    @Autowired
    private ISysRegionService iSysRegionService;

	@ApiOperation(value = "地区信息信息", notes = "地区信息信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys/sysRegion/info")
    public ResultMap info(@PathVariable("id") String id) {
    	SysRegionDTO dto = iSysRegionService.getDTOById(NumberUtil.toLongObject(id, -1L));
        return ResultMap.ok().put("SysRegion", dto);
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增地区信息", notes = "新增地区信息")
    @RequiresPermissions("sys/sysRegion/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody SysRegionDTO sysRegionDTO){
    	try {
    		//sysRegionDTO.setCreateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysRegionService.save(sysRegionDTO);
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
    @ApiOperation(value = "修改地区信息", notes = "修改地区信息")
    @RequiresPermissions("sys/sysRegion/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody SysRegionDTO sysRegionDTO){
     	try {
     		//sysRegionDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysRegionService.updateById(sysRegionDTO);
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
    @ApiOperation(value = "批量删除地区信息", notes = "删除地区信息")
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys/sysRegion/delete")
    public ResultMap delete(@PathVariable("id") Long id){
    	try {
	        Boolean b = iSysRegionService.removeById(id);
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
    @ApiOperation(value = "批量删除地区信息", notes = "删除地区信息")
    @PostMapping("/delete")
    @RequiresPermissions("sys/sysRegion/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids){
	    try {
	        Boolean b = iSysRegionService.removeByIds(Arrays.asList(ids));
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
    @ApiOperation(value = "修改地区信息", notes = "修改地区信息")
    @RequiresPermissions("sys/sysRegion/list")
    @GetMapping("/list")
    public ResultMap list(@RequestBody SysRegionDTO sysRegionDTO ){
        List<SysRegionDTO> list =  iSysRegionService.list(sysRegionDTO);
        return ResultMap.ok().put("list", list);
    }

    /**
    * 分页查询
    */
    @ApiOperation(value = "修改地区信息", notes = "修改地区信息")
    @RequiresPermissions("sys/sysRegion/page")
    @GetMapping("/page")
    public ResultMap page(@RequestParam Map<String, Object> params){
        PageInfoVO<SysRegionDTO> page = iSysRegionService.page(params);
    	
        return ResultMap.ok().put("page", page);
    }


}

