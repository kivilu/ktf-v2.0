package com.kivi.dashboard.org.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 企业部门
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

@ApiModel(value = "OrgDeptDTO", description = "企业部门")
public class OrgDeptDTO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	private Long				id;

	@ApiModelProperty(value = "企业部门父ID")
	private Long				parentId;

	@ApiModelProperty(value = "企业ID(对应企业主表ID)")
	private Long				corpId;

	@ApiModelProperty(value = "部门代码缩写")
	private String				abbr;

	@ApiModelProperty(value = "部门名称")
	private String				name;

	@ApiModelProperty(value = "部门状态（0-正常，1-禁用）")
	private Integer				status;

	@ApiModelProperty(value = "是否有子菜单")
	private Boolean				hasChildren;

	@ApiModelProperty(value = "是否叶子")
	private Boolean				hasLeaf;

	public static final String	ID					= "id";
	public static final String	PARENT_ID			= "parentId";
	public static final String	CORP_ID				= "corpId";
	public static final String	ABBR				= "abbr";
	public static final String	NAME				= "name";
	public static final String	STATUS				= "status";
	public static final String	GMT_CREATE			= "gmtCreate";
	public static final String	GMT_UPDATE			= "gmtUpdate";
	public static final String	CREATE_USER			= "createUser";
	public static final String	UPDATE_USER			= "updateUser";

}
