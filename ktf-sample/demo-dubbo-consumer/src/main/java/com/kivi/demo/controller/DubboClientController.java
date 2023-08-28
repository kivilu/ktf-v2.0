package com.kivi.demo.controller;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSON;
import com.kivi.demo.dto.DemoReqDto;
import com.kivi.demo.dto.DemoRspDto;
import com.kivi.demo.service.DubboBroadcastTestService;
import com.kivi.demo.service.DubboForkingTestService;
import com.kivi.demo.service.DubboTestService;
import com.kivi.framework.util.kit.DateTimeKit;
import com.kivi.framework.util.kit.StrKit;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = { "dubbo" }, value = "Dubbo测试接口")
@RestController
@RequestMapping("/dubbo")
public class DubboClientController {

	private static AtomicInteger		count	= new AtomicInteger();

	@DubboReference(version = "1.0.0", check = false)
	private DubboTestService			dubboTestService;

	@DubboReference(version = "1.0.0", check = false)
	private DubboBroadcastTestService	dubboBroadcastTestService;

	@DubboReference(version = "1.0.0", check = false)
	private DubboForkingTestService		dubboForkingTestService;

	@PostConstruct
	private void init() {
		log.info("**********应用启动************");
	}

	@ApiOperation(value = "Dubbo调用", nickname = "execute")
	@GetMapping("/execute")
	public String execute() {

		DemoReqDto<Integer> reqDto = new DemoReqDto<Integer>();

		reqDto.setSeqId(StrKit.join("", DateTimeKit.currentUnix(), String.format("%08d", count.incrementAndGet())));
		reqDto.setVersion("1.0");
		reqDto.setBody(count.get());
		DemoRspDto<String> rsp = dubboTestService.execute(reqDto);

		return StrKit.join("：", "Dubbo broadcast测试", JSON.toJSONString(rsp));
	}

	@ApiOperation(value = "Dubbo广播调用", nickname = "broadcast")
	@GetMapping("/broadcast")
	public String broadcast() {

		DemoReqDto<Integer> reqDto = new DemoReqDto<Integer>();

		reqDto.setSeqId(StrKit.join("", DateTimeKit.currentUnix(), String.format("%08d", count.incrementAndGet())));
		reqDto.setVersion("1.0");
		reqDto.setBody(count.get());
		DemoRspDto<String> rsp = dubboBroadcastTestService.broadcast(reqDto);

		return StrKit.join("：", "Dubbo broadcast测试", JSON.toJSONString(rsp));
	}

	@ApiOperation(value = "Dubbo forking调用", nickname = "cluster")
	@GetMapping("/cluster")
	public String cluster() {

		DemoReqDto<Integer> reqDto = new DemoReqDto<Integer>();

		reqDto.setSeqId(StrKit.join("", DateTimeKit.currentUnix(), String.format("%08d", count.incrementAndGet())));
		reqDto.setVersion("1.0");
		reqDto.setBody(count.get());
		DemoRspDto<String> rsp = dubboForkingTestService.forking(reqDto);

		return StrKit.join("：", "Dubbo cluster测试", JSON.toJSONString(rsp));
	}
}
