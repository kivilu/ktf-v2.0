package com.kivi.framework.form;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

/**
 * @Description 登录表单
 */
@Data
public class LoginForm {
	/**
	 * 用户名
	 */
	@NotBlank(message = "用户名不能为空")
	private String	userName;
	/**
	 * 密码
	 */
	// @NotBlank(message = "密码不能为空")
	private String	password;
	/**
	 * 验证码
	 */
	private String	captcha;
	/**
	 * 请求唯一ID
	 */
	// @NotBlank(message = "uuid不能为空")
	private String	uuid;

	/**
	 * 签名信息
	 */
	// @NotBlank(message = "签名信息不能为空")
	private String	sign;

	/**
	 * 记住
	 */
	// private Integer rememberMe;
}
