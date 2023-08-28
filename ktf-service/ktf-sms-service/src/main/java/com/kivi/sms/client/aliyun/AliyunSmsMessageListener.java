package com.kivi.sms.client.aliyun;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.alicom.mns.tools.MessageListener;
import com.aliyun.mns.model.Message;
import com.kivi.sms.client.SmsCallback;
import com.kivi.sms.domain.SmsReportDO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AliyunSmsMessageListener implements MessageListener {

	@SuppressWarnings("unused")
	private final SmsCallback<SmsReportDO> callback;

	public AliyunSmsMessageListener(SmsCallback<SmsReportDO> callback) {
		this.callback = callback;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public boolean dealMessage(Message message) {

		if (log.isDebugEnabled())
			log.debug("收到短信回执，handle：{}，body：{}，id：{}，dequeue count：{} ", message.getReceiptHandle(),
					message.getMessageBodyAsString(), message.getMessageId(), message.getDequeueCount());

		try {
			Map<String, Object> contentMap = JSON.parseObject(message.getMessageBodyAsString(), HashMap.class);

			// TODO 这里开始编写您的业务代码

		} catch (JSONException e) {
			log.error("error_json_format:" + message.getMessageBodyAsString(), e);
			// 理论上不会出现格式错误的情况，所以遇见格式错误的消息，只能先delete,否则重新推送也会一直报错
			return true;
		} catch (Throwable e) {
			// 您自己的代码部分导致的异常，应该return false,这样消息不会被delete掉，而会根据策略进行重推
			return false;
		}

		// 消息处理成功，返回true, SDK将调用MNS的delete方法将消息从队列中删除掉
		return true;
	}

}
