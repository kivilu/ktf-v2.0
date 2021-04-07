package com.kivi.dashboard.org.entity;

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
@TableName("ktf_org_corp")
public class OrgCorp extends Model<OrgCorp> {

	private static final long	serialVersionUID		= 1L;

	/**
	 * 主键ID
	 */
	@TableId("id")
	private Long				id;
	/**
	 * 应用ID，默认值0
	 */
	@TableField("app_id")
	private Long				appId;
	/**
	 * 企业名称
	 */
	@TableField("name")
	private String				name;
	/**
	 * 企业缩写
	 */
	@TableField("abbr")
	private String				abbr;
	/**
	 * 企业id前缀
	 */
	@TableField("prefix")
	private String				prefix;

	/**
	 * 企业注册码(工商注册码-三证合一)
	 */
	@TableField("uscc")
	private String				uscc;

	/**
	 * 企业类型(政府：0，事业：1，国企:2，民企:3，外企:4)
	 */
	@TableField("type")
	private Integer				type;

	/**
	 * 所属行业
	 */
	@TableField("industry_id")
	private Long				industryId;
	/**
	 * 所属省ID
	 */
	@TableField("province_id")
	private Long				provinceId;

	/**
	 * 所属城市ID
	 */
	@TableField("city_id")
	private Long				cityId;

	/**
	 * 所属区县ID
	 */
	@TableField("county_id")
	private Long				countyId;

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
	 * 企业邮箱
	 */
	@TableField("main_person_email")
	private String				mainPersonEmail;

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
	 * 企业状态（0-正常，1-禁用）
	 */
	@TableField("status")
	private Integer				status;

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

	public static final String	DB_ID					= "id";
	public static final String	ID						= "id";
	public static final String	DB_APP_ID				= "app_id";
	public static final String	APP_ID					= "appId";
	public static final String	DB_NAME					= "name";
	public static final String	NAME					= "name";
	public static final String	DB_ABBR					= "abbr";
	public static final String	ABBR					= "abbr";
	public static final String	DB_PREFIX				= "prefix";
	public static final String	PREFIX					= "prefix";
	public static final String	DB_USCC					= "uscc";
	public static final String	USCC					= "uscc";
	public static final String	DB_TYPE					= "type";
	public static final String	TYPE					= "type";
	public static final String	DB_INDUSTRY_ID			= "industry_id";
	public static final String	INDUSTRY_ID				= "industryId";
	public static final String	DB_REGION_IDS			= "region_ids";
	public static final String	REGION_IDS				= "regionIds";
	public static final String	DB_MAIN_PERSON			= "main_person";
	public static final String	MAIN_PERSON				= "mainPerson";
	public static final String	DB_MAIN_PERSON_MOBILE	= "main_person_mobile";
	public static final String	MAIN_PERSON_MOBILE		= "mainPersonMobile";
	public static final String	DB_MAIN_PERSON_EMAIL	= "main_person_email";
	public static final String	MAIN_PERSON_EMAIL		= "mainPersonEmail";
	public static final String	DB_ZIP_CODE				= "zip_code";
	public static final String	ZIP_CODE				= "zipCode";
	public static final String	DB_ADDRESS				= "address";
	public static final String	ADDRESS					= "address";
	public static final String	DB_MAP_X				= "map_x";
	public static final String	MAP_X					= "mapX";
	public static final String	DB_MAP_Y				= "map_y";
	public static final String	MAP_Y					= "mapY";
	public static final String	DB_MAP_Z				= "map_z";
	public static final String	MAP_Z					= "mapZ";
	public static final String	DB_STATUS				= "status";
	public static final String	STATUS					= "status";
	public static final String	DB_GMT_CREATE			= "gmt_create";
	public static final String	GMT_CREATE				= "gmtCreate";
	public static final String	DB_GMT_UPDATE			= "gmt_update";
	public static final String	GMT_UPDATE				= "gmtUpdate";
	public static final String	DB_CREATE_USER			= "create_user";
	public static final String	CREATE_USER				= "createUser";
	public static final String	DB_UPDATE_USER			= "update_user";
	public static final String	UPDATE_USER				= "updateUser";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
