package com.kivi.dashboard.sys.dto;


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

@ApiModel(value="SysUserEnterpriseDTO对象", description="监管用户与企业关联")
public class SysUserEnterpriseDTO implements Serializable {

    private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "主键id")
	private Long id;


	@ApiModelProperty(value = "角色id")
	private Long userId;


	@ApiModelProperty(value = "企业id列表(;分割)")
	private Long enterpriseId;


	public static final String ID = "id";
	public static final String USER_ID = "userId";
	public static final String ENTERPRISE_ID = "enterpriseId";

}
