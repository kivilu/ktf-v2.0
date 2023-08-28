package com.kivi.cif.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

@ApiModel(value = "CifCustomerAuthsDTO对象", description = "客户验证")
public class CifCustomerAuthsDTO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(value = "主键ID")
	private Long				id;

	@ApiModelProperty(value = "客户唯一ID")
	private Long				cifId;

	@ApiModelProperty(value = "应用ID")
	private Long				appId;

	@ApiModelProperty(value = "用户类型：00-任意，01-管理员，02：用户，03：操作员，04：审核员")
	private String				userType;

	@ApiModelProperty(value = "业务代码")
	private String				bizCode;

	@ApiModelProperty(value = "登录类型，00：手机号，01：邮箱，02：用户名，03：微信，04：支付宝，05：微博")
	private String				identityType;

	@ApiModelProperty(value = "标识（手机号 邮箱 用户名或第三方应用的唯一标识）")
	private String				identifier;

	@ApiModelProperty(value = "密码凭证")
	private String				credential;

	@ApiModelProperty(value = "密码盐")
	private String				credentialSalt;

	@ApiModelProperty(value = "验证标识，0：未验证，1：已验证。默认第三方登录都是已验证")
	private String				verified;

	@ApiModelProperty(value = "注册IP")
	private String				regIp;

	@ApiModelProperty(value = "注册时间")
	private LocalDateTime		regTime;

	@ApiModelProperty(value = "上次登录IP")
	private String				lastIp;

	@ApiModelProperty(value = "上次登录时间")
	private LocalDateTime		lastTime;

	@ApiModelProperty(value = "00:enabled,1:locked,2:disabled,3:processing,4:deleted,5:freezed,9:init")
	private String				status;

	public static final String	ID					= "id";
	public static final String	CIF_ID				= "cifId";
	public static final String	APP_ID				= "appId";
	public static final String	USER_TYPE			= "userType";
	public static final String	BIZ_CODE			= "bizCode";
	public static final String	IDENTITY_TYPE		= "identityType";
	public static final String	IDENTIFIER			= "identifier";
	public static final String	CREDENTIAL			= "credential";
	public static final String	CREDENTIAL_SALT		= "credentialSalt";
	public static final String	VERIFIED			= "verified";
	public static final String	REG_IP				= "regIp";
	public static final String	REG_TIME			= "regTime";
	public static final String	LAST_IP				= "lastIp";
	public static final String	LAST_TIME			= "lastTime";
	public static final String	STATUS				= "status";

}
