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
 * 企业职务配置
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

@ApiModel(value = "EnterpriseJobDTO对象", description = "企业职务配置")
public class EnterpriseJobDTO implements Serializable {

	private static final long		serialVersionUID	= 1L;

	private Long					id;

	@ApiModelProperty(value = "企业部门表ID")
	private Long					departmentId;

	@ApiModelProperty(value = "职务代码")
	private String					jobCode;

	@ApiModelProperty(value = "职务名称")
	private String					jobName;

	@ApiModelProperty(value = "记录创建用户")
	private String					createUser;

	@ApiModelProperty(value = "记录最后更新用户")
	private String					updateUser;

	@ApiModelProperty(value = "预留1")
	private String					parameter1;

	@ApiModelProperty(value = "预留2")
	private String					parameter2;

	@ApiModelProperty(value = "是否同步（0：是，1：否）")
	private Integer					isSync;

	/**
	 * 记录创建时间
	 */
	@ApiModelProperty(hidden = true)
	private LocalDateTime			gmtCreate;
	/**
	 * 记录最后修改时间
	 */
	@ApiModelProperty(hidden = true)
	private LocalDateTime			gmtUpdate;

	@ApiModelProperty(hidden = true)
	private EnterpriseDepartmentDTO	enterpriseDepartment;

	public static final String		ID					= "id";
	public static final String		DEPARTMENT_ID		= "departmentId";
	public static final String		JOB_CODE			= "jobCode";
	public static final String		JOB_NAME			= "jobName";
	public static final String		CREATE_USER			= "createUser";
	public static final String		UPDATE_USER			= "updateUser";
	public static final String		PARAMETER1			= "parameter1";
	public static final String		PARAMETER2			= "parameter2";
	public static final String		IS_SYNC				= "isSync";

}
