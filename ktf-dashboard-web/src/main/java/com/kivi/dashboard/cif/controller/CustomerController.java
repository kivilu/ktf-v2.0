package com.kivi.dashboard.cif.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.web.bind.annotation.*;

import com.kivi.dashboard.cif.service.ICustomerService;
import com.kivi.dashboard.model.ResultMap;
//import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.cif.dto.CustomerDTO;
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
 * 客户信息 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
 
 
@Api(tags = {"客户信息"})
@RestController
@RequestMapping("/cif/customer")
@Slf4j
public class CustomerController extends BaseController {
	
    
    @Autowired
    private ICustomerService iCustomerService;

	@ApiOperation(value = "客户信息信息", notes = "客户信息信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("cif/customer/info")
    public ResultMap info(@PathVariable("id") String id) {
    	CustomerDTO dto = iCustomerService.getDTOById(NumberUtil.toLongObject(id, -1L));
        return ResultMap.ok().put("Customer", dto);
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增客户信息", notes = "新增客户信息")
    @RequiresPermissions("cif/customer/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody CustomerDTO customerDTO){
    	try {
    		//customerDTO.setCreateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iCustomerService.save(customerDTO);
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
    @ApiOperation(value = "修改客户信息", notes = "修改客户信息")
    @RequiresPermissions("cif/customer/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody CustomerDTO customerDTO){
     	try {
     		//customerDTO.setUpdateUser(ShiroKit.getUser().getLoginName());
	        Boolean b = iCustomerService.updateById(customerDTO);
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
    @ApiOperation(value = "批量删除客户信息", notes = "删除客户信息")
    @PostMapping("/delete/{id}")
    @RequiresPermissions("cif/customer/delete")
    public ResultMap delete(@PathVariable("id") Long id){
    	try {
	        Boolean b = iCustomerService.removeById(id);
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
    @ApiOperation(value = "批量删除客户信息", notes = "删除客户信息")
    @PostMapping("/delete")
    @RequiresPermissions("cif/customer/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids){
	    try {
	        Boolean b = iCustomerService.removeByIds(Arrays.asList(ids));
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
    @ApiOperation(value = "修改客户信息", notes = "修改客户信息")
    @RequiresPermissions("cif/customer/list")
    @GetMapping("/list")
    public ResultMap list(@RequestBody CustomerDTO customerDTO ){
        List<CustomerDTO> list =  iCustomerService.list(customerDTO);
        return ResultMap.ok().put("list", list);
    }

    /**
    * 分页查询
    */
    @ApiOperation(value = "修改客户信息", notes = "修改客户信息")
    @RequiresPermissions("cif/customer/page")
    @GetMapping("/page")
    public ResultMap page(@RequestParam Map<String, Object> params){
        PageInfoVO<CustomerDTO> page = iCustomerService.page(params);
    	
        return ResultMap.ok().put("page", page);
    }


}
