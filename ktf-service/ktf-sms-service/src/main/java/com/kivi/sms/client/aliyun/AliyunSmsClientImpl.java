package com.kivi.sms.client.aliyun;

import java.text.ParseException;

import com.alibaba.fastjson.JSON;
import com.alicom.mns.tools.DefaultAlicomMessagePuller;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.component.SpringContextHolder;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.StrKit;
import com.kivi.sms.client.SmsCallback;
import com.kivi.sms.client.SmsClient;
import com.kivi.sms.domain.SmsReportDO;
import com.kivi.sms.domain.SmsReqDO;
import com.kivi.sms.domain.SmsRspDO;
import com.kivi.sms.properties.KtfSmsProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AliyunSmsClientImpl implements SmsClient {

	// 产品名称:云通信短信API产品,开发者无需替换
	private static final String					product	= "Dysmsapi";
	// 产品域名,开发者无需替换
	private static final String					domain	= "dysmsapi.aliyuncs.com";

	private final KtfSmsProperties				ktfSmsProperties;

	private final AliyunSmsProperties			aliSmsProperties;

	private final IAcsClient					acsClient;

	private final DefaultAlicomMessagePuller	alicomMessagePuller;

	public AliyunSmsClientImpl(SmsCallback<SmsReportDO> callback) {
		this.ktfSmsProperties		= SpringContextHolder.getBean(KtfSmsProperties.BEAN_NAME);
		this.aliSmsProperties		= SpringContextHolder.getBean(AliyunSmsProperties.BEAN_NAME);
		this.acsClient				= this.createIAcsClient();
		this.alicomMessagePuller	= createAlicomMessagePuller(callback);

	}

	@KtfTrace("发送短信")
	@Override
	public SmsRspDO sendSms(String bizSeqId, SmsReqDO reqDto) {
		SmsRspDO		result	= new SmsRspDO();
		// 组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest	request	= new SendSmsRequest();
		request.setSysConnectTimeout(ktfSmsProperties.getConnectTimeout());
		request.setSysReadTimeout(ktfSmsProperties.getReadTimeout());

		// 必填:待发送手机号
		request.setPhoneNumbers(StrKit.join(",", reqDto.getPhoneNumbers()));
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName(aliSmsProperties.getSignNameUTF8());
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(aliSmsProperties.getTemplateCode(reqDto.getSmsBizType()));
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		request.setTemplateParam(JSON.toJSONString(reqDto.getTemplateParam()));

		// 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");

		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		request.setOutId(bizSeqId);

		// hint 此处可能会抛出异常，注意catch
		try {
			SendSmsResponse response = acsClient.getAcsResponse(request);
			if (!"OK".equals(response.getCode())) {
				log.error("阿里云通道短信发送失败，电话：{}，短信：{}，业务流程号：{}", reqDto.getPhoneNumbers(), reqDto.getTemplateParam(),
						bizSeqId);
				throw new KtfException(response.getMessage());
			}

			result.setCode(KtfError.SUCCESS);
			result.setMessage(response.getMessage());
			result.setReceiptID(response.getBizId());
			result.setRequestId(response.getRequestId());
		} catch (ClientException e) {
			throw new KtfException("阿里云通道短信发送异常", e);
		}

		return result;
	}

	private IAcsClient createIAcsClient() {
		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile(aliSmsProperties.getRegionId(),
				aliSmsProperties.getAccessKeyId(), aliSmsProperties.getAccessKeySecret());
		DefaultProfile.addEndpoint(aliSmsProperties.getRegionId(), product, domain);
		// DefaultProfile.addEndpoint(aliSmsProperties.getRegionId(),
		// aliSmsProperties.getRegionId(), product, domain);

		return new DefaultAcsClient(profile);
	}

	private DefaultAlicomMessagePuller createAlicomMessagePuller(SmsCallback<SmsReportDO> callback) {
		DefaultAlicomMessagePuller puller = new DefaultAlicomMessagePuller();

		// 初始化短信回执拉取对象
		// 设置异步线程池大小及任务队列的大小，还有无数据线程休眠时间
		puller.setConsumeMinThreadSize(aliSmsProperties.getPullerMinThreadSize());
		puller.setConsumeMaxThreadSize(aliSmsProperties.getPullerMaxThreadSize());
		puller.setThreadQueueSize(aliSmsProperties.getPullerQueueSize());
		puller.setPullMsgThreadSize(aliSmsProperties.getPullerMsgThreadSize());
		puller.setSleepMillsWhenNoData(aliSmsProperties.getPullerIdleTime());
		// 和服务端联调问题时开启,平时无需开启，消耗性能
		puller.openDebugLog(aliSmsProperties.getOpenDebugLog());

		/*
		 * 1:短信回执：SmsReport， 2:短息上行：SmsUp 3:语音呼叫：VoiceReport 4:流量直冲：FlowReport
		 */
		String messageType = "SmsReport";// 此处应该替换成相应产品的消息类型
		try {
			puller.startReceiveMsg(aliSmsProperties.getAccessKeyId(), aliSmsProperties.getAccessKeySecret(),
					messageType, aliSmsProperties.getSmsReportQueue(), new AliyunSmsMessageListener(callback));
		} catch (ClientException | ParseException e) {
			throw new KtfException("阿里云短信通短信报告接收始化异常", e);
		}

		return puller;
	}

	@Override
	public void shutdown() {
		alicomMessagePuller.stop();
	}

}
