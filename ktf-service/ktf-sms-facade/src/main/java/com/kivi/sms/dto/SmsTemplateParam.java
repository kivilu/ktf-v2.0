package com.kivi.sms.dto;

import java.util.Map;
import java.util.stream.Collectors;

import com.kivi.framework.converter.BeanConverter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SmsTemplateParam {
	/** 模板参数name */
	private String	name;

	/** 模板参数code */
	private String	code;

	/** 模板参数startTime */
	private String	startTime;

	/** 模板参数endTime */
	private String	endTime;

	/** 模板参数mtname */
	private String	mtname;

	/** 模板参数content */
	private String	content;

	/** 模板参数days */
	private String	days;

	public Map<String, String> map() {
		Map<String, Object> map = BeanConverter.beanToMap(this);

		return map.entrySet().stream()
				.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue().toString()));
	}
}
