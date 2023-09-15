package com.kivi.sys.org.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 企业职务配置
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

@ApiModel(value = "OrgTitleDTO", description = "企业职务配置")
public class OrgTitleDTO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	private Long				id;

	@ApiModelProperty(value = "企业ID(对应企业主表ID)")
	private Long				corpId;

	@ApiModelProperty(value = "企业部门表ID")
	private Long				deptId;

	@ApiModelProperty(value = "部门名称")
	private String				deptName;

	@ApiModelProperty(value = "职务代码")
	private String				code;

	@ApiModelProperty(value = "职务名称")
	private String				name;

	@ApiModelProperty(value = "职位状态（0-正常，1-禁用）")
	private Integer				status;

	public static final String	ID					= "id";
	public static final String	CORP_ID				= "corpId";
	public static final String	DEPT_ID				= "deptId";
	public static final String	CODE				= "code";
	public static final String	NAME				= "name";
	public static final String	STATUS				= "status";
	public static final String	GMT_CREATE			= "gmtCreate";
	public static final String	GMT_UPDATE			= "gmtUpdate";
	public static final String	CREATE_USER			= "createUser";
	public static final String	UPDATE_USER			= "updateUser";

}
