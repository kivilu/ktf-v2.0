package com.kivi.dashboard.sys.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kivi.dashboard.base.DashboardController;
import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.shiro.ShiroUser;
// import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.sys.dto.SysLogDTO;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.properties.KtfSwaggerProperties;
import com.kivi.framework.vo.page.PageInfoVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@ConditionalOnProperty(
		prefix = KtfSwaggerProperties.PREFIX,
		value = "enable-sys-api",
		havingValue = "true",
		matchIfMissing = false)
@Api(tags = { "系统日志" })
@RestController
@RequestMapping("/sys/log")
public class SysLogController extends DashboardController {

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "分页查询系统日志", notes = "分页查询系统日志")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "keyword",
					dataType = "string",
					value = "用户名称，可选，模糊匹配",
					paramType = "query",
					allowEmptyValue = true),
			@ApiImplicitParam(
					name = SysLogDTO.OPERATION,
					dataType = "string",
					value = "操作名称，可选，模糊匹配",
					paramType = "query",
					allowEmptyValue = true),
			@ApiImplicitParam(
					name = SysLogDTO.TYPE,
					dataType = "integer",
					value = "日志类型，可选，（0:系统日志，1：操作日志）",
					paramType = "query",
					allowEmptyValue = true),
			@ApiImplicitParam(
					name = "startTime",
					dataType = "string",
					value = "开始时间，可选，格式：yyyy-mm-dd",
					paramType = "query",
					allowEmptyValue = true),
			@ApiImplicitParam(
					name = "endTime",
					dataType = "string",
					value = "结束时间，可选，格式：yyyy-mm-dd",
					paramType = "query",
					allowEmptyValue = true),
			@ApiImplicitParam(
					name = "page",
					dataType = "integer",
					value = "当前页，可选，默认值：1",
					paramType = "query",
					allowEmptyValue = true),
			@ApiImplicitParam(
					name = "limit",
					dataType = "integer",
					value = "每页大小，可选，默认值：10",
					paramType = "query",
					allowEmptyValue = true) })
	@RequiresPermissions("sys/log/page")
	@GetMapping("/page")
	public ResultMap page(@ApiIgnore @RequestParam(required = false) Map<String, Object> params) {
		ShiroUser shiroUser = ShiroKit.getUser();
		if (shiroUser.getId() != KtfConstant.SUPER_ADMIN) {
			params.put("enterpriseId", ShiroKit.getUser().getCorpId());
		}
		PageInfoVO<SysLogDTO> page = sysLogService().page(params);

		return ResultMap.ok().data(page);
	}

}
