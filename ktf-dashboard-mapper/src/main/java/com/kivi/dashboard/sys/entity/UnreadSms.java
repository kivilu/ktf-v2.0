package com.kivi.dashboard.sys.entity;

import java.io.Serializable;
import java.util.List;

import com.kivi.dashboard.sys.dto.Sms;
import com.kivi.framework.util.JacksonUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description 未读消息
 */
@Data
@EqualsAndHashCode
public class UnreadSms implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * 未读消息数量
	 */
	private Integer				count;

	private List<Sms>			list;

	@Override
	public String toString() {
		return JacksonUtils.toJson(this);
	}
}
