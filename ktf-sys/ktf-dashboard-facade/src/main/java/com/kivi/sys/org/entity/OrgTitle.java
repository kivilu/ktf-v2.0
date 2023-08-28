package com.kivi.sys.org.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 企业职务配置
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_org_title")
public class OrgTitle extends Model<OrgTitle> {

	private static final long	serialVersionUID	= 1L;

	@TableId("id")
	private Long				id;
	/**
	 * 企业ID
	 */
	@TableField("corp_id")
	private Long				corpId;
	/**
	 * 企业部门表ID
	 */
	@TableField("dept_id")
	private Long				deptId;
	/**
	 * 职务代码
	 */
	@TableField("code")
	private String				code;
	/**
	 * 职务名称
	 */
	@TableField("name")
	private String				name;
	/**
	 * 记录创建时间
	 */
	@TableField(value = "gmt_create", fill = FieldFill.INSERT)
	private LocalDateTime		gmtCreate;
	/**
	 * 记录最后更新时间
	 */
	@TableField(value = "gmt_update", fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime		gmtUpdate;
	/**
	 * 记录创建用户
	 */
	@TableField(value = "create_user", fill = FieldFill.INSERT)
	private String				createUser;
	/**
	 * 记录最后更新用户
	 */
	@TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
	private String				updateUser;
	/**
	 * 职位状态（0-正常，1-禁用）
	 */
	@TableField("status")
	private Integer				status;

	public static final String	DB_ID				= "id";
	public static final String	ID					= "id";
	public static final String	DB_CORP_ID			= "corp_id";
	public static final String	CORP_ID				= "corpId";
	public static final String	DB_DEPT_ID			= "dept_id";
	public static final String	DEPT_ID				= "deptId";
	public static final String	DB_CODE				= "code";
	public static final String	CODE				= "code";
	public static final String	DB_NAME				= "name";
	public static final String	NAME				= "name";
	public static final String	DB_STATUS			= "status";
	public static final String	STATUS				= "status";
	public static final String	DB_GMT_CREATE		= "gmt_create";
	public static final String	GMT_CREATE			= "gmtCreate";
	public static final String	DB_GMT_UPDATE		= "gmt_update";
	public static final String	GMT_UPDATE			= "gmtUpdate";
	public static final String	DB_CREATE_USER		= "create_user";
	public static final String	CREATE_USER			= "createUser";
	public static final String	DB_UPDATE_USER		= "update_user";
	public static final String	UPDATE_USER			= "updateUser";

	@Override
	public Serializable pkVal() {
		return this.id;
	}

}
