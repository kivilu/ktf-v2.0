package com.kivi.demo.service;

import com.kivi.demo.dto.DemoReqDto;
import com.kivi.demo.dto.DemoRspDto;

public interface DubboForkingTestService {

	/**
	 * 集群测试
	 * @param reqDto
	 * @return
	 */
	DemoRspDto<String> forking( DemoReqDto<Integer> reqDto);
}
