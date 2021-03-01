package com.kivi.dashboard.sys.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据字典
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

@ApiModel(value = "SysDicDTO对象", description = "数据字典")
public class SysDicDTO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(value = "主键ID")
	private Long				id;

	@ApiModelProperty(value = "父变量ID")
	private Long				parentId;

	@ApiModelProperty(value = "变量名称")
	private String				varName;

	@ApiModelProperty(value = "变量代码")
	private String				varCode;

	@ApiModelProperty(value = "变量值")
	private String				varValue;

	@ApiModelProperty(value = "数据类型：string，boolean，number，array，json")
	private String				type;

	@ApiModelProperty(value = "状态（0-正常，1-禁用）")
	private Integer				status;

	@ApiModelProperty(value = "是否有子菜单")
	private Boolean				hasChildren;

	@ApiModelProperty(value = "是否叶子")
	private Boolean				isLeaf;

	@ApiModelProperty(hidden = true)
	private Boolean				localCached			= false;

	public static final String	ID					= "id";
	public static final String	PARENT_ID			= "parentId";
	public static final String	VAR_CODE			= "varCode";
	public static final String	VAR_NAME			= "varName";
	public static final String	TYPE				= "type";
	public static final String	STATUS				= "status";

}
