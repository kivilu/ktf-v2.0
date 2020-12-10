package com.kivi.dashboard.permission.dto;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

@ApiModel(value = "SysRoleDTO对象", description = "角色")
public class SysRoleDTO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(value = "主键id")
	private Long				id;

	@ApiModelProperty(value = "角色名称")
	private String				name;

	@ApiModelProperty(value = "角色描述")
	private String				description;

	private Integer				status;

	@ApiModelProperty(value = "角色资源列表")
	private List<Long>			resourceIds;

	@ApiModelProperty(hidden = true)
	private Long				createUserId;

	public static final String	ID					= "id";
	public static final String	NAME				= "name";
	public static final String	DESCRIPTION			= "description";
	public static final String	STATUS				= "status";
	public static final String	CREATE_USER_ID		= "createUserId";
}
