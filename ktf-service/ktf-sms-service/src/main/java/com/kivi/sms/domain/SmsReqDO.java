package com.kivi.sms.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kivi.sms.enums.SmsBizType;

import lombok.Data;

@Data
public class SmsReqDO {

    /**
     * 短信业务类型
     */
    private SmsBizType   smsBizType;

    /**
     * 电话号码
     */
    private List<String> phoneNumbers = new ArrayList<>();

    /**
     * 短信模板参数
     */
    Map<String, String>  templateParam;

    public void clear() {
        phoneNumbers.clear();
    }

    public SmsReqDO phoneNumber( String phoneNumber ) {
        phoneNumbers.add(phoneNumber);
        return this;
    }

}
