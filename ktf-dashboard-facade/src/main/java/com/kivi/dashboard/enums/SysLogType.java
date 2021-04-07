package com.kivi.dashboard.enums;

import lombok.Getter;

@Getter
public enum SysLogType {
	Login(0),
		Operate(1),
		Audit(2);

	private final int code;

	private SysLogType(int code) {
		this.code = code;
	}
}
