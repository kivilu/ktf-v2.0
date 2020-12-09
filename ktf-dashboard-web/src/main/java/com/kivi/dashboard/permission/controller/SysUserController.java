package com.kivi.dashboard.permission.controller;

import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kivi.dashboard.base.DashboardController;
import com.kivi.dashboard.permission.dto.SysUserDTO;
import com.kivi.dashboard.permission.entity.SysUser;
import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.shiro.ShiroUser;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.constant.enums.KtfIdentifyType;
import com.kivi.framework.constant.enums.KtfStatus;
import com.kivi.framework.constant.enums.UserType;
import com.kivi.framework.dto.JwtUserDTO;
import com.kivi.framework.form.PasswordForm;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.properties.KtfSwaggerProperties;
import com.kivi.framework.service.KtfTokenService;
import com.kivi.framework.vo.PasswordResetVO;
import com.kivi.framework.vo.UserVo;
import com.kivi.framework.vo.page.PageInfoVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户管理前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@ConditionalOnProperty(
		prefix = KtfSwaggerProperties.PREFIX,
		value = "enable-permission-api",
		havingValue = "true",
		matchIfMissing = false)
@Api(value = "用户管理", tags = { "用户管理" })
@RestController
@RequestMapping("/permission/user")
@Slf4j
public class SysUserController extends DashboardController {

	@Autowired
	private KtfTokenService ktfTokenService;

	@ApiOperation(value = "用户信息", notes = "用户信息")
	@GetMapping("/info")
	@RequiresPermissions("permission/user/info")
	public ResultMap self() {
		JwtUserDTO	jwtUser	= super.getJwtUser();
		SysUserDTO	user	= sysUserService().getDto(jwtUser.getId());
		return ResultMap.ok().data(user);
	}

	@ApiOperation(value = "用户信息", notes = "用户信息")
	@GetMapping("/info/{id}")
	@RequiresPermissions("permission/user/info")
	public ResultMap info(@PathVariable("id") Long id) {
		SysUserDTO user = sysUserService().getDto(id);
		return ResultMap.ok().data(user);
	}

	/**
	 * 所有用户列表
	 */
	@GetMapping("/page")
	@RequiresPermissions("permission/user/page")
	public ResultMap page(@RequestParam Map<String, Object> params) {
		// 只有超级管理员，才能查看所有管理员列表
		ShiroUser shiroUser = ShiroKit.getUser();

		if (shiroUser.getId() != KtfConstant.SUPER_ADMIN) {
			params.put("userId", ShiroKit.getUser().getId());
			if (shiroUser.getUserType() != UserType.SYS.value) {
				Long corpId = shiroUser.getCorpId();
				params.put(SysUserDTO.ORG_ID, corpId);
			}
		}

		PageInfoVO<SysUserDTO> page = sysUserService().page(params);

		return ResultMap.ok().data(page);
	}

	/**
	 * 修改登录用户密码
	 */
	@PostMapping("/password")
	@RequiresPermissions("permission/user/password")
	public ResultMap password(@Valid @RequestBody PasswordForm form) {
		ShiroUser	shiroUser	= ShiroKit.getUser();
		UserVo		userVo		= new UserVo();
		userVo.setId(shiroUser.getId());
		userVo.setPassword(form.getPassword());

		boolean ret = customerAuthsService().updateCredential(userVo, form.getNewPassword());
		if (!ret) {
			return ResultMap.error("修改密码失败");
		}

		// 修改用户状态：初始-->正常
		if (KtfStatus.INIT.code == ShiroKit.getUser().getStatus()) {
			SysUser entity = new SysUser();
			entity.setId(ShiroKit.getUser().getId());
			entity.setStatus(KtfStatus.ENABLED.code);
			sysUserService().updateById(entity);
		}

		// 注销token
		ktfTokenService.evictJwt(shiroUser.getId().toString());

		return ResultMap.ok("密码修改成功");
	}

	@ApiOperation(value = "用户密码重置", notes = "用户密码重置")
	@GetMapping("/passwordReset/{id}")
	public ResultMap restUserPassword(@Valid @RequestBody PasswordResetVO form) {

		return ResultMap.error(KtfError.E_NOT_IMPLEMENT, "待实现");
	}

