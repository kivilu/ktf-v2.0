package com.kivi.framework.constant.enums;

import java.util.Arrays;
import java.util.Optional;

import lombok.Getter;

/**
 * 用户类型：00-任意，01-管理员，02：用户，03：操作员，04：审核员
 * 
 * @author Eric
 *
 */
@Getter
public enum UserType {
	ANYONE(0, "00"), // 任意
		ADMIN(1, "01"), // 管理员
		USER(2, "02"), // 用户
		OPERATOR(3, "03"), // 操作员
		REVIEWER(4, "04");// 审核员

	private final static UserType[]	userRoles	= { USER };

	public final Integer			value;
	public final String				code;

	private UserType(Integer value, String code) {
		this.code	= code;
		this.value	= value;
	}

	public static UserType userType(String code) {
		Optional<UserType> op = Arrays.stream(UserType.values()).filter(s -> s.code.equals(code)).findFirst();

		return op.isPresent() ? op.get() : null;
	}

	public static UserType valueOf(int value) {
		Optional<UserType> op = Arrays.stream(UserType.values()).filter(s -> s.value == value).findFirst();

		return op.isPresent() ? op.get() : null;
	}

	public byte toByte() {
		Byte b = Byte.parseByte(code);

		return b.byteValue();
	}

	public boolean isUser() {
		Optional<UserType> op = Arrays.stream(userRoles).filter(s -> s.code.equals(this.code)).findFirst();

		return op.isPresent();
	}
}
