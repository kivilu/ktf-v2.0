package com.kivi.dashboard.sys.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.web.bind.annotation.*;

import com.kivi.dashboard.sys.service.ISysIndustryService;
import com.kivi.dashboard.model.ResultMap;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.sys.dto.SysIndustryDTO;
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
 * 行业代码 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
 
 
@Api(tags = {"行业代码"})
@RestController
@RequestMapping("/sys/sysIndustry")
@Slf4j
public class SysIndustryController extends BaseController {
	
    
    @Autowired
    private ISysIndustryService iSysIndustryService;

	@ApiOperation(value = "行业代码信息", notes = "行业代码信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys/sysIndustry/info")
    public ResultMap info(@PathVariable("id") String id) {
    	SysIndustryDTO dto = iSysIndustryService.getDTOById(NumberUtil.toLongObject(id, -1L));
        return ResultMap.ok().put("SysIndustry", dto);
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增行业代码", notes = "新增行业代码")
    @RequiresPermissions("sys/sysIndustry/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody SysIndustryDTO sysIndustryDTO){
    	try {
    		//sysIndustryDTO.setCreateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysIndustryService.save(sysIndustryDTO);
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
    @ApiOperation(value = "修改行业代码", notes = "修改行业代码")
    @RequiresPermissions("sys/sysIndustry/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody SysIndustryDTO sysIndustryDTO){
     	try {
     		//sysIndustryDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysIndustryService.updateById(sysIndustryDTO);
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
    @ApiOperation(value = "批量删除行业代码", notes = "删除行业代码")
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys/sysIndustry/delete")
    public ResultMap delete(@PathVariable("id") Long id){
    	try {
	        Boolean b = iSysIndustryService.removeById(id);
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
    @ApiOperation(value = "批量删除行业代码", notes = "删除行业代码")
    @PostMapping("/delete")
    @RequiresPermissions("sys/sysIndustry/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids){
	    try {
	        Boolean b = iSysIndustryService.removeByIds(Arrays.asList(ids));
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
    @ApiOperation(value = "修改行业代码", notes = "修改行业代码")
    @RequiresPermissions("sys/sysIndustry/list")
    @GetMapping("/list")
    public ResultMap list(@RequestBody SysIndustryDTO sysIndustryDTO ){
        List<SysIndustryDTO> list =  iSysIndustryService.list(sysIndustryDTO);
        return ResultMap.ok().put("list", list);
    }

    /**
    * 分页查询
    */
    @ApiOperation(value = "修改行业代码", notes = "修改行业代码")
    @RequiresPermissions("sys/sysIndustry/page")
    @GetMapping("/page")
    public ResultMap page(@RequestParam Map<String, Object> params){
        PageInfoVO<SysIndustryDTO> page = iSysIndustryService.page(params);
    	
        return ResultMap.ok().put("page", page);
    }


}

