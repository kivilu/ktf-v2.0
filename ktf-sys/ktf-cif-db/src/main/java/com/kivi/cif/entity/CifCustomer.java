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
 * 客户信息
 * </p>
 *
 * @author Auto-generator
 * @since 2019-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cif_customer")
public class CifCustomer extends Model<CifCustomer> {

	private static final long	serialVersionUID	= 1L;

	/**
	 * 主键ID
	 */
	@TableId("id")
	private Long				id;
	/**
	 * 客户唯一标识
	 */
	@TableField("customer_id")
	private String				customerId;
	/**
	 * 用户注册手机号
	 */
	@TableField("reg_phone_number")
	private String				regPhoneNumber;
	/**
	 * 没有区号的手机号
	 */
	@TableField("wx_phone_umber")
	private String				wxPhoneUmber;

	/**
	 * 电子邮箱
	 */
	@TableField("email")
	private String				email;

	@TableField("country_code")
	private String				countryCode;
	/**
	 * 用户姓名
	 */
	@TableField("name")
	private String				name;
	/**
	 * 用户昵称
	 */
	@TableField("nick_name")
	private String				nickName;
	/**
	 * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表132*132正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	 */
	@TableField("avatar_url")
	private String				avatarUrl;
	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
	@TableField("gender")
	private String				gender;
	/**
	 * 用户所在城市
	 */
	@TableField("city")
	private String				city;
	/**
	 * 用户所在省份
	 */
	@TableField("province")
	private String				province;
	/**
	 * 用户所在国家
	 */
	@TableField("country")
	private String				country;
	/**
	 * 用户的语言，简体中文为zh_CN
	 */
	@TableField("language")
	private String				language;
	/**
	 * 证件类型
	 */
	@TableField("cert_type")
	private String				certType;
	/**
	 * 证件号
	 */
	@TableField("cert_no")
	private String				certNo;
	@TableField("photo")
	private String				photo;
	/**
	 * 微信客户状态，00:enabled,1:locked,2:disabled,3:processing,4:deleted,5:freezed,9:init
	 */
	@TableField("status")
	private String				status;
	/**
	 * 创建时间
	 */
	@TableField(value = "gmt_create", fill = FieldFill.INSERT)
	private LocalDateTime		gmtCreate;
	/**
	 * 更新时间
	 */
	@TableField(value = "gmt_update", fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime		gmtUpdate;

	public static final String	DB_ID				= "id";
	public static final String	ID					= "id";
	public static final String	DB_CUSTOMER_ID		= "customer_id";
	public static final String	CUSTOMER_ID			= "customerId";
	public static final String	DB_REG_PHONE_NUMBER	= "reg_phone_number";
	public static final String	REG_PHONE_NUMBER	= "regPhoneNumber";
	public static final String	DB_WX_PHONE_UMBER	= "wx_phone_umber";
	public static final String	WX_PHONE_UMBER		= "wxPhoneUmber";
	public static final String	DB_COUNTRY_CODE		= "country_code";
	public static final String	COUNTRY_CODE		= "countryCode";
	public static final String	DB_NAME				= "name";
	public static final String	NAME				= "name";
	public static final String	DB_NICK_NAME		= "nick_name";
	public static final String	NICK_NAME			= "nickName";
	public static final String	DB_AVATAR_URL		= "avatar_url";
	public static final String	AVATAR_URL			= "avatarUrl";
	public static final String	DB_GENDER			= "gender";
	public static final String	GENDER				= "gender";
	public static final String	DB_CITY				= "city";
	public static final String	CITY				= "city";
	public static final String	DB_PROVINCE			= "province";
	public static final String	PROVINCE			= "province";
	public static final String	DB_COUNTRY			= "country";
	public static final String	COUNTRY				= "country";
	public static final String	DB_LANGUAGE			= "language";
	public static final String	LANGUAGE			= "language";
	public static final String	DB_CERT_TYPE		= "cert_type";
	public static final String	CERT_TYPE			= "certType";
	public static final String	DB_CERT_NO			= "cert_no";
	public static final String	CERT_NO				= "certNo";
	public static final String	DB_PHOTO			= "photo";
	public static final String	PHOTO				= "photo";
	public static final String	DB_STATUS			= "status";
	public static final String	STATUS				= "status";
	public static final String	DB_GMT_CREATE		= "gmt_create";
	public static final String	GMT_CREATE			= "gmtCreate";
	public static final String	DB_GMT_UPDATE		= "gmt_update";
	public static final String	GMT_UPDATE			= "gmtUpdate";

	@Override
	public Serializable pkVal() {
		return this.id;
	}

}
