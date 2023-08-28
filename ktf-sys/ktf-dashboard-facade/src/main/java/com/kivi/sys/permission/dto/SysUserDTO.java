package com.kivi.sys.permission.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

@ApiModel(value = "SysUserDTO对象", description = "用户")
public class SysUserDTO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(value = "主键id")
	private Long				id;

	@ApiModelProperty(value = "用户类型：0：超级管理员，1：企业用户，2：监管用户，3：个人用户")
	private Integer				userType;

	@ApiModelProperty(value = "登录类型，00：手机号，01：邮箱，02：用户名，03：微信，04：支付宝，05：微博")
	private String				loginMode;

	@ApiModelProperty(value = "登陆名")
	private String				loginName;

	@ApiModelProperty(value = "密码")
	private String				password;

	@ApiModelProperty(value = "用户名")
	private String				name;

	@ApiModelProperty(value = "头像")
	private String				avatar;

	@ApiModelProperty(value = " 性别")
	private Integer				sex;

	@ApiModelProperty(value = "手机号")
	private String				phone;

	@ApiModelProperty(value = "邮箱")
	private String				email;

	@ApiModelProperty(value = "用户状态(0：正常，1：不正常)")
	private Integer				status;

	@ApiModelProperty(value = "所属企业")
	private Long				orgId;

	@ApiModelProperty(value = "所属部门")
	private Long				deptId;

	@ApiModelProperty(value = "用户职务")
	private Long				titleId;

	/**
	 * 角色Id列表
	 */
	@ApiModelProperty(value = "角色Id列表")
	private List<Long>			roleIds;

	/**
	 * 用户拥有的企业Id列表
	 */
	@ApiModelProperty(value = "用户拥有的企业Id列表")
	private List<Long>			orgIds;

	private LocalDateTime		gmtCreate;

	private LocalDateTime		gmtUpdate;

	@ApiModelProperty(value = "上次登录时间")
	private LocalDateTime		lastTime;

	// 内部使用属性
	@ApiModelProperty(hidden = true)
	private Long				createUserId;

	@ApiModelProperty(hidden = true)
	private Long				createRoleId;

	@ApiModelProperty(hidden = true)
	private Long				cifId;

	// 是否校验角色的创建用户ID
	@ApiModelProperty(hidden = true)
	private Boolean				checkRoleCreateId	= true;

	public static final String	ID					= "id";
	public static final String	LOGIN_MODE			= "loginMode";
	public static final String	LOGIN_NAME			= "loginName";
	public static final String	USER_NAME			= "userName";
	public static final String	USER_TYPE			= "userType";
	public static final String	STATUS				= "status";
	public static final String	EXPIRED				= "expired";
	public static final String	ORG_ID				= "orgId";
	public static final String	DEPT_ID				= "deptId";
	public static final String	TITLE_ID			= "titleId";
	public static final String	CREATE_ROLE_ID		= "createRoleId";
}
