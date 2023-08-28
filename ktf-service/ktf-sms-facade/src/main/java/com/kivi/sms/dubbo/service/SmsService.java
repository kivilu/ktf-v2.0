package com.kivi.sms.dubbo.service;

import com.kivi.framework.dto.warapper.WarpReqDTO;
import com.kivi.sms.dto.OtpVerifyDTO;
import com.kivi.sms.dto.SmsOtpReqDTO;
import com.kivi.sms.dto.SmsReqDTO;

/**
 * 短信相关服务
 * 
 * @author Eric
 *
 */
public interface SmsService {

	public static final String MOCK = "com.kivi.sms.dubbo.mock.SmsServiceMock";

	/**
	 * 发送短信
	 * 
	 * @param otpReqDto
	 * @return
	 */
	Boolean sendMessage(WarpReqDTO<SmsReqDTO> warpReqDTO);

	/**
	 * 发送短信OTP
	 * 
	 * @param otpReqDto
	 * @return
	 */
	Boolean sendOtp(WarpReqDTO<SmsOtpReqDTO> warpReqDTO);

	/**
	 * 验证短信OTP
	 * 
	 * @param otpVerifyDto
	 * @return
	 */
	Boolean verifyOtp(WarpReqDTO<OtpVerifyDTO> warpReqDTO);

}
