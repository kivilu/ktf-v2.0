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
 * 资源
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_sys_resource")
public class SysResource extends Model<SysResource> {

	private static final long	serialVersionUID	= 1L;

	/**
	 * 应用ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long				id;
	/**
	 * 应用ID，默认值0
	 */
	@TableField("application_id")
	private Long				applicationId;
	/**
	 * 父级资源ID
	 */
	@TableField("parent_id")
	private Long				parentId;
	/**
	 * 资源名称
	 */
	@TableField("name")
	private String				name;
	/**
	 * 资源路径
	 */
	@TableField("url")
	private String				url;
	/**
	 * 资源介绍
	 */
	@TableField("description")
	private String				description;
	/**
	 * 资源图标
	 */
	@TableField("icon")
	private String				icon;
	/**
	 * 排序
	 */
	@TableField("seq")
	private Integer				seq;
	/**
	 * 资源类别(0：菜单，1：按钮)
	 */
	@TableField("resource_type")
	private Integer				resourceType;
	/**
	 * 是否隐藏菜单，0：显示，1：隐藏
	 */
	@TableField("hidden")
	private Integer				hidden;
	/**
	 * 状态(0：开，1：关）
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
	 * 记录创建用户
	 */
	@TableField(value = "create_user", fill = FieldFill.INSERT)
	private String				createUser;
	/**
	 * 记录最后修改用户
	 */
	@TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
	private String				updateUser;

	public static final String	DB_ID				= "id";
	public static final String	ID					= "id";
	public static final String	DB_APPLICATION_ID	= "application_id";
	public static final String	APPLICATION_ID		= "applicationId";
	public static final String	DB_PARENT_ID		= "parent_id";
	public static final String	PARENT_ID			= "parentId";
	public static final String	DB_NAME				= "name";
	public static final String	NAME				= "name";
	public static final String	DB_URL				= "url";
	public static final String	URL					= "url";
	public static final String	DB_DESCRIPTION		= "description";
	public static final String	DESCRIPTION			= "description";
	public static final String	DB_ICON				= "icon";
	public static final String	ICON				= "icon";
	public static final String	DB_SEQ				= "seq";
	public static final String	SEQ					= "seq";
	public static final String	DB_RESOURCE_TYPE	= "resource_type";
	public static final String	RESOURCE_TYPE		= "resourceType";
	public static final String	DB_HIDDEN			= "hidden";
	public static final String	HIDDEN				= "hidden";
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
