package com.kivi.dashboard.enterprise.entity;

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
 * 企业信息
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_enterprise")
public class Enterprise extends Model<Enterprise> {

	private static final long	serialVersionUID			= 1L;

	/**
	 * 主键ID
	 */
	@TableId("id")
	private Long				id;
	/**
	 * 应用ID，默认值0
	 */
	@TableField("application_id")
	private Long				applicationId;
	/**
	 * 企业id前缀
	 */
	@TableField("prefix")
	private String				prefix;

	@TableField("matrix")
	private String matrix;

	@TableField("service")
	private String service;



	/**
	 * 企业注册码(工商注册码-三证合一)
	 */
	@TableField("business_license_number")
	private String				businessLicenseNumber;
	/**
	 * 企业编号
	 */
	@TableField("enterprise_code")
	private String				enterpriseCode;
	/**
	 * 企业名称
	 */
	@TableField("enterprise_name")
	private String				enterpriseName;
	/**
	 * 所属行业
	 */
	@TableField("industry_Id")
	private Long				industryId;
	/**
	 * 所属区域
	 */
	@TableField("area_Id")
	private Long				areaId;
	/**
	 * 企业类型(政府：0，事业：1，国企:2，民企:3，私企:4，外企:5)
	 */
	@TableField("enterprise_type")
	private Integer				enterpriseType;
	/**
	 * 企业联系电话
	 */
	@TableField("telephone")
	private String				telephone;
	/**
	 * 企业邮箱
	 */
	@TableField("email")
	private String				email;
	/**
	 * 法人
	 */
	@TableField("legal_person")
	private String				legalPerson;
	/**
	 * 企业负责人姓名
	 */
	@TableField("main_person")
	private String				mainPerson;
	/**
	 * 企业负责人移动电话号码
	 */
	@TableField("main_person_mobile")
	private String				mainPersonMobile;
	/**
	 * x坐标
	 */
	@TableField("map_x")
	private String				mapX;
	/**
	 * y坐标
	 */
	@TableField("map_y")
	private String				mapY;
	/**
	 * z坐标
	 */
	@TableField("map_z")
	private String				mapZ;
	/**
	 * 邮政编码
	 */
	@TableField("zip_code")
	private String				zipCode;
	/**
	 * 地址
	 */
	@TableField("address")
	private String				address;
	/**
	 * 数据是否同步(0:是,1:否)
	 */
	@TableField("is_sync")
	private Integer				isSync;
	/**
	 * 企业状态（0-正常，1-禁用）
	 */
	@TableField("status")
	private Integer				status;

	@TableField("key_number")
	private Integer				keyNumber;
	/**
	 * 记录创建时间
	 */
	@TableField(value = "gmt_create", fill = FieldFill.INSERT)
	private LocalDateTime		gmtCreate;
	/**
	 * 记录最后修改时间
	 */
	@TableField(value = "gmt_update", fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime		gmtUpdate;
	/**
	 * 记录创建者(用户)
	 */
	@TableField(value = "create_user", fill = FieldFill.INSERT)
	private String				createUser;
	/**
	 * 记录最后修改者(用户)
	 */
	@TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
	private String				updateUser;

	public static final String	DB_ID						= "id";
	public static final String	ID							= "id";
	public static final String	DB_APPLICATION_ID			= "application_id";
	public static final String	APPLICATION_ID				= "applicationId";
	public static final String	DB_PREFIX					= "prefix";
	public static final String	PREFIX						= "prefix";
	public static final String	DB_BUSINESS_LICENSE_NUMBER	= "business_license_number";
	public static final String	BUSINESS_LICENSE_NUMBER		= "businessLicenseNumber";
	public static final String	DB_ENTERPRISE_CODE			= "enterprise_code";
	public static final String	ENTERPRISE_CODE				= "enterpriseCode";
	public static final String	DB_ENTERPRISE_NAME			= "enterprise_name";
	public static final String	ENTERPRISE_NAME				= "enterpriseName";
	public static final String	DB_INDUSTRY_CODE			= "industry_code";
	public static final String	INDUSTRY_CODE				= "industryCode";
	public static final String	DB_AREA_CODE				= "area_code";
	public static final String	AREA_CODE					= "areaCode";
	public static final String	DB_ENTERPRISE_TYPE			= "enterprise_type";
	public static final String	ENTERPRISE_TYPE				= "enterpriseType";
	public static final String	DB_TELEPHONE				= "telephone";
	public static final String	TELEPHONE					= "telephone";
	public static final String	DB_EMAIL					= "email";
	public static final String	EMAIL						= "email";
	public static final String	DB_LEGAL_PERSON				= "legal_person";
	public static final String	LEGAL_PERSON				= "legalPerson";
	public static final String	DB_MAIN_PERSON				= "main_person";
	public static final String	MAIN_PERSON					= "mainPerson";
	public static final String	DB_MAIN_PERSON_MOBILE		= "main_person_mobile";
	public static final String	MAIN_PERSON_MOBILE			= "mainPersonMobile";
	public static final String	DB_MAP_X					= "map_x";
	public static final String	MAP_X						= "mapX";
	public static final String	DB_MAP_Y					= "map_y";
	public static final String	MAP_Y						= "mapY";
	public static final String	DB_MAP_Z					= "map_z";
	public static final String	MAP_Z						= "mapZ";
	public static final String	DB_ZIP_CODE					= "zip_code";
	public static final String	ZIP_CODE					= "zipCode";
	public static final String	DB_ADDRESS					= "address";
	public static final String	ADDRESS						= "address";
	public static final String	DB_IS_SYNC					= "is_sync";
	public static final String	IS_SYNC						= "isSync";
	public static final String	DB_STATUS					= "status";
	public static final String	STATUS						= "status";
	public static final String	DB_GMT_CREATE				= "gmt_create";
	public static final String	GMT_CREATE					= "gmtCreate";
	public static final String	DB_GMT_UPDATE				= "gmt_update";
	public static final String	GMT_UPDATE					= "gmtUpdate";
	public static final String	DB_CREATE_USER				= "create_user";
	public static final String	CREATE_USER					= "createUser";
	public static final String	DB_UPDATE_USER				= "update_user";
	public static final String	UPDATE_USER					= "updateUser";
	public static final String	DB_KEY_NUMBER			= "key_number";
	public static final String	KEY_NUMBER				= "keyNumber";
	public static final String	DB_MATRIX		= "matrix";
	public static final String	MATRIX			= "matrix";
	public static final String	DB_SERVICE		= "service";
	public static final String	SERVICE			= "service";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
