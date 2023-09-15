package com.kivi.framework.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.kivi.framework.models.TreeNode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description：UserVo
 */
@Deprecated
@ApiModel(value = "RoleVo对象", description = "角色")
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleVo implements Serializable {

	/**
	 *
	 */
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

	// 拥有的权限列表
	private List<ResourceVo>	permissions;

}
