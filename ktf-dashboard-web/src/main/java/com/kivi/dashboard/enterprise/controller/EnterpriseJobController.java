package com.kivi.dashboard.enterprise.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.web.bind.annotation.*;

import com.kivi.dashboard.enterprise.service.IEnterpriseJobService;
import com.kivi.dashboard.model.ResultMap;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.enterprise.dto.EnterpriseJobDTO;
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
 * 企业职务配置 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
 
 
@Api(tags = {"企业职务配置"})
@RestController
@RequestMapping("/enterprise/enterpriseJob")
@Slf4j
public class EnterpriseJobController extends BaseController {
	
    
    @Autowired
    private IEnterpriseJobService iEnterpriseJobService;

	@ApiOperation(value = "企业职务配置信息", notes = "企业职务配置信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("enterprise/enterpriseJob/info")
    public ResultMap info(@PathVariable("id") String id) {
    	EnterpriseJobDTO dto = iEnterpriseJobService.getDTOById(NumberUtil.toLongObject(id, -1L));
        return ResultMap.ok().put("EnterpriseJob", dto);
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增企业职务配置", notes = "新增企业职务配置")
    @RequiresPermissions("enterprise/enterpriseJob/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody EnterpriseJobDTO enterpriseJobDTO){
    	try {
    		//enterpriseJobDTO.setCreateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iEnterpriseJobService.save(enterpriseJobDTO);
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
    @ApiOperation(value = "修改企业职务配置", notes = "修改企业职务配置")
    @RequiresPermissions("enterprise/enterpriseJob/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody EnterpriseJobDTO enterpriseJobDTO){
     	try {
     		//enterpriseJobDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iEnterpriseJobService.updateById(enterpriseJobDTO);
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
    @ApiOperation(value = "批量删除企业职务配置", notes = "删除企业职务配置")
    @PostMapping("/delete/{id}")
    @RequiresPermissions("enterprise/enterpriseJob/delete")
    public ResultMap delete(@PathVariable("id") Long id){
    	try {
	        Boolean b = iEnterpriseJobService.removeById(id);
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
    @ApiOperation(value = "批量删除企业职务配置", notes = "删除企业职务配置")
    @PostMapping("/delete")
    @RequiresPermissions("enterprise/enterpriseJob/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids){
	    try {
	        Boolean b = iEnterpriseJobService.removeByIds(Arrays.asList(ids));
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
    @ApiOperation(value = "修改企业职务配置", notes = "修改企业职务配置")
    @RequiresPermissions("enterprise/enterpriseJob/list")
    @GetMapping("/list")
    public ResultMap list(@RequestBody EnterpriseJobDTO enterpriseJobDTO ){
        List<EnterpriseJobDTO> list =  iEnterpriseJobService.list(enterpriseJobDTO);
        return ResultMap.ok().put("list", list);
    }

    /**
    * 分页查询
    */
    @ApiOperation(value = "修改企业职务配置", notes = "修改企业职务配置")
    @RequiresPermissions("enterprise/enterpriseJob/page")
    @GetMapping("/page")
    public ResultMap page(@RequestParam Map<String, Object> params){
        PageInfoVO<EnterpriseJobDTO> page = iEnterpriseJobService.page(params);
    	
        return ResultMap.ok().put("page", page);
    }


}

