package com.kivi.dashboard.sys.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.web.bind.annotation.*;

import com.kivi.dashboard.sys.service.ISysSmsRecordService;
import com.kivi.dashboard.model.ResultMap;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.sys.dto.SysSmsRecordDTO;
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
 * 消息记录 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
 
 
@Api(tags = {"消息记录"})
@RestController
@RequestMapping("/sys/sysSmsRecord")
@Slf4j
public class SysSmsRecordController extends BaseController {
	
    
    @Autowired
    private ISysSmsRecordService iSysSmsRecordService;

	@ApiOperation(value = "消息记录信息", notes = "消息记录信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys/sysSmsRecord/info")
    public ResultMap info(@PathVariable("id") String id) {
    	SysSmsRecordDTO dto = iSysSmsRecordService.getDTOById(NumberUtil.toLongObject(id, -1L));
        return ResultMap.ok().put("SysSmsRecord", dto);
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增消息记录", notes = "新增消息记录")
    @RequiresPermissions("sys/sysSmsRecord/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody SysSmsRecordDTO sysSmsRecordDTO){
    	try {
    		//sysSmsRecordDTO.setCreateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysSmsRecordService.save(sysSmsRecordDTO);
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
    @ApiOperation(value = "修改消息记录", notes = "修改消息记录")
    @RequiresPermissions("sys/sysSmsRecord/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody SysSmsRecordDTO sysSmsRecordDTO){
     	try {
     		//sysSmsRecordDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysSmsRecordService.updateById(sysSmsRecordDTO);
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
    @ApiOperation(value = "批量删除消息记录", notes = "删除消息记录")
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys/sysSmsRecord/delete")
    public ResultMap delete(@PathVariable("id") Long id){
    	try {
	        Boolean b = iSysSmsRecordService.removeById(id);
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
    @ApiOperation(value = "批量删除消息记录", notes = "删除消息记录")
    @PostMapping("/delete")
    @RequiresPermissions("sys/sysSmsRecord/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids){
	    try {
	        Boolean b = iSysSmsRecordService.removeByIds(Arrays.asList(ids));
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
    @ApiOperation(value = "修改消息记录", notes = "修改消息记录")
    @RequiresPermissions("sys/sysSmsRecord/list")
    @GetMapping("/list")
    public ResultMap list(@RequestBody SysSmsRecordDTO sysSmsRecordDTO ){
        List<SysSmsRecordDTO> list =  iSysSmsRecordService.list(sysSmsRecordDTO);
        return ResultMap.ok().put("list", list);
    }

    /**
    * 分页查询
    */
    @ApiOperation(value = "修改消息记录", notes = "修改消息记录")
    @RequiresPermissions("sys/sysSmsRecord/page")
    @GetMapping("/page")
    public ResultMap page(@RequestParam Map<String, Object> params){
        PageInfoVO<SysSmsRecordDTO> page = iSysSmsRecordService.page(params);
    	
        return ResultMap.ok().put("page", page);
    }


}

