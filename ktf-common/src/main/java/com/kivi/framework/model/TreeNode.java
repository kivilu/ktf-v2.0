package com.kivi.framework.model;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * @Description vue 树形控件对象
 */
@Data
public class TreeNode implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * id
	 */
	private String				id;

	/**
	 * label
	 */
	private String				label;

	private Integer				type;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<TreeNode>		children;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
