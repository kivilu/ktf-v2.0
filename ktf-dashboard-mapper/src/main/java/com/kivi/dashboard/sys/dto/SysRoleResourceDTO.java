package com.kivi.dashboard.sys.dto;


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

@ApiModel(value="SysRoleResourceDTO对象", description="角色资源")
public class SysRoleResourceDTO implements Serializable {

    private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "主键id")
	private Long id;


	@ApiModelProperty(value = "角色id")
	private Long roleId;


	@ApiModelProperty(value = "资源id")
	private Long resourceId;


	public static final String ID = "id";
	public static final String ROLE_ID = "roleId";
	public static final String RESOURCE_ID = "resourceId";

}
