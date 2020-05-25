package com.kivi.dashboard.sys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

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
@TableName("ktf_sys_user")
public class SysUser extends Model<SysUser> {

	private static final long	serialVersionUID	= 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long				id;
	/**
	 * 客户ID
	 */
	@TableField("cif_id")
	private Long				cifId;
	/**
	 * 登录类型，00：手机号，01：邮箱，02：用户名，03：微信，04：支付宝，05：微博
	 */
	@TableField("login_mode")
	private String				loginMode;
	/**
	 * 登陆名
	 */
	@TableField("login_name")
	private String				loginName;
	/**
	 * 用户类别（0：超级管理员，1：企业用户，2：监管用户）
	 */
	@TableField("user_type")
	private Integer				userType;
	/**
	 * 应用ID，默认值0
	 */
	@TableField("application_id")
	private Long				applicationId;
	/**
	 * 用户状态(0：正常，1：不正常)
	 */
	@TableField("status")
	private Integer				status;
	/**
	 * 过期字段（0-不过期，1-过期）
	 */
	@TableField("expired")
	private Integer				expired;
	/**
	 * 所属企业
	 */
	@TableField("enterprise_id")
	private Long				enterpriseId;
	/**
	 * 所属部门
	 */
	@TableField("department_id")
	private Long				departmentId;
	/**
	 * 用户职务
	 */
	@TableField("job_id")
	private Long				jobId;
	/**
	 * 是否领导（0-是，1-否）
	 */
	@TableField("is_leader")
	private Integer				isLeader;
	/**
	 * 记录创建时间
	 */
	@TableField(value = "gmt_create", fill = FieldFill.INSERT)
	private LocalDateTime		gmtCreate;
	/**
	 * 记录最后修改时间
	 */
	@TableField(value = "gmt_update", fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime		gmtUpdate;
	/**
	 * 记录创建用户ID
	 */
	@TableField("create_user_id")
	private Long				createUserId;

	public static final String	DB_ID				= "id";
	public static final String	ID					= "id";
	public static final String	DB_CIF_ID			= "cif_id";
	public static final String	CIF_ID				= "cifId";
	public static final String	DB_LOGIN_MODE		= "login_mode";
	public static final String	LOGIN_MODE			= "loginMode";
	public static final String	DB_LOGIN_NAME		= "login_name";
	public static final String	LOGIN_NAME			= "loginName";
	public static final String	DB_USER_TYPE		= "user_type";
	public static final String	USER_TYPE			= "userType";
	public static final String	DB_APPLICATION_ID	= "application_id";
	public static final String	APPLICATION_ID		= "applicationId";
	public static final String	DB_STATUS			= "status";
	public static final String	STATUS				= "status";
	public static final String	DB_EXPIRED			= "expired";
	public static final String	EXPIRED				= "expired";
	public static final String	DB_ENTERPRISE_ID	= "enterprise_id";
	public static final String	ENTERPRISE_ID		= "enterpriseId";
	public static final String	DB_DEPARTMENT_ID	= "department_id";
	public static final String	DEPARTMENT_ID		= "departmentId";
	public static final String	DB_JOB_ID			= "job_id";
	public static final String	JOB_ID				= "jobId";
	public static final String	DB_IS_LEADER		= "is_leader";
	public static final String	IS_LEADER			= "isLeader";
	public static final String	DB_GMT_CREATE		= "gmt_create";
	public static final String	GMT_CREATE			= "gmtCreate";
	public static final String	DB_GMT_UPDATE		= "gmt_update";
	public static final String	GMT_UPDATE			= "gmtUpdate";
	public static final String	DB_CREATE_USER_ID	= "create_user_id";
	public static final String	CREATE_USER_ID		= "createUserId";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
