package com.kivi.cif.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 客户信息
 * </p>
 *
 * @author Auto-generator
 * @since 2019-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

@ApiModel(value = "CifCustomerDTO对象", description = "客户信息")
public class CifCustomerDTO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(value = "主键ID")
	private Long				id;

	@ApiModelProperty(value = "客户唯一标识")
	private String				customerId;

	@ApiModelProperty(value = "用户注册手机号")
	private String				regPhoneNumber;

	@ApiModelProperty(value = "没有区号的手机号")
	private String				wxPhoneUmber;

	@ApiModelProperty(value = "电子邮箱")
	private String				email;

	private String				countryCode;

	@ApiModelProperty(value = "用户姓名")
	private String				name;

	@ApiModelProperty(value = "用户昵称")
	private String				nickName;

	@ApiModelProperty(
			value = "用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表132*132正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。")
	private String				avatarUrl;

	@ApiModelProperty(value = "用户的性别，值为1时是男性，值为2时是女性，值为0时是未知")
	private String				gender;

	@ApiModelProperty(value = "用户所在城市")
	private String				city;

	@ApiModelProperty(value = "用户所在省份")
	private String				province;

	@ApiModelProperty(value = "用户所在国家")
	private String				country;

	@ApiModelProperty(value = "用户的语言，简体中文为zh_CN")
	private String				language;

	@ApiModelProperty(value = "证件类型")
	private String				certType;

	@ApiModelProperty(value = "证件号")
	private String				certNo;

	private String				photo;

	@ApiModelProperty(value = "微信客户状态，00:enabled,1:locked,2:disabled,3:processing,4:deleted,5:freezed,9:init")
	private String				status;

	public static final String	ID					= "id";
	public static final String	CUSTOMER_ID			= "customerId";
	public static final String	REG_PHONE_NUMBER	= "regPhoneNumber";
	public static final String	WX_PHONE_UMBER		= "wxPhoneUmber";
	public static final String	COUNTRY_CODE		= "countryCode";
	public static final String	NAME				= "name";
	public static final String	NICK_NAME			= "nickName";
	public static final String	AVATAR_URL			= "avatarUrl";
	public static final String	GENDER				= "gender";
	public static final String	CITY				= "city";
	public static final String	PROVINCE			= "province";
	public static final String	COUNTRY				= "country";
	public static final String	LANGUAGE			= "language";
	public static final String	CERT_TYPE			= "certType";
	public static final String	CERT_NO				= "certNo";
	public static final String	PHOTO				= "photo";
	public static final String	STATUS				= "status";

}
