package com.kivi.demo.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "DemoReqDto", description = "Demo请求DTO")
@Data
public class DemoReqDto<T> implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(value = "version", required = true, notes = "版本号", example = "1.0")
	private String				version;

	@ApiModelProperty(value = "seqId", required = true, notes = "请求流水号", example = "12345678")
	private String				seqId;

	@ApiModelProperty(value = "body", required = true, notes = "请求json对象", example = "{name:\"Alice\"}")
	private T					body;

}
