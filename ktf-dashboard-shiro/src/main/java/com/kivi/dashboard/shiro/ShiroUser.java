package com.kivi.dashboard.shiro;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.kivi.framework.util.JacksonUtils;

import lombok.Data;

/**
 * @Description 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
 */
@Data
public class ShiroUser implements Serializable {
	private static final long	serialVersionUID	= 1L;

	private Long				id;

	private Long				cifId;

	private String				loginName;

	private String				name;

	// 用户类型（0-超级用户，1-企业用户，2-监管用户）
	private Integer				userType;

	// 状态
	private Integer				status;

	// 企业Id
	private Long				orgId;

	// 所属部门Id
	private Long				deptId;

	// 所属职务Id
	private Long				titileId;

	/**
	 * 用户拥有的角色集合
	 */
	private List<Long>			roleIds;

	/**
	 * 用户拥有的权限集合
	 */
	private Set<String>			urlSet;

	/**
	 * 角色管理的企业ID集合
	 */
	private List<Long>			corpIdList;

	@Override
	public String toString() {
		return JacksonUtils.toJson(this);
	}

	public Long getRoleId() {
		if (roleIds == null || roleIds.isEmpty())
			return null;

		return roleIds.get(0);
	}

}
