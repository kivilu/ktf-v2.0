package com.kivi.dashboard.permission.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 监管用户与企业关联
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

@ApiModel(value = "SysUserOrgDTO对象", description = "监管用户与企业关联")
public class SysUserOrgDTO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(value = "角色id")
	private Long				userId;

	@ApiModelProperty(value = "企业id")
	private Long				orgId;

	public static final String	ID					= "id";
	public static final String	USER_ID				= "userId";
	public static final String	ORG_ID				= "orgId";

}
