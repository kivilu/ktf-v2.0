package com.kivi.framework.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.kivi.framework.constant.enums.UserType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "PasswordResetVO", description = "密码重置")
public class PasswordResetVO implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(position = 1, value = "登录账号", required = true, dataType = "String", example = "13800100500")
	@NotBlank
	@Size(max = 32, message = "登录账号不能为空")
	private String				identifier;

	@ApiModelProperty(
			position = 2,
			value = "用户密码，md5(12345)",
			required = true,
			dataType = "String",
			example = "827ccb0eea8a706c4c34a16891f84e7b")
	@NotBlank
	@Size(max = 32, message = "用户密码摘要不能为空")
	private String				password;

	@ApiModelProperty(position = 3, value = "验证码", required = false, dataType = "String", example = "2wq4")
	@Size(max = 6, message = "验证码长度为6")
	private String				smsCode;

	@ApiModelProperty(
			position = 4,
			value = "用户类型",
			required = true,
			dataType = "com.kivi.framework.constant.enums.UserType",
			example = "ADMIN")
	@NotNull(message = "UserType不能为空")
	private UserType			userType;

}
