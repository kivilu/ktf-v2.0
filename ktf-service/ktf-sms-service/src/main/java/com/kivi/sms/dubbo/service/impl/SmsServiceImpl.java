package com.kivi.sms.dubbo.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.dto.warapper.WarpReqDTO;
import com.kivi.framework.properties.KtfCommonProperties;
import com.kivi.sms.client.SmsClient;
import com.kivi.sms.client.SmsClientManager;
import com.kivi.sms.domain.SmsReqDO;
import com.kivi.sms.dto.OtpVerifyDTO;
import com.kivi.sms.dto.SmsOtpReqDTO;
import com.kivi.sms.dto.SmsReqDTO;
import com.kivi.sms.dto.SmsTemplateParam;
import com.kivi.sms.dubbo.service.SmsService;
import com.kivi.sms.enums.SmsBizType;
import com.kivi.sms.enums.SmsClientType;
import com.kivi.sms.properties.KtfSmsProperties;
import com.kivi.sms.service.SmsOtpService;

import lombok.extern.slf4j.Slf4j;

@Service(version = KtfSmsProperties.DUBBO_VERSION, timeout = 10000, validation = "true")
@Slf4j
public class SmsServiceImpl implements SmsService {

	@Autowired
	private SmsOtpService		smsOtpService;

	@Autowired
	private KtfCommonProperties	ktfProperties;

	@KtfTrace("发送短信验证码")
	@Override
	public Boolean sendOtp(WarpReqDTO<SmsOtpReqDTO> otpReqDto) {
		final Long			hawkSeqId	= otpReqDto.getTranUniqueId();
		final SmsOtpReqDTO	otpReq		= otpReqDto.getReqObject();

		log.info("【{}】发送短信验证码，接收号码：{}", hawkSeqId, otpReq.getPhoneNumber());

		SmsReqDO smsReq = new SmsReqDO();
		smsReq.phoneNumber(otpReq.getPhoneNumber());
		smsReq.setSmsBizType(otpReq.getSmsBizType());

		String smsCode = smsOtpService.smsOtp(otpReq.getPhoneNumber());

		log.trace("【{}】短信发送的验证码：{}", hawkSeqId, smsCode);
		if (ktfProperties.getEnableTest()) {
			log.info("【{}】-----------开启了测试开关，不直接发送短信。---------", hawkSeqId);
			return true;
		}

		smsReq.setTemplateParam(SmsTemplateParam.builder().code(smsCode).build().map());

		SmsClient smsClient = SmsClientManager.me().getSmsClient(SmsClientType.Aliyun, new SmsReportCallback());
		smsClient.sendSms(otpReqDto.getTranSeqId(), smsReq);

		return true;
	}

	@KtfTrace("验证短信验证码")
	@Override
	public Boolean verifyOtp(WarpReqDTO<OtpVerifyDTO> otpDto) {
		OtpVerifyDTO dto = otpDto.getReqObject();
		return smsOtpService.smsOtp(dto.getPhoneNumber(), dto.getOtp());
	}

	@KtfTrace("发送短信")
	@Override
	public Boolean sendMessage(WarpReqDTO<SmsReqDTO> otpReqDto) {
		final Long		hawkSeqId	= otpReqDto.getTranUniqueId();
		final SmsReqDTO	smsReqDTO	= otpReqDto.getReqObject();

		log.info("【{}】发送短信，接收号码：{}", hawkSeqId, smsReqDTO.getPhoneNumber());

		SmsReqDO smsReq = new SmsReqDO();
		smsReq.phoneNumber(smsReqDTO.getPhoneNumber());
		smsReq.setSmsBizType(smsReqDTO.getSmsBizType());

		log.trace("【{}】短信内容：{}", hawkSeqId, smsReqDTO);
		if (ktfProperties.getEnableTest()) {
			log.info("【{}】-----------开启了测试开关，不直接发送短信。---------", hawkSeqId);
			return true;
		}

		String	content	= null;
		String	days	= null;
		if (SmsBizType.unlockOTP.compareTo(smsReqDTO.getSmsBizType()) == 0) {
			days = StringUtils.remove(smsReqDTO.getContent(), "天");
		} else
			content = smsReqDTO.getContent();

		smsReq.setTemplateParam(SmsTemplateParam.builder().code(smsReqDTO.getOtpCode())
				.startTime(smsReqDTO.getStartTime()).endTime(smsReqDTO.getEndTime()).mtname(smsReqDTO.getMtname())
				.content(content).days(days).build().map());

		SmsClient smsClient = SmsClientManager.me().getSmsClient(SmsClientType.Aliyun, new SmsReportCallback());
		smsClient.sendSms(otpReqDto.getTranSeqId(), smsReq);

		return true;
	}

}
