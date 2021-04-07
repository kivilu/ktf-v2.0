package com.kivi.dashboard.permission.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色资源
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

@ApiModel(value = "SysRoleResourceDTO对象", description = "角色资源")
public class SysRoleResourceDTO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(value = "角色id")
	private Long				roleId;

	@ApiModelProperty(value = "资源id")
	private Long				resourceId;

	@ApiModelProperty(value = "角色")
	private SysRoleDTO			role;

	@ApiModelProperty(value = "资源")
	private SysResourceDTO		resource;

	public static final String	ID					= "id";
	public static final String	ROLE_ID				= "roleId";
	public static final String	RESOURCE_ID			= "resourceId";

}
