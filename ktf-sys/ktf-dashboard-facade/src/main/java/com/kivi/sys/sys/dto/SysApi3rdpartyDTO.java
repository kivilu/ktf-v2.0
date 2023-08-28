package com.kivi.sys.sys.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

@ApiModel(value = "SysApi3rdpartyDTO对象", description = "第三方API账号")
public class SysApi3rdpartyDTO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(value = "主键ID")
	private Long				id;

	@ApiModelProperty(value = "平台应用ID")
	private Long				ktfAppId;

	@ApiModelProperty(value = "平台应用名称")
	private String				ktfAppName;

	@ApiModelProperty(value = "API接口类型，00-微信，01-支付宝")
	private String				type;

	@ApiModelProperty(value = "API接口appid")
	private String				apiAppid;

	@ApiModelProperty(value = "API接口secret")
	private String				apiSecret;

	@ApiModelProperty(value = "API接口名称")
	private String				apiName;

	@ApiModelProperty(value = "API接口URL")
	private String				apiBaseUrl;

	@ApiModelProperty(value = "我方证书文件名")
	private String				selfCertName;

	@ApiModelProperty(value = "我方证书文件内容")
	private String				selfCertData;

	@ApiModelProperty(value = "我方证书密码")
	private String				selfCertPass;

	@ApiModelProperty(value = "对端证书文件名")
	private String				peerCertName;

	@ApiModelProperty(value = "对端证书文件内容")
	private String				peerCertData;

	@ApiModelProperty(value = "对端证书文件密码")
	private String				peerCertPass;

	private LocalDateTime		gmtCreate;
	private LocalDateTime		gmtUpdate;

	public static final String	ID					= "id";
	public static final String	KTF_APP_ID			= "ktfAppId";
	public static final String	TYPE				= "type";
	public static final String	APPID				= "apiAppid";
	public static final String	APP_SECRET			= "apiSecret";
	public static final String	APP_NAME			= "apiName";
	public static final String	API_BASE_URL		= "apiBaseUrl";
	public static final String	SELF_CERT_NAME		= "selfCertName";
	public static final String	SELF_CERT_DATA		= "selfCertData";
	public static final String	SELF_CERT_PASS		= "selfCertPass";
	public static final String	PEER_CERT_NAME		= "peerCertName";
	public static final String	PEER_CERT_DATA		= "peerCertData";
	public static final String	PEER_CERT_PASS		= "peerCertPass";

}
