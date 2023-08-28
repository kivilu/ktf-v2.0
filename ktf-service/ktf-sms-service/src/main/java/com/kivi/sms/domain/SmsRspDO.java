package com.kivi.sms.domain;

import lombok.Data;

@Data
public class SmsRspDO {

    /**
     * 请求ID
     */
    private String  requestId;

    /**
     * 发送回执ID,可根据该ID查询具体的发送状态
     */
    private String  receiptID;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态码的描述
     */
    private String  message;

}
