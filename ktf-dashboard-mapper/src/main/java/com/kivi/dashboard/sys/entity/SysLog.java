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
 * 系统日志
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_sys_log")
public class SysLog extends Model<SysLog> {

	private static final long	serialVersionUID	= 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long				id;
	/**
	 * 客户姓名
	 */
	@TableField("user_name")
	private String				userName;
	/**
	 * 用户类型
	 */
	@TableField("login_name")
	private String				loginName;
	/**
	 * 角色名
	 */
	@TableField("user_type")
	private Integer				userType;
	/**
	 * 日志类型（0:系统日志，1：操作日志）
	 */
	@TableField("type")
	private Integer				type;
	/**
	 * 用户操作
	 */
	@TableField("operation")
	private String				operation;
	/**
	 * 类名
	 */
	@TableField("class_name")
	private String				className;
	/**
	 * 请求方法
	 */
	@TableField("method")
	private String				method;
	/**
	 * 请求参数
	 */
	@TableField("params")
	private String				params;
	/**
	 * 执行时长
	 */
	@TableField("time")
	private Long				time;
	/**
	 * 客户端ip
	 */
	@TableField("client_ip")
	private String				clientIp;
	@TableField("result")
	private String				result;
	/**
	 * 创建时间
	 */
	@TableField(value = "gmt_create")
	private LocalDateTime		gmtCreate;
	/**
	 * 更新时间
	 */
	@TableField(value = "gmt_update", fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime		gmtUpdate;
	/**
	 * 操作签名
	 */
	@TableField("operation_sign")
	private String				operationSign;

	/**
	 * 日志操作管理记录ID
	 */
	@TableField("record_ids")
	private String				recordIds;

	@TableField("enterprise_id")
	private Long				enterpriseId;

	public static final String	DB_ID				= "id";
	public static final String	ID					= "id";
	public static final String	DB_USER_NAME		= "user_name";
	public static final String	USER_NAME			= "userName";
	public static final String	DB_LOGIN_NAME		= "login_name";
	public static final String	LOGIN_NAME			= "loginName";
	public static final String	DB_USER_TYPE		= "user_type";
	public static final String	USER_TYPE			= "userType";
	public static final String	DB_TYPE				= "type";
	public static final String	TYPE				= "type";
	public static final String	DB_OPERATION		= "operation";
	public static final String	OPERATION			= "operation";
	public static final String	DB_CLASS_NAME		= "class_name";
	public static final String	CLASS_NAME			= "className";
	public static final String	DB_METHOD			= "method";
	public static final String	METHOD				= "method";
	public static final String	DB_PARAMS			= "params";
	public static final String	PARAMS				= "params";
	public static final String	DB_TIME				= "time";
	public static final String	TIME				= "time";
	public static final String	DB_CLIENT_IP		= "client_ip";
	public static final String	CLIENT_IP			= "clientIp";
	public static final String	DB_RESULT			= "result";
	public static final String	RESULT				= "result";
	public static final String	DB_GMT_CREATE		= "gmt_create";
	public static final String	GMT_CREATE			= "gmtCreate";
	public static final String	DB_GMT_UPDATE		= "gmt_update";
	public static final String	GMT_UPDATE			= "gmtUpdate";
	public static final String	DB_OPERATION_SIGN	= "operation_sign";
	public static final String	OPERATION_SIGN		= "operationSign";
	public static final String	DB_ENTERPRISE_ID	= "enterprise_id";
	public static final String	ENTERPRISE_ID		= "enterpriseId";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
