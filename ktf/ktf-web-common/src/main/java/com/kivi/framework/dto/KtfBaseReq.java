package com.kivi.framework.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "KtfBaseReq", description = "接口请求基础Bean")
public class KtfBaseReq<T> implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(
			position = 1,
			value = "接口版本 ",
			required = true,
			dataType = "String",
			notes = "接口版本 ",
			example = "1.0.0")
	@NotEmpty(message = "版本号不能为空")
	@Size(max = 8, message = "版本号长度最大为8")
	private String				version				= "1.0.0";

	@ApiModelProperty(
			position = 2,
			value = "业务流水号 ",
			required = true,
			dataType = "String",
			notes = "业务流水号 ",
			example = "")
	@NotEmpty(message = "业务流水号不能为空")
	@Size(max = 32, message = "业务流水号长度最大为32")
	private String				bizSeqId;

	@ApiModelProperty(
			position = 4,
			value = "请求报文内容",
			required = true,
			dataType = "String",
			notes = "请求报文内容",
			example = "")
	private T					data;

	@ApiModelProperty(position = 3, value = "签名数据", dataType = "String", notes = "签名数据", example = "")
	private String				sign;
}
