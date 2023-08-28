package com.kivi.sms.configure;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

import com.kivi.framework.constant.KtfFramework;

@MapperScan( basePackages = { KtfFramework.MapperScan.KTF_MAPPER_SCAN, "com.ins.sms.**.persist" } )
@Configuration
public class SmsConfigure {

}
