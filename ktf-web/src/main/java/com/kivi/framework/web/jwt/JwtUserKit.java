package com.kivi.framework.web.jwt;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.kivi.framework.constant.enums.UserType;
import com.kivi.framework.dto.JwtUserDTO;
import com.kivi.framework.util.NumUtil;
import com.kivi.framework.util.kit.ByteStringKit;
import com.kivi.framework.util.kit.StrKit;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class JwtUserKit extends JwtUserDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // 用户iD
    // @Builder.Default
    // private Long id = 0L;

    // 用户标识
    // @Builder.Default
    // private String identifier = "";

    //// 用户类型
    // private UserType userType;

    public String[] audience() {
        String[] result = new String[3];
        result[0] = id.toString();
        result[1] = encode(identifier);
        result[2] = userType == null ? "" : userType.code;

        return result;
    }

    public static JwtUserDTO audience( List<String> audiences ) {
        JwtUserDTO result = JwtUserDTO.builder()
                .id(Long.valueOf(audiences.get(0)))
                .identifier(decode(audiences.get(1)))
                .userType(UserType.userType(audiences.get(2)))
                .build();
        return result;
    }

    private static String encode( final String identifier ) {
        if (!NumUtil.isNum(identifier))
            return identifier;

        String data = padding(identifier);

        byte[] bdata = ByteStringKit.toBytes(data, ByteStringKit.HEX);
        ArrayUtils.reverse(bdata);

        return ByteStringKit.toString(bdata, ByteStringKit.BASE64);
    }

    private static String decode( final String identifier ) {
        if (!NumUtil.isNum(identifier))
            return identifier;

        byte[] rdata = ByteStringKit.toBytes(identifier, ByteStringKit.BASE64);
        ArrayUtils.reverse(rdata);

        String result = ByteStringKit.toString(rdata, ByteStringKit.HEX);
        int pads = Integer.parseInt(StrKit.subSuf(result, result.length() - 1));

        return result.substring(0, result.length() - pads);
    }

    private static String padding( final String identifier ) {
        final String[] pad = { "22", "1" };
        int mod = identifier.length() % 2;

        StringBuilder builder = new StringBuilder();
        builder.append(identifier);
        builder.append(pad[mod]);

        return builder.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
                .append("id=").append(id).append(", ")
                .append("identifier=").append(identifier).append(", ")
                .append("userType=").append(userType.name());
        return builder.toString();
    }

}
