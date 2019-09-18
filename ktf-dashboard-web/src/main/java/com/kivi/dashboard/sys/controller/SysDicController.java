package com.kivi.dashboard.sys.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.web.bind.annotation.*;

import com.kivi.dashboard.sys.service.ISysDicService;
import com.kivi.dashboard.model.ResultMap;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.sys.dto.SysDicDTO;
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
 * 数据字典 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
 
 
@Api(tags = {"数据字典"})
@RestController
@RequestMapping("/sys/sysDic")
@Slf4j
public class SysDicController extends BaseController {
	
    
    @Autowired
    private ISysDicService iSysDicService;

	@ApiOperation(value = "数据字典信息", notes = "数据字典信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys/sysDic/info")
    public ResultMap info(@PathVariable("id") String id) {
    	SysDicDTO dto = iSysDicService.getDTOById(NumberUtil.toLongObject(id, -1L));
        return ResultMap.ok().put("SysDic", dto);
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增数据字典", notes = "新增数据字典")
    @RequiresPermissions("sys/sysDic/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody SysDicDTO sysDicDTO){
    	try {
    		//sysDicDTO.setCreateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysDicService.save(sysDicDTO);
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
    @ApiOperation(value = "修改数据字典", notes = "修改数据字典")
    @RequiresPermissions("sys/sysDic/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody SysDicDTO sysDicDTO){
     	try {
     		//sysDicDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysDicService.updateById(sysDicDTO);
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
    @ApiOperation(value = "批量删除数据字典", notes = "删除数据字典")
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys/sysDic/delete")
    public ResultMap delete(@PathVariable("id") Long id){
    	try {
	        Boolean b = iSysDicService.removeById(id);
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
    @ApiOperation(value = "批量删除数据字典", notes = "删除数据字典")
    @PostMapping("/delete")
    @RequiresPermissions("sys/sysDic/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids){
	    try {
	        Boolean b = iSysDicService.removeByIds(Arrays.asList(ids));
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
    @ApiOperation(value = "修改数据字典", notes = "修改数据字典")
    @RequiresPermissions("sys/sysDic/list")
    @GetMapping("/list")
    public ResultMap list(@RequestBody SysDicDTO sysDicDTO ){
        List<SysDicDTO> list =  iSysDicService.list(sysDicDTO);
        return ResultMap.ok().put("list", list);
    }

    /**
    * 分页查询
    */
    @ApiOperation(value = "修改数据字典", notes = "修改数据字典")
    @RequiresPermissions("sys/sysDic/page")
    @GetMapping("/page")
    public ResultMap page(@RequestParam Map<String, Object> params){
        PageInfoVO<SysDicDTO> page = iSysDicService.page(params);
    	
        return ResultMap.ok().put("page", page);
    }


}

