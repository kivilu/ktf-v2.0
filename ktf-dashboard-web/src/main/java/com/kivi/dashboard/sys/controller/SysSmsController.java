package com.kivi.dashboard.sys.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.web.bind.annotation.*;

import com.kivi.dashboard.sys.service.ISysSmsService;
import com.kivi.dashboard.model.ResultMap;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.sys.dto.SysSmsDTO;
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
 * 消息 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
 
 
@Api(tags = {"消息"})
@RestController
@RequestMapping("/sys/sysSms")
@Slf4j
public class SysSmsController extends BaseController {
	
    
    @Autowired
    private ISysSmsService iSysSmsService;

	@ApiOperation(value = "消息信息", notes = "消息信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys/sysSms/info")
    public ResultMap info(@PathVariable("id") String id) {
    	SysSmsDTO dto = iSysSmsService.getDTOById(NumberUtil.toLongObject(id, -1L));
        return ResultMap.ok().put("SysSms", dto);
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增消息", notes = "新增消息")
    @RequiresPermissions("sys/sysSms/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody SysSmsDTO sysSmsDTO){
    	try {
    		//sysSmsDTO.setCreateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysSmsService.save(sysSmsDTO);
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
    @ApiOperation(value = "修改消息", notes = "修改消息")
    @RequiresPermissions("sys/sysSms/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody SysSmsDTO sysSmsDTO){
     	try {
     		//sysSmsDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysSmsService.updateById(sysSmsDTO);
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
    @ApiOperation(value = "批量删除消息", notes = "删除消息")
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys/sysSms/delete")
    public ResultMap delete(@PathVariable("id") Long id){
    	try {
	        Boolean b = iSysSmsService.removeById(id);
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
    @ApiOperation(value = "批量删除消息", notes = "删除消息")
    @PostMapping("/delete")
    @RequiresPermissions("sys/sysSms/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids){
	    try {
	        Boolean b = iSysSmsService.removeByIds(Arrays.asList(ids));
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
    @ApiOperation(value = "修改消息", notes = "修改消息")
    @RequiresPermissions("sys/sysSms/list")
    @GetMapping("/list")
    public ResultMap list(@RequestBody SysSmsDTO sysSmsDTO ){
        List<SysSmsDTO> list =  iSysSmsService.list(sysSmsDTO);
        return ResultMap.ok().put("list", list);
    }

    /**
    * 分页查询
    */
    @ApiOperation(value = "修改消息", notes = "修改消息")
    @RequiresPermissions("sys/sysSms/page")
    @GetMapping("/page")
    public ResultMap page(@RequestParam Map<String, Object> params){
        PageInfoVO<SysSmsDTO> page = iSysSmsService.page(params);
    	
        return ResultMap.ok().put("page", page);
    }


}

