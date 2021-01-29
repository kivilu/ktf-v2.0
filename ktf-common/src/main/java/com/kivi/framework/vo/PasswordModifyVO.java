package com.kivi.framework.vo;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "PasswordModifyVO", description = "密码修改")
public class PasswordModifyVO implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(
			position = 2,
			value = "用户旧密码，md5(12345)",
			required = true,
			dataType = "String",
			example = "827ccb0eea8a706c4c34a16891f84e7b")
	@NotBlank
	@Size(max = 32, message = "用户旧密码摘要不能为空")
	private String				password;

	@ApiModelProperty(
			position = 2,
			value = "用户新密码，md5(12345)",
			required = true,
			dataType = "String",
			example = "827ccb0eea8a706c4c34a16891f84e7b")
	@NotBlank
	@Size(max = 32, message = "用户新密码摘要不能为空")
	private String				newPassword;

	@ApiModelProperty(position = 3, value = "验证码", required = false, dataType = "String", example = "2wq4")
	@Size(max = 6, message = "验证码长度为6")
	private String				smsCode;

}
