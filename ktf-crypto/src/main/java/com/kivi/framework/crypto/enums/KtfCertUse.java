package com.kivi.framework.crypto.enums;

/**
 * 证书用途，0-用户证书，1-系统证书
 * 
 * @author xueqi
 *
 */
public enum KtfCertUse {

	USER(0, "用户证书"),
		SYS(1, "系统证书");
	public Integer	code;
	public String	desc;

	private KtfCertUse(Integer code, String desc) {
		this.code	= code;
		this.desc	= desc;
	}

	public static KtfCertUse forValue(Integer code) {
		KtfCertUse[] values = KtfCertUse.values();
		for (KtfCertUse v : values) {
			if (v.code.equals(code)) {
				return v;
			}
		}
		return null;
	}

}
