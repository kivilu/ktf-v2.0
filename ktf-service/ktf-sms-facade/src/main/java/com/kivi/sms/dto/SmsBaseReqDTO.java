package com.kivi.sms.dto;

import java.io.Serializable;

import com.kivi.sms.enums.SmsBizType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@ApiModel( value = "SmsBaseReqDTO", description = "短信基础DTO" )
@Getter
@SuperBuilder
public class SmsBaseReqDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(
                       position = 1,
                       value = "电话号码",
                       required = true,
                       dataType = "String",
                       example = "13800100500" )
    protected String          phoneNumber;

    /**
     * 业务类型
     */
    @ApiModelProperty(
                       position = 2,
                       value = "业务类型，可选值：regist、resetPwd",
                       required = true,
                       dataType = "com.kivi.sms.enums.SmsBizType",
                       example = "regist" )
    protected SmsBizType      smsBizType;

}
