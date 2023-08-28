package com.kivi.demo.service;

import com.kivi.demo.dto.DemoReqDto;
import com.kivi.demo.dto.DemoRspDto;

public interface DubboBroadcastTestService {

	/**
	 * 广播测试
	 * @param reqDto
	 * @return
	 */
	DemoRspDto<String> broadcast( DemoReqDto<Integer> reqDto);
	
}
