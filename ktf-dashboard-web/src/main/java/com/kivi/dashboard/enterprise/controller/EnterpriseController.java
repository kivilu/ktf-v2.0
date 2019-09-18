package com.kivi.dashboard.enterprise.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.web.bind.annotation.*;

import com.kivi.dashboard.enterprise.service.IEnterpriseService;
import com.kivi.dashboard.model.ResultMap;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.enterprise.dto.EnterpriseDTO;
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
 * 企业信息 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
 
 
@Api(tags = {"企业信息"})
@RestController
@RequestMapping("/enterprise/enterprise")
@Slf4j
public class EnterpriseController extends BaseController {
	
    
    @Autowired
    private IEnterpriseService iEnterpriseService;

	@ApiOperation(value = "企业信息信息", notes = "企业信息信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("enterprise/enterprise/info")
    public ResultMap info(@PathVariable("id") String id) {
    	EnterpriseDTO dto = iEnterpriseService.getDTOById(NumberUtil.toLongObject(id, -1L));
        return ResultMap.ok().put("Enterprise", dto);
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增企业信息", notes = "新增企业信息")
    @RequiresPermissions("enterprise/enterprise/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody EnterpriseDTO enterpriseDTO){
    	try {
    		//enterpriseDTO.setCreateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iEnterpriseService.save(enterpriseDTO);
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
    @ApiOperation(value = "修改企业信息", notes = "修改企业信息")
    @RequiresPermissions("enterprise/enterprise/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody EnterpriseDTO enterpriseDTO){
     	try {
     		//enterpriseDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iEnterpriseService.updateById(enterpriseDTO);
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
    @ApiOperation(value = "批量删除企业信息", notes = "删除企业信息")
    @PostMapping("/delete/{id}")
    @RequiresPermissions("enterprise/enterprise/delete")
    public ResultMap delete(@PathVariable("id") Long id){
    	try {
	        Boolean b = iEnterpriseService.removeById(id);
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
    @ApiOperation(value = "批量删除企业信息", notes = "删除企业信息")
    @PostMapping("/delete")
    @RequiresPermissions("enterprise/enterprise/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids){
	    try {
	        Boolean b = iEnterpriseService.removeByIds(Arrays.asList(ids));
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
    @ApiOperation(value = "修改企业信息", notes = "修改企业信息")
    @RequiresPermissions("enterprise/enterprise/list")
    @GetMapping("/list")
    public ResultMap list(@RequestBody EnterpriseDTO enterpriseDTO ){
        List<EnterpriseDTO> list =  iEnterpriseService.list(enterpriseDTO);
        return ResultMap.ok().put("list", list);
    }

    /**
    * 分页查询
    */
    @ApiOperation(value = "修改企业信息", notes = "修改企业信息")
    @RequiresPermissions("enterprise/enterprise/page")
    @GetMapping("/page")
    public ResultMap page(@RequestParam Map<String, Object> params){
        PageInfoVO<EnterpriseDTO> page = iEnterpriseService.page(params);
    	
        return ResultMap.ok().put("page", page);
    }


}

