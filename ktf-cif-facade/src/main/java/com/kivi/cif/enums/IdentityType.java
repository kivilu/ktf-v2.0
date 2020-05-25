package com.kivi.cif.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * 登录类型，00：手机号，01：邮箱，02：用户名，03：微信，04：支付宝，05：微博
 * 
 * @author Eric
 *
 */
public enum IdentityType {
    MOBILE( "00", "手机号" ),
    EMAIL( "01", "邮箱" ),
    USER_NAME( "02", "用户名" ),
    WEIXIN( "03", "微信" ),
    ALIPAY( "04", "支付宝" ),
    WEIBO( "05", "微博" );

    public final String code;
    public final String desc;

    private IdentityType( String code, String desc ) {
        this.code = code;
        this.desc = desc;
    }

    public static IdentityType valueof( String code ) {
        Optional<IdentityType> op = Arrays.stream(IdentityType.values())
                .filter(s-> s.code.equals(code))
                .findFirst();

        return op.isPresent() ? op.get() : null;
    }

}
