package com.kivi.sms.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@ApiModel( value = "OtpReqDTO", description = "动态验证码" )
@Getter
@SuperBuilder
public class SmsOtpReqDTO extends SmsBaseReqDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

}
