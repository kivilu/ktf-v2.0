package com.kivi.sms.dubbo.mock;

import com.kivi.framework.component.KtfKit;
import com.kivi.framework.dto.warapper.WarpReqDTO;
import com.kivi.framework.exception.KtfMockException;
import com.kivi.sms.dto.OtpVerifyDTO;
import com.kivi.sms.dto.SmsOtpReqDTO;
import com.kivi.sms.dto.SmsReqDTO;
import com.kivi.sms.dubbo.service.SmsService;

public class SmsServiceMock implements SmsService {

	@Override
	public Boolean sendOtp(WarpReqDTO<SmsOtpReqDTO> otpReqDto) {
		if (KtfKit.me().isActiveDev()) {
			return true;
		}
		throw new KtfMockException();
	}

	@Override
	public Boolean verifyOtp(WarpReqDTO<OtpVerifyDTO> otpVerifyDto) {
		if (KtfKit.me().isActiveDev()) {
			return true;
		}
		throw new KtfMockException();
	}

	@Override
	public Boolean sendMessage(WarpReqDTO<SmsReqDTO> otpReqDto) {
		if (KtfKit.me().isActiveDev()) {
			return true;
		}
		throw new KtfMockException();
	}

}
