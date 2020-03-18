package com.kivi.dashboard.sys.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.web.bind.annotation.*;

import com.kivi.dashboard.sys.service.ISysFileService;
import com.kivi.dashboard.model.ResultMap;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.sys.dto.SysFileDTO;
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
 * 附件 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
 
 
@Api(tags = {"附件"})
@RestController
@RequestMapping("/sys/sysFile")
@Slf4j
public class SysFileController extends BaseController {
	
    
    @Autowired
    private ISysFileService iSysFileService;

	@ApiOperation(value = "附件信息", notes = "附件信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys/sysFile/info")
    public ResultMap info(@PathVariable("id") String id) {
    	SysFileDTO dto = iSysFileService.getDTOById(NumberUtil.toLongObject(id, -1L));
        return ResultMap.ok().put("SysFile", dto);
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增附件", notes = "新增附件")
    @RequiresPermissions("sys/sysFile/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody SysFileDTO sysFileDTO){
    	try {
    		//sysFileDTO.setCreateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysFileService.save(sysFileDTO);
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
    @ApiOperation(value = "修改附件", notes = "修改附件")
    @RequiresPermissions("sys/sysFile/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody SysFileDTO sysFileDTO){
     	try {
     		//sysFileDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iSysFileService.updateById(sysFileDTO);
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
    @ApiOperation(value = "批量删除附件", notes = "删除附件")
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys/sysFile/delete")
    public ResultMap delete(@PathVariable("id") Long id){
    	try {
	        Boolean b = iSysFileService.removeById(id);
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
    @ApiOperation(value = "批量删除附件", notes = "删除附件")
    @PostMapping("/delete")
    @RequiresPermissions("sys/sysFile/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids){
	    try {
	        Boolean b = iSysFileService.removeByIds(Arrays.asList(ids));
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
    @ApiOperation(value = "修改附件", notes = "修改附件")
    @RequiresPermissions("sys/sysFile/list")
    @GetMapping("/list")
    public ResultMap list(@RequestBody SysFileDTO sysFileDTO ){
        List<SysFileDTO> list =  iSysFileService.list(sysFileDTO);
        return ResultMap.ok().put("list", list);
    }

    /**
    * 分页查询
    */
    @ApiOperation(value = "修改附件", notes = "修改附件")
    @RequiresPermissions("sys/sysFile/page")
    @GetMapping("/page")
    public ResultMap page(@RequestParam Map<String, Object> params){
        PageInfoVO<SysFileDTO> page = iSysFileService.page(params);
    	
        return ResultMap.ok().put("page", page);
    }


}
