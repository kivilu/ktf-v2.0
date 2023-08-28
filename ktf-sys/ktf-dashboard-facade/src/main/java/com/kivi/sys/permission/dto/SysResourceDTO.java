package com.kivi.sys.permission.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 资源
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

@ApiModel(value = "SysResourceDTO对象", description = "资源")
public class SysResourceDTO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(value = "主键ID")
	private Long				id;

	@ApiModelProperty(value = "应用ID")
	private Long				appId;

	@ApiModelProperty(value = "父级资源ID")
	private Long				parentId;

	@ApiModelProperty(value = "资源名称")
	private String				name;

	@ApiModelProperty(value = "资源路径")
	private String				url;

	@ApiModelProperty(value = "资源介绍")
	private String				description;

	@ApiModelProperty(value = "资源图标")
	private String				icon;

	@ApiModelProperty(value = "排序")
	private Integer				seq;

	@ApiModelProperty(value = "资源类别(0：菜单，1：按钮)")
	private Integer				resourceType;

	@ApiModelProperty(value = "是否隐藏菜单（0：显示，1：隐藏）")
	private Integer				hidden;

	@ApiModelProperty(value = "状态(0：开，1：关）")
	private Integer				status;

	@ApiModelProperty(value = "是否有子菜单")
	private Boolean				hasChildren;

	@ApiModelProperty(value = "是否叶子")
	private Boolean				isLeaf;

	public static final String	ID					= "id";
	public static final String	PARENT_ID			= "parentId";
	public static final String	NAME				= "name";
	public static final String	URL					= "url";
	public static final String	DESCRIPTION			= "description";
	public static final String	ICON				= "icon";
	public static final String	SEQ					= "seq";
	public static final String	RESOURCE_TYPE		= "resourceType";
	public static final String	STATUS				= "status";
	public static final String	HIDDEN				= "hidden";

}
