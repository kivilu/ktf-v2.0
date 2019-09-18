package com.kivi.dashboard.shiro.form;

import lombok.Data;

/**
 * @Description 密码表单
 */

@Data
public class PasswordForm {
	/**
	 * 原密码
	 */
	private String	password;
	/**
	 * 新密码
	 */
	private String	newPassword;
}
