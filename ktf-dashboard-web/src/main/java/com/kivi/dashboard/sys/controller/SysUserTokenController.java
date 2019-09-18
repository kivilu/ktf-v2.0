package com.kivi.dashboard.sys.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.web.bind.annotation.*;

import com.kivi.dashboard.sys.service.ISysUserTokenService;
import com.kivi.dashboard.model.ResultMap;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.sys.dto.SysUserTokenDTO;
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
 * 系统用户Token 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
 
 
@Api(tags = {"系统用户Token"})
@RestController
@RequestMapping("/sys/sysUserToken")
@Slf4j
public class SysUserTokenController extends BaseController {
	
    
    @Autowired
    private ISysUserTokenService iSysUserTokenService;

	@ApiOperation(value = "系统用户Token信息", notes = "系统用户Token信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys/sysUserToken/info")
    public ResultMap info(@PathVariable("id") String id) {
    	SysUserTokenDTO dto = iSysUserTokenService.getDTOById(NumberUtil.toLongObject(id, -1L));
        return ResultMap.ok().put("SysUserToken", dto);
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增系统用户Token", notes = "新增系统用户Token")
    @RequiresPermissions("sys/sysUserToken/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody SysUserTokenDTO sysUserTokenDTO){
    	try {
    		//sysUserTokenDTO.setCreateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysUserTokenService.save(sysUserTokenDTO);
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
    @ApiOperation(value = "修改系统用户Token", notes = "修改系统用户Token")
    @RequiresPermissions("sys/sysUserToken/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody SysUserTokenDTO sysUserTokenDTO){
     	try {
     		//sysUserTokenDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysUserTokenService.updateById(sysUserTokenDTO);
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
    @ApiOperation(value = "批量删除系统用户Token", notes = "删除系统用户Token")
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys/sysUserToken/delete")
    public ResultMap delete(@PathVariable("id") Long id){
    	try {
	        Boolean b = iSysUserTokenService.removeById(id);
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
    @ApiOperation(value = "批量删除系统用户Token", notes = "删除系统用户Token")
    @PostMapping("/delete")
    @RequiresPermissions("sys/sysUserToken/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids){
	    try {
	        Boolean b = iSysUserTokenService.removeByIds(Arrays.asList(ids));
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
    @ApiOperation(value = "修改系统用户Token", notes = "修改系统用户Token")
    @RequiresPermissions("sys/sysUserToken/list")
    @GetMapping("/list")
    public ResultMap list(@RequestBody SysUserTokenDTO sysUserTokenDTO ){
        List<SysUserTokenDTO> list =  iSysUserTokenService.list(sysUserTokenDTO);
        return ResultMap.ok().put("list", list);
    }

    /**
    * 分页查询
    */
    @ApiOperation(value = "修改系统用户Token", notes = "修改系统用户Token")
    @RequiresPermissions("sys/sysUserToken/page")
    @GetMapping("/page")
    public ResultMap page(@RequestParam Map<String, Object> params){
        PageInfoVO<SysUserTokenDTO> page = iSysUserTokenService.page(params);
    	
        return ResultMap.ok().put("page", page);
    }


}

