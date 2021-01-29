package com.kivi.dashboard.enums;

import lombok.Getter;

/**
 * 数据字典常量枚举
 * 
 * @author xueqi
 *
 */

@Getter
public enum DicEnum {
	DATA_TYPE("DATA_TYPES", "数据类型"),
		STS_RUNTIME("RUNTIME_SETTINGS", "系统运行配置"),
		STS_VUE("VUE_SETTINGS", "VUE前端设置"),
		MODULE_NAME("MODULE_NAME", "模块名称"),
		LOG_OPERATIONS("LOG_OPERATIONS", "操作日志"),
		LOG_REC_OPTS("LOG_REC_OPERATIONS", "需记录的操作"),
		LOG_ADT_OPTS("LOG_AUDIT_OPERATIONS", "需审计的操作");

	private final String	code;
	private final String	desc;

	private DicEnum(String code, String desc) {
		this.code	= code;
		this.desc	= desc;
	}
}
