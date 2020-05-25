package com.kivi.framework.constant.enums;

import java.util.Arrays;
import java.util.Optional;

import com.kivi.framework.util.kit.NumberKit;

/**
 * 登录类型，00：手机号，01：邮箱，02：用户名，03：微信，04：支付宝，05：微博
 * 
 * @author xueqi
 *
 */
public enum KtfIdentifyType {
	PHONE(0, "00", "手机号"),
		EMAIL(1, "01", "邮箱"),
		USERNAME(2, "02", "用户名"),
		WEIXIN(3, "03", "微信"),
		ALIPAY(4, "04", "支付宝"),
		WEIBO(5, "05", "微博");

	private KtfIdentifyType(int code, String text, String desc) {
		this.code	= code;
		this.text	= text;
		this.desc	= desc;
	}

	public int		code;
	public String	text;
	public String	desc;

	public static KtfIdentifyType valueOf(Integer code) {
		Optional<KtfIdentifyType> op = Arrays.asList(KtfIdentifyType.values()).stream()
				.filter(gender -> NumberKit.equals(gender.code, code)).findAny();

		return op.isPresent() ? op.get() : PHONE;
	}
}
