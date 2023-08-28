package com.kivi.framework.form;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 登录表单
 */
@Data
public class LoginForm {
	@ApiModelProperty(value = "用户名", required = true)
	@NotBlank(message = "用户名不能为空")
	private String	userName;

	@ApiModelProperty(value = "密码")
	private String	password;

	@ApiModelProperty(value = "验证码")
	private String	captcha;

	@ApiModelProperty(value = "请求唯一ID")
	private String	uuid;

	@ApiModelProperty(value = "签名信息")
	private String	sign;

	@ApiModelProperty(value = "公钥证书文件内容base64（非公钥）")
	private String	cert;

	@ApiModelProperty(value = "校验类型  0：CA签名 1：CPK签名 9：密码")
	private Integer	type;

}
