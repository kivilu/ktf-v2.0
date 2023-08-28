package com.kivi.demo.service.impl;

import org.apache.dubbo.config.annotation.DubboService;

import com.alibaba.fastjson2.JSON;
import com.kivi.demo.dto.DemoReqDto;
import com.kivi.demo.dto.DemoRspDto;
import com.kivi.demo.service.DubboBroadcastTestService;
import com.kivi.framework.component.KtfKit;
import com.kivi.framework.util.kit.StrKit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DubboService(version = "1.0.0", cluster = "broadcast")
public class DubboBroadcastTestServiceImpl implements DubboBroadcastTestService {

	@Override
	public DemoRspDto<String> broadcast(DemoReqDto<Integer> reqDto) {
		DemoRspDto<String> result = new DemoRspDto<String>();
		log.info("收到广播消息：{}", JSON.toJSONString(reqDto));

		result.setRspCode("S0000000");
		result.setSeqId(reqDto.getSeqId());
		result.setVersion(reqDto.getVersion());

		result.setBody(StrKit.join("-", "broadcast", KtfKit.me().getAppcationName(), KtfKit.me().getServerPort(),
				reqDto.getBody()));
		log.info("回复广播消息：{}", JSON.toJSONString(result));

		return result;
	}

}
