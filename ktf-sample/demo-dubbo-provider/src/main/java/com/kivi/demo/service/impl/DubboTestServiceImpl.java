package com.kivi.demo.service.impl;

import org.apache.dubbo.config.annotation.DubboService;

import com.alibaba.fastjson2.JSON;
import com.kivi.demo.dto.DemoReqDto;
import com.kivi.demo.dto.DemoRspDto;
import com.kivi.demo.service.DubboTestService;
import com.kivi.framework.component.KtfKit;
import com.kivi.framework.util.kit.StrKit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DubboService(version = "1.0.0")
public class DubboTestServiceImpl implements DubboTestService {

	@Override
	public DemoRspDto<String> execute(DemoReqDto<Integer> reqDto) {
		DemoRspDto<String> result = new DemoRspDto<String>();
		log.info("收到dubbo调用消息：{}", JSON.toJSONString(reqDto));

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result.setRspCode("S0000000");
		result.setSeqId(reqDto.getSeqId());
		result.setVersion(reqDto.getVersion());

		result.setBody(StrKit.join("-", "dubbo", KtfKit.me().getAppcationName(), KtfKit.me().getServerPort(),
				reqDto.getBody(), "count", reqDto.getBody() + 1));
		log.info("回复dubbo调用消息：{}", JSON.toJSONString(result));

		return result;
	}

}
