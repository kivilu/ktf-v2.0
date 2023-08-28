package com.kivi.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kivi.framework.model.ResultMap;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "DubboProvider接口", tags = { "DubboProvider接口" })
@RestController
@RequestMapping("/dubboProvider")
public class DubboProviderController {

	@Autowired

	@ApiOperation(value = "数据字典信息", notes = "数据字典信息")
	@GetMapping("/list")
	public ResultMap info() {

		return ResultMap.ok();
	}

}
