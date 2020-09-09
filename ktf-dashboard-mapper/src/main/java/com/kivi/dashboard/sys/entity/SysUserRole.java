package com.kivi.dashboard.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.kivi.framework.util.kit.StrKit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户角色
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_sys_user_role")
public class SysUserRole extends Model<SysUserRole> {

	private static final long	serialVersionUID	= 1L;

	/**
	 * 用户id
	 */
	@TableField("user_id")
	private Long				userId;
	/**
	 * 角色id
	 */
	@TableField("role_id")
	private Long				roleId;

	public static final String	DB_USER_ID			= "user_id";
	public static final String	USER_ID				= "userId";
	public static final String	DB_ROLE_ID			= "role_id";
	public static final String	ROLE_ID				= "roleId";

	@Override
	protected Serializable pkVal() {
		return StrKit.join("-", this.userId, this.roleId);
	}

}
