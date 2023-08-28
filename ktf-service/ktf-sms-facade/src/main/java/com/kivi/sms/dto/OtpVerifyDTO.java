package com.kivi.sms.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class OtpVerifyDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public OtpVerifyDTO() {

    }

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 生成时间
     */
    private Long   createTime;

    /**
     * 验证码
     */
    private String otp;

}
