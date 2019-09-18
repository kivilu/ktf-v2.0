package com.kivi.dashboard.cif.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.web.bind.annotation.*;

import com.kivi.dashboard.cif.service.ICustomerAuthsService;
import com.kivi.dashboard.model.ResultMap;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.cif.dto.CustomerAuthsDTO;
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
 * 客户认证 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
 
 
@Api(tags = {"客户认证"})
@RestController
@RequestMapping("/cif/customerAuths")
@Slf4j
public class CustomerAuthsController extends BaseController {
	
    
    @Autowired
    private ICustomerAuthsService iCustomerAuthsService;

	@ApiOperation(value = "客户认证信息", notes = "客户认证信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("cif/customerAuths/info")
    public ResultMap info(@PathVariable("id") String id) {
    	CustomerAuthsDTO dto = iCustomerAuthsService.getDTOById(NumberUtil.toLongObject(id, -1L));
        return ResultMap.ok().put("CustomerAuths", dto);
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增客户认证", notes = "新增客户认证")
    @RequiresPermissions("cif/customerAuths/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody CustomerAuthsDTO customerAuthsDTO){
    	try {
    		//customerAuthsDTO.setCreateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iCustomerAuthsService.save(customerAuthsDTO);
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
    @ApiOperation(value = "修改客户认证", notes = "修改客户认证")
    @RequiresPermissions("cif/customerAuths/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody CustomerAuthsDTO customerAuthsDTO){
     	try {
     		//customerAuthsDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iCustomerAuthsService.updateById(customerAuthsDTO);
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
    @ApiOperation(value = "批量删除客户认证", notes = "删除客户认证")
    @PostMapping("/delete/{id}")
    @RequiresPermissions("cif/customerAuths/delete")
    public ResultMap delete(@PathVariable("id") Long id){
    	try {
	        Boolean b = iCustomerAuthsService.removeById(id);
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
    @ApiOperation(value = "批量删除客户认证", notes = "删除客户认证")
    @PostMapping("/delete")
    @RequiresPermissions("cif/customerAuths/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids){
	    try {
	        Boolean b = iCustomerAuthsService.removeByIds(Arrays.asList(ids));
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
    @ApiOperation(value = "修改客户认证", notes = "修改客户认证")
    @RequiresPermissions("cif/customerAuths/list")
    @GetMapping("/list")
    public ResultMap list(@RequestBody CustomerAuthsDTO customerAuthsDTO ){
        List<CustomerAuthsDTO> list =  iCustomerAuthsService.list(customerAuthsDTO);
        return ResultMap.ok().put("list", list);
    }

    /**
    * 分页查询
    */
    @ApiOperation(value = "修改客户认证", notes = "修改客户认证")
    @RequiresPermissions("cif/customerAuths/page")
    @GetMapping("/page")
    public ResultMap page(@RequestParam Map<String, Object> params){
        PageInfoVO<CustomerAuthsDTO> page = iCustomerAuthsService.page(params);
    	
        return ResultMap.ok().put("page", page);
    }


}

