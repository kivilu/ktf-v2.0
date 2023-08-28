package com.kivi.framework.constant.enums;

import lombok.Getter;

@Getter
public enum LoginType {
	USERNAME(0),
		UKEY(1),
		CUSTOMIZE(2);

	private final int code;

	private LoginType(int code) {
		this.code = code;
	}
}
