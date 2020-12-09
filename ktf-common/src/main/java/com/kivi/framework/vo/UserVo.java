package com.kivi.framework.vo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description：UserVo
 */
@ApiModel(value = "UserVo对象", description = "用户")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserVo implements Serializable {
	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(value = "主键id")
	private Long				id;

	@ApiModelProperty(value = "客户ID")
	private Long				cifId;

	@ApiModelProperty(value = "登录类型，00：手机号，01：邮箱，02：用户名，03：微信，04：支付宝，05：微博")
	private String				loginMode;

	@ApiModelProperty(value = "登陆名")
	private String				loginName;

	@ApiModelProperty(value = "密码")
	private String				password;

	@ApiModelProperty(value = "用户类别（0：超级管理员，1：系统管理员、2：业务管理员、3：密钥操作员、4：密钥审核员、5：审计管理员、6：审计员）")
	private Integer				userType;

	@ApiModelProperty(value = "姓名")
	private String				name;

	@ApiModelProperty(value = "应用ID，默认值0")
	private Long				appId;

	@ApiModelProperty(value = "用户状态(0：正常，1：不正常)")
	private Integer				status;

	@ApiModelProperty(value = "所属企业Id")
	private Long				orgId;

	/**
	 * 密码加密盐
	 */
	@ApiModelProperty(hidden = true)
	private String				salt;

	@ApiModelProperty(hidden = true)
	private List<Long>			roleIds;

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
