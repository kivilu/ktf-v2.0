package com.kivi.sms.service;

public interface SmsOtpService {

    /**
     * 生成短信验证码
     * 
     * @return
     */
    String smsOtp( String phoneNumber );

    /**
     * 验证短信验证码
     * 
     * @param otp
     * @return
     */
    Boolean smsOtp( String phoneNumber, String otp );
}
