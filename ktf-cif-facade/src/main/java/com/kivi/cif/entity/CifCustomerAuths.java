package com.kivi.cif.entity;

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
 * 客户验证
 * </p>
 *
 * @author Auto-generator
 * @since 2019-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cif_customer_auths")
public class CifCustomerAuths extends Model<CifCustomerAuths> {

	private static final long	serialVersionUID	= 1L;

	/**
	 * 主键ID
	 */
	@TableId("id")
	private Long				id;
	/**
	 * 客户唯一ID
	 */
	@TableField("cif_id")
	private Long				cifId;
	/**
	 * 应用ID
	 */
	@TableField("application_id")
	private Long				applicationId;
	/**
	 * 用户类型：01-房东，02-承租人，03-用户，04-服务员
	 */
	@TableField("user_type")
	private String				userType;

	/**
	 * 登录类型，00：手机号，01：邮箱，02：用户名，03：微信，04：支付宝，05：微博
	 */
	@TableField("identity_type")
	private String				identityType;
	/**
	 * 标识（手机号 邮箱 用户名或第三方应用的唯一标识）
	 */
	@TableField("identifier")
	private String				identifier;
	/**
	 * 密码凭证
	 */
	@TableField("credential")
	private String				credential;
	/**
	 * 密码盐
	 */
	@TableField("credential_salt")
	private String				credentialSalt;
	/**
	 * 验证标识，0：未验证，1：已验证。默认第三方登录都是已验证
	 */
	@TableField("verified")
	private String				verified;
	/**
	 * 注册IP
	 */
	@TableField("reg_ip")
	private String				regIp;
	/**
	 * 注册时间
	 */
	@TableField("reg_time")
	private LocalDateTime		regTime;
	/**
	 * 上次登录IP
	 */
	@TableField("last_ip")
	private String				lastIp;
	/**
	 * 上次登录时间
	 */
	@TableField("last_time")
	private LocalDateTime		lastTime;
	/**
	 * 00:enabled,1:locked,2:disabled,3:processing,4:deleted,5:freezed,9:init
	 */
	@TableField("status")
	private String				status;
	/**
	 * 记录创建时间
	 */
	@TableField(value = "gmt_create", fill = FieldFill.INSERT)
	private LocalDateTime		gmtCreate;
	/**
	 * 记录修改时间
	 */
	@TableField(value = "gmt_update", fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime		gmtUpdate;

	public static final String	DB_ID				= "id";
	public static final String	ID					= "id";
	public static final String	DB_CIF_ID			= "cif_id";
	public static final String	CIF_ID				= "cifId";
	public static final String	DB_USER_TYPE		= "user_type";
	public static final String	USER_TYPE			= "userType";
	public static final String	DB_BIZ_CODE			= "biz_code";
	public static final String	BIZ_CODE			= "bizCode";
	public static final String	DB_IDENTITY_TYPE	= "identity_type";
	public static final String	IDENTITY_TYPE		= "identityType";
	public static final String	DB_IDENTIFIER		= "identifier";
	public static final String	IDENTIFIER			= "identifier";
	public static final String	DB_CREDENTIAL		= "credential";
	public static final String	CREDENTIAL			= "credential";
	public static final String	DB_CREDENTIAL_SALT	= "credential_salt";
	public static final String	CREDENTIAL_SALT		= "credentialSalt";
	public static final String	DB_VERIFIED			= "verified";
	public static final String	VERIFIED			= "verified";
	public static final String	DB_REG_IP			= "reg_ip";
	public static final String	REG_IP				= "regIp";
	public static final String	DB_REG_TIME			= "reg_time";
	public static final String	REG_TIME			= "regTime";
	public static final String	DB_LAST_IP			= "last_ip";
	public static final String	LAST_IP				= "lastIp";
	public static final String	DB_LAST_TIME		= "last_time";
	public static final String	LAST_TIME			= "lastTime";
	public static final String	DB_STATUS			= "status";
	public static final String	STATUS				= "status";
	public static final String	DB_GMT_CREATE		= "gmt_create";
	public static final String	GMT_CREATE			= "gmtCreate";
	public static final String	DB_GMT_UPDATE		= "gmt_update";
	public static final String	GMT_UPDATE			= "gmtUpdate";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
