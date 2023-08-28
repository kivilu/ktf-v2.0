package com.kivi.demo.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "DemoRspDto", description = "Demo响应DTO")
@Data
public class DemoRspDto<T> implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(value = "version", notes = "版本号", example = "1.0")
	private String				version;

	@ApiModelProperty(value = "seqId", notes = "响应流水号", example = "12345678")
	private String				seqId;

	@ApiModelProperty(value = "rspCode", notes = "响应代码号", example = "200")
	private String				rspCode;

	@ApiModelProperty(value = "body", notes = "响应内容", example = "{name:\"Alice\",age:20}")
	private T					body;

}
