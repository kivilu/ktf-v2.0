package com.kivi.sys.sys.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysMenu implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * 目录父ID
	 */
	Long						catalogPid;

	/**
	 * 目录名称
	 */
	String						catalog;

	/**
	 * 目录icon
	 */
	String						catalogIcon;

	/**
	 * 菜单ID
	 */
	Long						id;

	/**
	 * 菜单父ID
	 */
	Long						pid;

	/**
	 * 资源名称
	 */
	private String				name;
	/**
	 * 资源路径
	 */
	private String				url;
	/**
	 * 资源图标
	 */
	private String				icon;

	/**
	 * 资源类别(0：目录，1：菜单)
	 */
	private Integer				resourceType;

}
