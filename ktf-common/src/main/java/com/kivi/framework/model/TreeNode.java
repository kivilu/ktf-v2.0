package com.kivi.framework.model;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @Description vue 树形控件对象
 */
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

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<TreeNode>		children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
