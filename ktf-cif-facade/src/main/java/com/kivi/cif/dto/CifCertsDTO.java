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
 * 客户证书
 * </p>
 *
 * @author Auto-generator
 * @since 2019-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

@ApiModel(value = "CifCertsDTO对象", description = "证书DTO对象")
public class CifCertsDTO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(hidden = true)
	private Long				id;

	@ApiModelProperty(value = "证书标识")
	private String				identifier;

	@ApiModelProperty(value = "证书用途，0-用户证书，1-系统证书")
	private Integer				certUse;

	@ApiModelProperty(value = "类型，00：根证书，01：CRL，02：签名证书，03：验签证书，04：加密证书，05：解密证书")
	private String				type;

	@ApiModelProperty(hidden = true, value = "证书序列号")
	private String				serialNumber;

	@ApiModelProperty(hidden = true)
	private String				dn;

	@ApiModelProperty(hidden = true, value = "有效期起始日")
	private LocalDateTime		notBefore;

	@ApiModelProperty(hidden = true, value = "有效期结束日")
	private LocalDateTime		notAfter;

	@ApiModelProperty(hidden = true, value = "加密算法")
	private String				algEncryption;

	@ApiModelProperty(hidden = true, value = "签名算法")
	private String				algSign;

	@ApiModelProperty(hidden = true, value = "证书文件后缀：pem,cer,p12,pfx")
	private String				ext;

	@ApiModelProperty(hidden = true, value = "证书文件名称")
	private String				fileName;

	@ApiModelProperty(hidden = true, value = "证书文件内容")
	private String				dataBase64;

	@ApiModelProperty(value = "证书密码")
	private String				passwdBase64;

	@ApiModelProperty(hidden = true, value = "状态，0启用  1停用")
	private String				state;

	public static final String	ID					= "id";
	public static final String	IDENTIFIER			= "identifier";
	public static final String	USE					= "use";
	public static final String	TYPE				= "type";
	public static final String	SERIAL_NUMBER		= "serialNumber";
	public static final String	DN					= "dn";
	public static final String	NOT_BEFORE			= "notBefore";
	public static final String	NOT_AFTER			= "notAfter";
	public static final String	ALG_ENCRYPTION		= "algEncryption";
	public static final String	ALG_SIGN			= "algSign";
	public static final String	EXT					= "ext";
	public static final String	DATA_BASE64			= "dataBase64";
	public static final String	PASSWD_BASE64		= "passwdBase64";
	public static final String	STATE				= "state";

}
