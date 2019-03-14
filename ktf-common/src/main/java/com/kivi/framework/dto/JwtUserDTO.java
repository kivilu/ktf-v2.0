package com.kivi.framework.dto;

import java.io.Serializable;

import com.kivi.framework.constant.enums.UserType;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class JwtUserDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // 用户iD
    @Builder.Default
    protected Long            id               = 0L;

    // 用户标识
    @Builder.Default
    protected String          identifier       = "";

    // 用户姓名
    @Builder.Default
    protected String          name             = "---";

    // 用户类型
    protected UserType        userType;

}
