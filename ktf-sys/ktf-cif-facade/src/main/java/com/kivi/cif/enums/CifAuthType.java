package com.kivi.cif.enums;

import java.util.Arrays;
import java.util.Optional;

import lombok.Getter;

/**
 * 用户类型：0：CA签名，1：CPK签名， 9 密码验证
 * 
 * @author Eric
 *
 */
@Getter
public enum CifAuthType {
	CA(0, "CA签名"),
		CPK(1, "CPK签名"),
		IPK(2, "IPK签名"),
		PASS(9, "密码验证");

	public final int	code;
	public final String	desc;

	private CifAuthType(int code, String desc) {
		this.code	= code;
		this.desc	= desc;
	}

	public static CifAuthType valueOf(int code) {
		Optional<CifAuthType> op = Arrays.asList(CifAuthType.values()).stream().filter(t -> t.code == code).findFirst();

		return op.isPresent() ? op.get() : null;
	}

}
