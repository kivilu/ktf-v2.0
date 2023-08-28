package com.kivi.mp.sys.dto;

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

	@ApiModelProperty(value = "变量代码")
	private String				varCode;

	@ApiModelProperty(value = "变量名称")
	private String				varName;

	@ApiModelProperty(value = "父变量名称")
	private String				parentName;

	@ApiModelProperty(value = "数据是否同步(0:是,1:否)")
	private Integer				isSync;

	@ApiModelProperty(value = "记录创建者（用户）")
	private String				createUser;

	@ApiModelProperty(value = "记录最后修改者（用户）")
	private String				updateUser;

	public static final String	ID					= "id";
	public static final String	PARENT_ID			= "parentId";
	public static final String	VAR_CODE			= "varCode";
	public static final String	VAR_NAME			= "varName";
	public static final String	IS_SYNC				= "isSync";
	public static final String	CREATE_USER			= "createUser";
	public static final String	UPDATE_USER			= "updateUser";

}
