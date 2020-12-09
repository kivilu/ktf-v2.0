package com.kivi.framework.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @Description 密码表单
 */

@Data
public class PasswordForm implements Serializable {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	/**
	 * 原密码
	 */
	@NotBlank(message = "原密码不允许为空")
	private String				password;
	/**
	 * 新密码
	 */
	@NotBlank(message = "新密码不允许为空")
	private String				newPassword;
}
