package com.kivi.framework.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ResourceVo对象", description = "菜单")
@Data
public class ResourceVo implements Serializable {
	/**
	* 
	*/
	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(value = "应用ID")
	private Long				id;

	@ApiModelProperty(value = "应用ID，默认值0")
	private Long				applicationId;

	@ApiModelProperty(value = "父级资源ID")
	private Long				parentId;

	@ApiModelProperty(value = "资源名称")
	private String				name;

	@ApiModelProperty(value = "资源路径")
	private String				url;

	@ApiModelProperty(value = "资源介绍")
	private String				description;

	@ApiModelProperty(value = "资源图标")
	private String				icon;

	@ApiModelProperty(value = "排序")
	private Integer				seq;

	@ApiModelProperty(value = "资源类别(0：菜单，1：按钮)")
	private Integer				resourceType;

	@ApiModelProperty(value = "是否隐藏菜单（0：显示，1：隐藏）")
	private Integer				hidden;

	@ApiModelProperty(value = "状态(0：开，1：关）")
	private Integer				status;

	@ApiModelProperty(value = "记录创建用户")
	private String				createUser;

	@ApiModelProperty(value = "记录最后修改用户")
	private String				updateUser;

	@ApiModelProperty(value = "记录创建时间")
	private LocalDateTime		gmtCreate;

	@ApiModelProperty(value = "记录最后修改时间")
	private LocalDateTime		gmtUpdate;

	/**
	 * 父菜单名称
	 */
	@ApiModelProperty(value = "父菜单名称")
	private String				parentName;

	/**
	 * ztree属性，菜单是否打开
	 */
	private Boolean				open;

	private List<ResourceVo>	list;

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.parentId, this.icon, this.name, this.url, this.resourceType);
	}

	@Override
	public boolean equals(Object obj) {
		return (this.hashCode() == obj.hashCode());
	}

}
