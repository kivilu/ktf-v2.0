package com.kivi.dashboard.enterprise.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

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

@ApiModel(value = "EnterpriseDepartmentDTO对象", description = "企业部门")
public class EnterpriseDepartmentDTO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	private Long				id;

	@ApiModelProperty(value = "企业部门父ID")
	private Long				parentId;

	@ApiModelProperty(value = "企业ID(对应企业主表ID)")
	private Long				enterpriseId;

	@ApiModelProperty(value = "部门代码(可添加多个部门ID，用逗号隔开，表示该部门可以管理多个部门)")
	private String				departmentCode;

	@ApiModelProperty(value = "部门名称")
	private String				departmentName;

	@ApiModelProperty(value = "预留1")
	private String				parameter1;

	@ApiModelProperty(value = "预留2")
	private String				parameter2;

	@ApiModelProperty(value = "数据是否同步(0:是,1:否)")
	private Integer				isSync;

	/**
	 * 记录创建时间
	 */
	@ApiModelProperty(hidden = true)
	private LocalDateTime		gmtCreate;
	/**
	 * 记录最后修改时间
	 */
	@ApiModelProperty(hidden = true)
	private LocalDateTime		gmtUpdate;

	@ApiModelProperty(value = "记录创建者(用户)")
	private String				createUser;

	@ApiModelProperty(value = "记录最后修改者(用户)")
	private String				updateUser;

	@ApiModelProperty(hidden = true)
	private EnterpriseDTO		enterprise;

	public static final String	ID					= "id";
	public static final String	PARENT_ID			= "parentId";
	public static final String	ENTERPRISE_ID		= "enterpriseId";
	public static final String	DEPARTMENT_CODE		= "departmentCode";
	public static final String	DEPARTMENT_NAME		= "departmentName";
	public static final String	PARAMETER1			= "parameter1";
	public static final String	PARAMETER2			= "parameter2";
	public static final String	IS_SYNC				= "isSync";
	public static final String	CREATE_USER			= "createUser";
	public static final String	UPDATE_USER			= "updateUser";

}
