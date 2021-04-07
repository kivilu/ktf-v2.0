package com.kivi.dashboard.org.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

@ApiModel(value = "OrgCorpDTO对象", description = "企业信息")
public class OrgCorpDTO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(value = "主键ID")
	private Long				id;

	@ApiModelProperty(value = "appID")
	private Long				appId;

	@ApiModelProperty(value = "企业名称")
	private String				name;

	@ApiModelProperty(value = "企业缩写")
	private String				abbr;

	@ApiModelProperty(value = "企业id前缀")
	private String				prefix;

	@ApiModelProperty(value = "企业注册码(工商注册码-三证合一)")
	private String				uscc;

	@ApiModelProperty(value = "企业类型(政府：0，事业：1，国企:2，民企:3，外企:4)")
	private Integer				type;

	@ApiModelProperty(value = "所属行业ID")
	private Long				industryId;

	@ApiModelProperty(value = "所属行业名称")
	private String				industryName;

	@ApiModelProperty(value = "所属省ID")
	private Long				provinceId;

	@ApiModelProperty(value = "所属省")
	private String				province;

	@ApiModelProperty(value = "企业负责人姓名")
	private String				mainPerson;

	@ApiModelProperty(value = "企业负责人移动电话号码")
	private String				mainPersonMobile;

	@ApiModelProperty(value = "企业联系人邮箱")
	private String				mainPersonEmail;

	@ApiModelProperty(value = "邮政编码")
	private String				zipCode;

	@ApiModelProperty(value = "地址")
	private String				address;

	@ApiModelProperty(value = "企业状态（0-正常，1-禁用）")
	private Integer				status;

	public static final String	ID					= "id";
	public static final String	APP_ID				= "appId";
	public static final String	NAME				= "name";
	public static final String	ABBR				= "abbr";
	public static final String	PREFIX				= "prefix";
	public static final String	USCC				= "uscc";
	public static final String	TYPE				= "type";
	public static final String	INDUSTRY_ID			= "industryId";
	public static final String	REGION_IDS			= "regionIds";
	public static final String	MAIN_PERSON			= "mainPerson";
	public static final String	MAIN_PERSON_MOBILE	= "mainPersonMobile";
	public static final String	MAIN_PERSON_EMAIL	= "mainPersonEmail";
	public static final String	ZIP_CODE			= "zipCode";
	public static final String	ADDRESS				= "address";
	public static final String	MAP_X				= "mapX";
	public static final String	MAP_Y				= "mapY";
	public static final String	MAP_Z				= "mapZ";
	public static final String	STATUS				= "status";
	public static final String	GMT_CREATE			= "gmtCreate";
	public static final String	GMT_UPDATE			= "gmtUpdate";
	public static final String	CREATE_USER			= "createUser";
	public static final String	UPDATE_USER			= "updateUser";

}
