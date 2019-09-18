package com.kivi.dashboard.sys.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.web.bind.annotation.*;

import com.kivi.dashboard.sys.service.ISysUserEnterpriseService;
import com.kivi.dashboard.model.ResultMap;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.sys.dto.SysUserEnterpriseDTO;
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
 * 监管用户与企业关联 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
 
 
@Api(tags = {"监管用户与企业关联"})
@RestController
@RequestMapping("/sys/sysUserEnterprise")
@Slf4j
public class SysUserEnterpriseController extends BaseController {
	
    
    @Autowired
    private ISysUserEnterpriseService iSysUserEnterpriseService;

	@ApiOperation(value = "监管用户与企业关联信息", notes = "监管用户与企业关联信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys/sysUserEnterprise/info")
    public ResultMap info(@PathVariable("id") String id) {
    	SysUserEnterpriseDTO dto = iSysUserEnterpriseService.getDTOById(NumberUtil.toLongObject(id, -1L));
        return ResultMap.ok().put("SysUserEnterprise", dto);
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增监管用户与企业关联", notes = "新增监管用户与企业关联")
    @RequiresPermissions("sys/sysUserEnterprise/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody SysUserEnterpriseDTO sysUserEnterpriseDTO){
    	try {
    		//sysUserEnterpriseDTO.setCreateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysUserEnterpriseService.save(sysUserEnterpriseDTO);
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
    @ApiOperation(value = "修改监管用户与企业关联", notes = "修改监管用户与企业关联")
    @RequiresPermissions("sys/sysUserEnterprise/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody SysUserEnterpriseDTO sysUserEnterpriseDTO){
     	try {
     		//sysUserEnterpriseDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysUserEnterpriseService.updateById(sysUserEnterpriseDTO);
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
    @ApiOperation(value = "批量删除监管用户与企业关联", notes = "删除监管用户与企业关联")
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys/sysUserEnterprise/delete")
    public ResultMap delete(@PathVariable("id") Long id){
    	try {
	        Boolean b = iSysUserEnterpriseService.removeById(id);
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
    @ApiOperation(value = "批量删除监管用户与企业关联", notes = "删除监管用户与企业关联")
    @PostMapping("/delete")
    @RequiresPermissions("sys/sysUserEnterprise/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids){
	    try {
	        Boolean b = iSysUserEnterpriseService.removeByIds(Arrays.asList(ids));
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
    @ApiOperation(value = "修改监管用户与企业关联", notes = "修改监管用户与企业关联")
    @RequiresPermissions("sys/sysUserEnterprise/list")
    @GetMapping("/list")
    public ResultMap list(@RequestBody SysUserEnterpriseDTO sysUserEnterpriseDTO ){
        List<SysUserEnterpriseDTO> list =  iSysUserEnterpriseService.list(sysUserEnterpriseDTO);
        return ResultMap.ok().put("list", list);
    }

    /**
    * 分页查询
    */
    @ApiOperation(value = "修改监管用户与企业关联", notes = "修改监管用户与企业关联")
    @RequiresPermissions("sys/sysUserEnterprise/page")
    @GetMapping("/page")
    public ResultMap page(@RequestParam Map<String, Object> params){
        PageInfoVO<SysUserEnterpriseDTO> page = iSysUserEnterpriseService.page(params);
    	
        return ResultMap.ok().put("page", page);
    }


}

