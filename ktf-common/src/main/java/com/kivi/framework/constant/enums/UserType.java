package com.kivi.framework.constant.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * 用户类型：01-管理员，02：用户
 * 
 * @author Eric
 *
 */
public enum UserType {
    ADMIN( "01" ), // 管理员
    USER( "02" );// 用户

    private final static UserType[] userRoles = { USER };

    public final String             code;

    private UserType( String code ) {
        this.code = code;
    }

    public static UserType userType( String code ) {
        Optional<UserType> op = Arrays.stream(UserType.values())
                .filter(s-> s.code.equals(code))
                .findFirst();

        return op.isPresent() ? op.get() : null;
    }

    public byte toByte() {
        Byte b = Byte.parseByte(this.code);

        return b.byteValue();
    }

    public boolean isUser() {
        Optional<UserType> op = Arrays.stream(userRoles)
                .filter(s-> s.code.equals(this.code))
                .findFirst();

        return op.isPresent();
    }
}
