package com.kivi.dashboard.sys.dto;

import java.io.Serializable;
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

	@ApiModelProperty(value = "客户ID")
	private Long				cifId;

	@ApiModelProperty(value = "登录类型，00：手机号，01：邮箱，02：用户名，03：微信，04：支付宝，05：微博")
	private String				loginMode;

	@ApiModelProperty(value = "登陆名")
	private String				loginName;

	@ApiModelProperty(value = "密码")
	private String				password;

	@ApiModelProperty(value = "用户名")
	private String				name;

	@ApiModelProperty(value = " 性别")
	private Integer				sex;

	@ApiModelProperty(value = "用户类别（0：超级管理员，1：系统管理员、2：业务管理员、3：密钥操作员、4：密钥审核员、5：审计管理员、6：审计员）")
	private Integer				userType;

	@ApiModelProperty(value = "应用ID，默认值0")
	private Long				applicationId;

	@ApiModelProperty(value = "手机号")
	private String				phone;

	@ApiModelProperty(value = "邮箱")
	private String				email;

	@ApiModelProperty(value = "用户状态(0：正常，1：不正常)")
	private Integer				status;

	@ApiModelProperty(value = "过期字段（0-不过期，1-过期）")
	private Integer				expired;

	@ApiModelProperty(value = "所属企业")
	private Long				enterpriseId;

	@ApiModelProperty(value = "所属部门")
	private Long				departmentId;

	@ApiModelProperty(value = "用户职务")
	private Long				jobId;

	@ApiModelProperty(value = "职务")
	private String				post;

	@ApiModelProperty(value = "是否领导（0-是，1-否）")
	private Integer				isLeader;

	@ApiModelProperty(value = "记录创建用户ID")
	private Long				createUserId;

	/**
	 * 角色Id列表
	 */
	@ApiModelProperty(value = "角色Id列表")
	private List<Long>			roleIdList;

	/**
	 * 用户拥有的企业Id列表
	 */
	@ApiModelProperty(value = "用户拥有的企业Id列表")
	private List<Long>			enterpriseIdList;

	public static final String	ID					= "id";
	public static final String	CIF_ID				= "cifId";
	public static final String	LOGIN_MODE			= "loginMode";
	public static final String	LOGIN_NAME			= "loginName";
	public static final String	USER_NAME			= "userName";
	public static final String	USER_TYPE			= "userType";
	public static final String	APPLICATION_ID		= "applicationId";
	public static final String	STATUS				= "status";
	public static final String	EXPIRED				= "expired";
	public static final String	ENTERPRISE_ID		= "enterpriseId";
	public static final String	DEPARTMENT_ID		= "departmentId";
	public static final String	JOB_ID				= "jobId";
	public static final String	POST				= "post";
	public static final String	IS_LEADER			= "isLeader";
	public static final String	CREATE_USER_ID		= "createUserId";

}
