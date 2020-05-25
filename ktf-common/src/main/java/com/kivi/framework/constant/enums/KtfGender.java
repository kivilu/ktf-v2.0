package com.kivi.framework.constant.enums;

import java.util.Arrays;
import java.util.Optional;

import com.kivi.framework.util.kit.NumberKit;

/**
 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
 * 
 * @author xueqi
 *
 */
public enum KtfGender {
	UNKNOWN(0, "0", "未知"),
		MAN(1, "1", "男性"),
		WOMEN(2, "2", "女性");

	private KtfGender(int code, String text, String desc) {
		this.code	= code;
		this.text	= text;
		this.desc	= desc;
	}

	public int		code;
	public String	text;
	public String	desc;

	public static KtfGender valueOf(Integer code) {
		Optional<KtfGender> op = Arrays.asList(KtfGender.values()).stream()
				.filter(gender -> NumberKit.equals(gender.code, code)).findAny();

		return op.isPresent() ? op.get() : UNKNOWN;
	}
}
