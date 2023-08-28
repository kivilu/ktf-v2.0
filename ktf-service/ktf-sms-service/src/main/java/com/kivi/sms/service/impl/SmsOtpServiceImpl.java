package com.kivi.sms.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kivi.framework.cache.CacheKit;
import com.kivi.sms.constant.KtfSmsCache;
import com.kivi.sms.dto.OtpVerifyDTO;
import com.kivi.sms.properties.KtfSmsProperties;
import com.kivi.sms.service.SmsOtpService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SmsOtpServiceImpl implements SmsOtpService {

	@Autowired
	private KtfSmsProperties ktfSmsProperties;

	@Override
	public String smsOtp(String phoneNumber) {
		String	otp	= RandomStringUtils.randomNumeric(ktfSmsProperties.getSmsCodeLength());
		Long	now	= System.currentTimeMillis();
		log.trace("生成短信验证码：{}，手机号：{}，时间戳：{}", otp, phoneNumber, now);

		OtpVerifyDTO otpVerifyDTO = new OtpVerifyDTO();
		otpVerifyDTO.setPhoneNumber(phoneNumber);
		otpVerifyDTO.setOtp(otp);
		otpVerifyDTO.setCreateTime(now);

		CacheKit.me().put(KtfSmsCache.OTP_CACHE, otp, otpVerifyDTO);

		return otp;
	}

	@Override
	public Boolean smsOtp(String phoneNumber, String otp) {
		Boolean			result	= false;
		OtpVerifyDTO	otpReq	= CacheKit.me().get(KtfSmsCache.OTP_CACHE, otp);
		if (otpReq != null) {
			log.trace("验证码{}的原手机号：{}，生成时间：{}", otp, otpReq.getPhoneNumber(), otpReq.getCreateTime());

			result = Boolean.logicalAnd(
					System.currentTimeMillis() - otpReq.getCreateTime() < ktfSmsProperties.getSmsCodeExpire(),
					otpReq.getPhoneNumber().equals(phoneNumber));
		} else {
			log.error("缓存中找不到短信验证码记录，校验手机号｛｝，验证码：｛｝", phoneNumber, otp);
		}

		return result;
	}

}
