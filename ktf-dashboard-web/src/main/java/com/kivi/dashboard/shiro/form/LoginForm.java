package com.kivi.dashboard.shiro.form;

import javax.validation.constraints.NotBlank;

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
	private String	username;
	/**
	 * 密码
	 */
	@NotBlank(message = "密码不能为空")
	private String	password;
	/**
	 * 验证码
	 */
	@NotBlank(message = "验证码不能为空")
	private String	captcha;
	/**
	 * 请求唯一ID
	 */
	@NotBlank(message = "uuid不能为空")
	private String	uuid;

	/**
	 * 记住
	 */
	private Integer	rememberMe;
}
