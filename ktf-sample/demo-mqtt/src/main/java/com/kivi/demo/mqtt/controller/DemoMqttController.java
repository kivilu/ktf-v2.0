package com.kivi.demo.mqtt.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kivi.framework.dto.KtfBaseReq;
import com.kivi.framework.model.ResultMap;
import com.kivi.mqtt.properties.KtfMqttProperties;
import com.kivi.mqtt.utils.MqttUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"mqtt"}, value = "Mqtt测试接口")
@RestController
@RequestMapping("/mqtt")
public class DemoMqttController {

    @ApiOperation(value = "发布消息", nickname = "pub")
    @PostMapping("/pub/{id}")
    public ResultMap pubMessage(@PathVariable("id") String id, @RequestBody KtfBaseReq<String> data) {

        MqttUtil.sendMessage(KtfMqttProperties.DEFAULT_PRODUCER_TOPIC.replace("+", id), data);

        return ResultMap.ok();
    }
}
