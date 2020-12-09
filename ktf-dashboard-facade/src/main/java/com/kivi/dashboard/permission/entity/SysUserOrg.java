package com.kivi.dashboard.permission.entity;

import java.io.Serializable;

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
 * 监管用户与企业关联
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_sys_user_org")
public class SysUserOrg extends Model<SysUserOrg> {

	private static final long	serialVersionUID	= 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long				id;
	/**
	 * 角色id
	 */
	@TableField("user_id")
	private Long				userId;
	/**
	 * 企业id列表(;分割)
	 */
	@TableField("org_id")
	private Long				orgId;

	public static final String	DB_ID				= "id";
	public static final String	ID					= "id";
	public static final String	DB_USER_ID			= "user_id";
	public static final String	USER_ID				= "userId";
	public static final String	DB_ORG_ID			= "org_id";
	public static final String	ORG_ID				= "orgId";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
