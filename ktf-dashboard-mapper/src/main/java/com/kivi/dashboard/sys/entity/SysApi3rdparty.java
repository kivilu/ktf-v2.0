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
 * 第三方API账号
 * </p>
 *
 * @author Auto-generator
 * @since 2020-02-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_sys_api3rdparty")
public class SysApi3rdparty extends Model<SysApi3rdparty> {

	private static final long	serialVersionUID	= 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long				id;
	/**
	 * 平台应用ID
	 */
	@TableField("ktf_app_id")
	private Long				ktfAppId;
	/**
	 * API接口类型，00-微信，01-支付宝
	 */
	@TableField("type")
	private String				type;
	/**
	 * API接口appid
	 */
	@TableField("api_appid")
	private String				apiAppid;
	/**
	 * 接口secret
	 */
	@TableField("api_secret")
	private String				apiSecret;
	/**
	 * API接口名称
	 */
	@TableField("api_name")
	private String				apiName;
	/**
	 * API接口URL
	 */
	@TableField("api_base_url")
	private String				apiBaseUrl;
	/**
	 * 我方证书文件名
	 */
	@TableField("self_cert_name")
	private String				selfCertName;
	/**
	 * 我方证书文件内容
	 */
	@TableField("self_cert_data")
	private String				selfCertData;
	/**
	 * 我方证书密码
	 */
	@TableField("self_cert_pass")
	private String				selfCertPass;
	/**
	 * 对端证书文件名
	 */
	@TableField("peer_cert_name")
	private String				peerCertName;
	/**
	 * 对端证书文件内容
	 */
	@TableField("peer_cert_data")
	private String				peerCertData;
	/**
	 * 对端证书文件密码
	 */
	@TableField("peer_cert_pass")
	private String				peerCertPass;
	@TableField(value = "gmt_create", fill = FieldFill.INSERT)
	private LocalDateTime		gmtCreate;
	@TableField(value = "gmt_update", fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime		gmtUpdate;
	@TableField("create_id")
	private Long				createId;
	@TableField("update_id")
	private Long				updateId;

	public static final String	DB_ID				= "id";
	public static final String	ID					= "id";
	public static final String	DB_KTF_APP_ID		= "ktf_app_id";
	public static final String	KTF_APP_ID			= "ktfAppId";
	public static final String	DB_TYPE				= "type";
	public static final String	TYPE				= "type";
	public static final String	DB_API_APPID		= "api_appid";
	public static final String	API_APPID			= "apiAppid";
	public static final String	DB_API_SECRET		= "api_secret";
	public static final String	API_SECRET			= "apiSecret";
	public static final String	DB_API_NAME			= "api_name";
	public static final String	API_NAME			= "apiName";
	public static final String	DB_API_BASE_URL		= "api_base_url";
	public static final String	API_BASE_URL		= "apiBaseUrl";
	public static final String	DB_SELF_CERT_NAME	= "self_cert_name";
	public static final String	SELF_CERT_NAME		= "selfCertName";
	public static final String	DB_SELF_CERT_DATA	= "self_cert_data";
	public static final String	SELF_CERT_DATA		= "selfCertData";
	public static final String	DB_SELF_CERT_PASS	= "self_cert_pass";
	public static final String	SELF_CERT_PASS		= "selfCertPass";
	public static final String	DB_PEER_CERT_NAME	= "peer_cert_name";
	public static final String	PEER_CERT_NAME		= "peerCertName";
	public static final String	DB_PEER_CERT_DATA	= "peer_cert_data";
	public static final String	PEER_CERT_DATA		= "peerCertData";
	public static final String	DB_PEER_CERT_PASS	= "peer_cert_pass";
	public static final String	PEER_CERT_PASS		= "peerCertPass";
	public static final String	DB_GMT_CREATE		= "gmt_create";
	public static final String	GMT_CREATE			= "gmtCreate";
	public static final String	DB_GMT_UPDATE		= "gmt_update";
	public static final String	GMT_UPDATE			= "gmtUpdate";
	public static final String	DB_CREATE_ID		= "create_id";
	public static final String	CREATE_ID			= "createId";
	public static final String	DB_UPDATE_ID		= "update_id";
	public static final String	UPDATE_ID			= "updateId";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
