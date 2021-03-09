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
 * 客户证书
 * </p>
 *
 * @author Auto-generator
 * @since 2019-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cif_certs")
public class CifCerts extends Model<CifCerts> {

	private static final long	serialVersionUID	= 1L;

	/**
	 * 主键ID
	 */
	@TableId("id")
	private Long				id;
	/**
	 * 客户唯一ID
	 */
	@TableField("identifier")
	private String				identifier;
	/**
	 * 证书用途，0-用户证书，1-系统证书
	 */
	@TableField("cert_use")
	private Integer				certUse;
	/**
	 * 类型，00：根证书，01：CRL，02：签名证书，03：验签证书，04：加密证书，05：解密证书
	 */
	@TableField("type")
	private String				type;
	/**
	 * 证书序列号
	 */
	@TableField("serial_number")
	private String				serialNumber;
	@TableField("dn")
	private String				dn;
	@TableField("not_before")
	private LocalDateTime		notBefore;
	@TableField("not_after")
	private LocalDateTime		notAfter;
	/**
	 * 加密算法
	 */
	@TableField("alg_encryption")
	private String				algEncryption;
	@TableField("alg_sign")
	private String				algSign;
	/**
	 * 证书文件后缀：pem,cer,p12,pfx
	 */
	@TableField("ext")
	private String				ext;
	/**
	 * 证书文件名称
	 */
	@TableField("file_name")
	private String				fileName;
	/**
	 * 证书文件内容
	 */
	@TableField("data_base64")
	private String				dataBase64;
	@TableField("passwd_base64")
	private String				passwdBase64;
	/**
	 * 状态，0启用 1停用
	 */
	@TableField("state")
	private String				state;
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
	public static final String	DB_IDENTIFIER		= "identifier";
	public static final String	IDENTIFIER			= "identifier";
	public static final String	DB_CERT_USE			= "cert_use";
	public static final String	CERT_USE			= "certUse";
	public static final String	DB_TYPE				= "type";
	public static final String	TYPE				= "type";
	public static final String	DB_SERIAL_NUMBER	= "serial_number";
	public static final String	SERIAL_NUMBER		= "serialNumber";
	public static final String	DB_DN				= "dn";
	public static final String	DN					= "dn";
	public static final String	DB_NOT_BEFORE		= "not_before";
	public static final String	NOT_BEFORE			= "notBefore";
	public static final String	DB_NOT_AFTER		= "not_after";
	public static final String	NOT_AFTER			= "notAfter";
	public static final String	DB_ALG_ENCRYPTION	= "alg_encryption";
	public static final String	ALG_ENCRYPTION		= "algEncryption";
	public static final String	DB_ALG_SIGN			= "alg_sign";
	public static final String	ALG_SIGN			= "algSign";
	public static final String	DB_EXT				= "ext";
	public static final String	EXT					= "ext";
	public static final String	DB_DATA_BASE64		= "data_base64";
	public static final String	DATA_BASE64			= "dataBase64";
	// public static final String DB_PASSWD_BASE64 = "passwd_base64";
	// public static final String PASSWD_BASE64 = "passwdBase64";
	public static final String	DB_STATE			= "state";
	public static final String	STATE				= "state";
	public static final String	DB_GMT_CREATE		= "gmt_create";
	public static final String	GMT_CREATE			= "gmtCreate";
	public static final String	DB_GMT_UPDATE		= "gmt_update";
	public static final String	GMT_UPDATE			= "gmtUpdate";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
