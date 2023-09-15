package com.kivi.sys.sys.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统应用
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

@ApiModel(value = "SysApplicationDTO对象", description = "系统应用")
public class SysApplicationDTO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(value = "主键ID")
	private Long				id;

	@ApiModelProperty(value = "应用代码")
	private String				code;

	@ApiModelProperty(value = "应用名称")
	private String				name;

	private LocalDateTime		gmtCreate;
	private LocalDateTime		gmtUpdate;

	@ApiModelProperty(value = "记录创建者(用户)")
	private String				createUser;

	@ApiModelProperty(value = "记录修改者(用户)")
	private String				updateUser;

	public static final String	ID					= "id";
	public static final String	CODE				= "code";
	public static final String	NAME				= "name";
	public static final String	CREATE_USER			= "createUser";
	public static final String	UPDATE_USER			= "updateUser";

}
