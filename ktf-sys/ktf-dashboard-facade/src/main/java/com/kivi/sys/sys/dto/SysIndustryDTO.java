package com.kivi.sys.sys.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 行业代码
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

@ApiModel(value = "SysIndustryDTO对象", description = "行业代码")
public class SysIndustryDTO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(value = "主键ID")
	private Long				id;

	@ApiModelProperty(value = "上级ID")
	private Long				pid;

	@ApiModelProperty(value = "行业代码")
	private String				code;

	@ApiModelProperty(value = "上级行业代码")
	private String				pcode;

	@ApiModelProperty(value = "行业名称")
	private String				name;

	@ApiModelProperty(value = "上级行业名称")
	private String				pname;

	@ApiModelProperty(value = "状态，0无效，1有效")
	private Boolean				status;

	@ApiModelProperty(value = "是否有子菜单")
	private Boolean				hasChildren;

	@ApiModelProperty(value = "是否叶子")
	private Boolean				isLeaf;

	public static final String	ID					= "id";
	public static final String	PID					= "pid";
	public static final String	CODE				= "code";
	public static final String	PCODE				= "pcode";
	public static final String	NAME				= "name";
	public static final String	STATUS				= "status";

}
