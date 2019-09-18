package com.kivi.dashboard.sys.dto;


import java.io.Serializable;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户角色
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

@ApiModel(value="SysUserRoleDTO对象", description="用户角色")
public class SysUserRoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "主键id")
	private Long id;


	@ApiModelProperty(value = "用户id")
	private Long userId;


	@ApiModelProperty(value = "角色id")
	private Long roleId;


	public static final String ID = "id";
	public static final String USER_ID = "userId";
	public static final String ROLE_ID = "roleId";

}
