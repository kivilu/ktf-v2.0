package com.kivi.framework.constant.enums;

import java.util.Arrays;
import java.util.Optional;

import lombok.Getter;

/**
 * 用户类型：0：超级管理员，1：企业用户，2：监管用户，3：个人用户，
 * 
 * @author Eric
 *
 */
@Getter
public enum UserType {
    SYS(0, "00", "系统用户"), ENT(1, "01", "企业用户"), SVN(2, "02", "监管用户"), USR(3, "03", "个人用户"), SRV(99, "99", "服务");

    public final int value;
    public final String code;
    public final String desc;

    private UserType(int value, String code, String desc) {
        this.code = code;
        this.value = value;
        this.desc = desc;
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

    public static boolean isSYS(int value) {
        return value == SYS.value;
    }
}
