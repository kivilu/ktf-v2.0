package com.kivi.demo.service;

import com.kivi.demo.dto.DemoReqDto;
import com.kivi.demo.dto.DemoRspDto;

public interface DubboTestService {

	DemoRspDto<String> execute(DemoReqDto<Integer> reqDto);
}
