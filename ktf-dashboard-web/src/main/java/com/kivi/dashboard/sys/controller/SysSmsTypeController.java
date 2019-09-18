package com.kivi.dashboard.sys.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.web.bind.annotation.*;

import com.kivi.dashboard.sys.service.ISysSmsTypeService;
import com.kivi.dashboard.model.ResultMap;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.sys.dto.SysSmsTypeDTO;
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
 * 消息类型与用户关系 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
 
 
@Api(tags = {"消息类型与用户关系"})
@RestController
@RequestMapping("/sys/sysSmsType")
@Slf4j
public class SysSmsTypeController extends BaseController {
	
    
    @Autowired
    private ISysSmsTypeService iSysSmsTypeService;

	@ApiOperation(value = "消息类型与用户关系信息", notes = "消息类型与用户关系信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys/sysSmsType/info")
    public ResultMap info(@PathVariable("id") String id) {
    	SysSmsTypeDTO dto = iSysSmsTypeService.getDTOById(NumberUtil.toLongObject(id, -1L));
        return ResultMap.ok().put("SysSmsType", dto);
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增消息类型与用户关系", notes = "新增消息类型与用户关系")
    @RequiresPermissions("sys/sysSmsType/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody SysSmsTypeDTO sysSmsTypeDTO){
    	try {
    		//sysSmsTypeDTO.setCreateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysSmsTypeService.save(sysSmsTypeDTO);
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
    @ApiOperation(value = "修改消息类型与用户关系", notes = "修改消息类型与用户关系")
    @RequiresPermissions("sys/sysSmsType/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody SysSmsTypeDTO sysSmsTypeDTO){
     	try {
     		//sysSmsTypeDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysSmsTypeService.updateById(sysSmsTypeDTO);
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
    @ApiOperation(value = "批量删除消息类型与用户关系", notes = "删除消息类型与用户关系")
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys/sysSmsType/delete")
    public ResultMap delete(@PathVariable("id") Long id){
    	try {
	        Boolean b = iSysSmsTypeService.removeById(id);
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
    @ApiOperation(value = "批量删除消息类型与用户关系", notes = "删除消息类型与用户关系")
    @PostMapping("/delete")
    @RequiresPermissions("sys/sysSmsType/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids){
	    try {
	        Boolean b = iSysSmsTypeService.removeByIds(Arrays.asList(ids));
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
    @ApiOperation(value = "修改消息类型与用户关系", notes = "修改消息类型与用户关系")
    @RequiresPermissions("sys/sysSmsType/list")
    @GetMapping("/list")
    public ResultMap list(@RequestBody SysSmsTypeDTO sysSmsTypeDTO ){
        List<SysSmsTypeDTO> list =  iSysSmsTypeService.list(sysSmsTypeDTO);
        return ResultMap.ok().put("list", list);
    }

    /**
    * 分页查询
    */
    @ApiOperation(value = "修改消息类型与用户关系", notes = "修改消息类型与用户关系")
    @RequiresPermissions("sys/sysSmsType/page")
    @GetMapping("/page")
    public ResultMap page(@RequestParam Map<String, Object> params){
        PageInfoVO<SysSmsTypeDTO> page = iSysSmsTypeService.page(params);
    	
        return ResultMap.ok().put("page", page);
    }


}