	@ApiOperation(value = "用户密码重置", notes = "用户密码重置")
	@GetMapping("/passwordReset")
	public ResultMap restSelfPassword(@PathVariable("id") Long id) {
		SysUser entity = new SysUser();
		entity.setId(id);
		entity.setStatus(KtfStatus.INIT.code);
		sysUserService().updateById(entity);

		UserVo userVo = new UserVo();
		userVo.setId(id);
		customerAuthsService().updateCredential(userVo, null);

		return ResultMap.ok("重置成功");
	}

	/**
	 * 新增
	 */
	@ApiOperation(value = "新增用户", notes = "新增用户")
	@RequiresPermissions("permission/user/save")
	@PostMapping("/save")
	public ResultMap save(@Valid @RequestBody SysUserDTO dto) {
		try {
			LambdaQueryWrapper<SysUser> query = Wrappers.<SysUser>lambdaQuery().eq(SysUser::getLoginName,
					dto.getLoginName());
			if (sysUserService().count(query) > 0) {
				return ResultMap.error(KtfError.E_CONFLICT, "登录名已存在");
			}

			JwtUserDTO jwtUser = super.getJwtUser();
			dto.setLoginMode(KtfIdentifyType.USERNAME.text);
			dto.setCreateUserId(jwtUser.getId());

			Boolean b = sysUserService().save(dto);
			if (b)
				return ResultMap.ok("添加成功");
			else
				return ResultMap.error("添加失败");
		} catch (Exception e) {
			log.error("新增用户异常", e);
			return ResultMap.error("运行异常，请联系管理员");
		}
	}

	/**
	 * 修改
	 */
	@ApiOperation(value = "修改用户", notes = "修改用户")
	@RequiresPermissions("permission/user/update")
	@PostMapping("/update")
	public ResultMap update(@Valid @RequestBody SysUserDTO dto) {
		try {
			SysUser user = sysUserService().getById(dto.getId());
			if (user == null) {
				return ResultMap.error(KtfError.E_NOT_FOUND, "用户不存在");
			}

			dto.setCifId(user.getCifId());
			boolean b = sysUserService().update(dto);
			if (b) {
				return ResultMap.ok("编辑成功！");
			} else {
				return ResultMap.ok("编辑失败！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultMap.error("编辑失败，请联系管理员");
		}
	}

	/**
	 * 批量删除
	 */
	@ApiOperation(value = "批量删除用户", notes = "删除用户")
	@PostMapping("/delete")
	@RequiresPermissions("permission/user/delete")
	public ResultMap deleteBatchIds(@RequestBody Long[] ids) {
		try {
			if (ArrayUtils.contains(ids, KtfConstant.SUPER_ADMIN)) {
				return ResultMap.error("系统管理员不能删除");
			}
			if (ArrayUtils.contains(ids, ShiroKit.getUser().getId())) {
				return ResultMap.error("当前用户不能删除");
			}

			Boolean b = sysUserService().deleteBatch(ids);
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
	 * 用户选择树
	 *
	 * @return
	 */
//	@ApiOperation(value = "用户选择树", notes = "用户选择树")
//	@GetMapping("/getUserTree")
//	public ResultMap getUserTree() {
//		try {
//			List<Map<String, Object>>	list		= sysUserService().selectUserTree();
//			List<SelectNode>			nodeList	= list.stream().map(baseUser -> {
//														SelectNode node = new SelectNode();
//														node.setLabel(baseUser.get("name").toString());
//														node.setValue(baseUser.get("id").toString());
//														return node;
//													}).collect(Collectors.toList());
//
//			return ResultMap.ok().put("list", nodeList);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResultMap.error("运行异常，请联系管理员");
//		}
//	}

	/**
	 * 获取登录的用户信息
	 */
//	@GetMapping("/info")
//	public ResultMap info() {
//		ShiroUser	user			= ShiroKit.getUser();
//		OrgCorpDTO	enterpriseDTO	= orgCorpService().getDto(user.getEnterpriseId());
//		user.setEnterpriseName(enterpriseDTO.getName());
//		user.setPre(enterpriseDTO.getPrefix());
//		return ResultMap.ok().data(user);
//	}

}
