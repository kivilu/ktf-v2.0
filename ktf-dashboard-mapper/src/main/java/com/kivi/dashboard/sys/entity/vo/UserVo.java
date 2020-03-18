package com.kivi.dashboard.sys.entity.vo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.kivi.dashboard.sys.entity.SysRole;
import com.kivi.dashboard.sys.entity.SysUser;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description：UserVo
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserVo extends SysUser implements Serializable {
	private static final long	serialVersionUID	= 1L;

	private String				enterpriseName;

	private String				departmentName;

	private String				jobName;

	/**
	 * 用户名
	 */
	private String				name;
	/**
	 * 密码
	 */
	private String				password;
	/**
	 * 密码加密盐
	 */
	private String				salt;
	/**
	 * 性别
	 */
	private Integer				sex;
	/**
	 * 手机号
	 */
	private String				phone;
	/**
	 * 邮箱
	 */
	private String				email;

	private List<SysRole>		roles;

	/**
	 * 密码盐
	 */
	public String getCredentialsSalt() {
		return getLoginName() + getSalt();
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}