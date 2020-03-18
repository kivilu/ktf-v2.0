package com.kivi.dashboard.sys.dto;

import java.io.Serializable;
import java.util.Date;

import com.kivi.framework.util.JacksonUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消息推送记录表
 *
 */
@Data
@EqualsAndHashCode()
public class Sms implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private Long				id;
	/**
	 * 1-MQ消息，2-kettle消息,3-页面消息
	 */
	private Integer				smsType;

	/**
	 * 消息title
	 */
	private String				title;
	/**
	 * 推送内容
	 */
	private String				content;
	/**
	 * 要求推送时间
	 */
	private Date				smsTime;
	/**
	 * 要求推送次数
	 */
	private Integer				smsCount;
	/**
	 * 推送间隔时间(秒)
	 */
	private Integer				intervalTime;
	/**
	 * 0:待执行推送；1：推送成功；2：推送失败;
	 */
	private Integer				status;
	/**
	 * 预留1
	 */
	private String				parameter1;
	/**
	 * 预留2
	 */
	private String				parameter2;

	/**
	 * 消息距当前时间
	 */
	private String				timeStr;

	@Override
	public String toString() {
		return JacksonUtils.toJson(this);
	}

}
