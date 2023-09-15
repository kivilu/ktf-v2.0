package com.kivi.sys.enums;

import lombok.Getter;

/**
 * 数据字典常量枚举
 * 
 * @author xueqi
 *
 */

@Getter
public enum DicEnum {
	SYS_CONSTANT("SYS_CONSTANT", "系统常量"),
		STS_RUNTIME("RUNTIME_SETTINGS", "运行配置"),
		STS_VUE("VUE_SETTINGS", "VUE前端设置"),
		LOG_OPERATIONS("LOG_OPERATIONS", "操作日志"),
		MODULE_NAME("MODULE_NAME", "模块名称"),
		METHOD_NAME("METHOD_NAME", "方法名称"),;

	private final String	code;
	private final String	desc;

	private DicEnum(String code, String desc) {
		this.code	= code;
		this.desc	= desc;
	}
}
