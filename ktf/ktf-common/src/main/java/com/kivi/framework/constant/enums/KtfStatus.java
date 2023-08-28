package com.kivi.framework.constant.enums;

import java.util.Arrays;
import java.util.Optional;

import com.kivi.framework.util.kit.NumberKit;

public enum KtfStatus {
	ENABLED(0, "0", "有效"),
		LOCKED(1, "1", "锁定"),
		DISABLED(2, "2", "无效"),
		PROCESSING(3, "3", "处理中"),
		DELETED(4, "4", "已删除"),
		FREEZED(5, "5", "已冻结"),
		INIT(9, "9", "初始");

	private KtfStatus(int code, String text, String desc) {
		this.code	= code;
		this.text	= text;
		this.desc	= desc;
	}

	public int		code;
	public String	text;
	public String	desc;

	public static String valueOf(Integer code) {
		Optional<KtfStatus> op = Arrays.asList(KtfStatus.values()).stream()
				.filter(status -> NumberKit.equals(status.code, code)).findAny();

		return op.isPresent() ? op.get().desc : "";
	}

}
