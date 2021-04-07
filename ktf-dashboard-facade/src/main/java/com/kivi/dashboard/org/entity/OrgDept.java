package com.kivi.dashboard.org.entity;

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
 * 企业部门
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_org_dept")
public class OrgDept extends Model<OrgDept> {

	private static final long	serialVersionUID	= 1L;

	@TableId("id")
	private Long				id;
	/**
	 * 企业部门父ID
	 */
	@TableField("parent_id")
	private Long				parentId;
	/**
	 * 企业ID(对应企业主表ID)
	 */
	@TableField("corp_id")
	private Long				corpId;
	/**
	 * 部门代码缩写
	 */
	@TableField("abbr")
	private String				abbr;
	/**
	 * 部门名称
	 */
	@TableField("name")
	private String				name;
	/**
	 * 部门状态（0-正常，1-禁用）
	 */
	@TableField("status")
	private Integer				status;
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
	 * 记录创建者(用户)
	 */
	@TableField(value = "create_user", fill = FieldFill.INSERT)
	private String				createUser;
	/**
	 * 记录最后修改者(用户)
	 */
	@TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
	private String				updateUser;

	public static final String	DB_ID				= "id";
	public static final String	ID					= "id";
	public static final String	DB_PARENT_ID		= "parent_id";
	public static final String	PARENT_ID			= "parentId";
	public static final String	DB_CORP_ID			= "corp_id";
	public static final String	CORP_ID				= "corpId";
	public static final String	DB_ABBR				= "abbr";
	public static final String	ABBR				= "abbr";
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
	protected Serializable pkVal() {
		return this.id;
	}

}
