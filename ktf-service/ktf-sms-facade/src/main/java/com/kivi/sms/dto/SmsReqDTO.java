package com.kivi.sms.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SmsReqDTO extends SmsBaseReqDTO {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * 口令
	 */
	private String				otpCode;

	/**
	 * 开始时间
	 */
	private String				startTime;

	/**
	 * 结束时间
	 */
	private String				endTime;

	/**
	 * 授权人姓名
	 */
	private String				mtname;

	/** 模板参数content */
	private String				content;
}
