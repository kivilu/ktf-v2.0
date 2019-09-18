package com.kivi.dashboard.sys.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.web.bind.annotation.*;

import com.kivi.dashboard.sys.service.ISysResourceService;
import com.kivi.dashboard.model.ResultMap;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.sys.dto.SysResourceDTO;
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
 * 资源 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
 
 
@Api(tags = {"资源"})
@RestController
@RequestMapping("/sys/sysResource")
@Slf4j
public class SysResourceController extends BaseController {
	
    
    @Autowired
    private ISysResourceService iSysResourceService;

	@ApiOperation(value = "资源信息", notes = "资源信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys/sysResource/info")
    public ResultMap info(@PathVariable("id") String id) {
    	SysResourceDTO dto = iSysResourceService.getDTOById(NumberUtil.toLongObject(id, -1L));
        return ResultMap.ok().put("SysResource", dto);
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增资源", notes = "新增资源")
    @RequiresPermissions("sys/sysResource/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody SysResourceDTO sysResourceDTO){
    	try {
    		//sysResourceDTO.setCreateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysResourceService.save(sysResourceDTO);
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
    @ApiOperation(value = "修改资源", notes = "修改资源")
    @RequiresPermissions("sys/sysResource/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody SysResourceDTO sysResourceDTO){
     	try {
     		//sysResourceDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysResourceService.updateById(sysResourceDTO);
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
    @ApiOperation(value = "批量删除资源", notes = "删除资源")
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys/sysResource/delete")
    public ResultMap delete(@PathVariable("id") Long id){
    	try {
	        Boolean b = iSysResourceService.removeById(id);
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
    @ApiOperation(value = "批量删除资源", notes = "删除资源")
    @PostMapping("/delete")
    @RequiresPermissions("sys/sysResource/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids){
	    try {
	        Boolean b = iSysResourceService.removeByIds(Arrays.asList(ids));
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
    @ApiOperation(value = "修改资源", notes = "修改资源")
    @RequiresPermissions("sys/sysResource/list")
    @GetMapping("/list")
    public ResultMap list(@RequestBody SysResourceDTO sysResourceDTO ){
        List<SysResourceDTO> list =  iSysResourceService.list(sysResourceDTO);
        return ResultMap.ok().put("list", list);
    }

    /**
    * 分页查询
    */
    @ApiOperation(value = "修改资源", notes = "修改资源")
    @RequiresPermissions("sys/sysResource/page")
    @GetMapping("/page")
    public ResultMap page(@RequestParam Map<String, Object> params){
        PageInfoVO<SysResourceDTO> page = iSysResourceService.page(params);
    	
        return ResultMap.ok().put("page", page);
    }


}

