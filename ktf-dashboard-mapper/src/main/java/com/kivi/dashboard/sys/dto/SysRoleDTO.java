package com.kivi.dashboard.sys.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.kivi.framework.model.TreeNode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

@ApiModel(value = "SysRoleDTO对象", description = "角色")
public class SysRoleDTO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(value = "主键id")
	private Long				id;

	@ApiModelProperty(value = "角色名")
	private String				name;

	@ApiModelProperty(value = "排序号")
	private Integer				seq;

	@ApiModelProperty(value = "简介")
	private String				description;

	@ApiModelProperty(value = "状态(0：开启，1：关闭)")
	private Integer				status;

	@ApiModelProperty(value = "记录创建者ID")
	private Long				createUserId;

	private LocalDateTime		gmtCreate;

	/**
	 * 角色所拥有的菜单ID集合
	 */
	private List<Long>			resourceIdList;

	/**
	 * 选择树
	 */
	private List<TreeNode>		resourceNodeList;

	public static final String	ID					= "id";
	public static final String	NAME				= "name";
	public static final String	SEQ					= "seq";
	public static final String	DESCRIPTION			= "description";
	public static final String	STATUS				= "status";
	public static final String	CREATE_USER_ID		= "createUserId";

}
