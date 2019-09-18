package com.kivi.dashboard.shiro;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

import lombok.Data;

/**
 * @Description 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
 */
@Data
public class ShiroUser implements Serializable {
	private static final long	serialVersionUID	= 1L;

	private Long				id;

	private String				loginName;

	private String				name;

	// 用户类型（0-超级用户，1-企业用户，2-监管用户）
	private Integer				userType;

	// 状态
	private Integer				status;

	// 是否领导（0-是，1-否）
	private Integer				isLeader;

	// 企业Id
	private Long				enterpriseId;

	// 所属部门Id
	private Long				departmentId;

	// 所属职务Id
	private Long				jobId;

	/**
	 * 角色管理的企业ID集合
	 */
	private List<Long>			enterpriseIdList;

	/**
	 * 用户拥有的权限集合
	 */
	private List<String>		urlSet;

	/**
	 * 用户拥有的角色集合
	 */
	private List<String>		roles;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}