package com.kivi.sms.client;

import java.util.concurrent.ConcurrentHashMap;

import com.kivi.sms.client.aliyun.AliyunSmsClientImpl;
import com.kivi.sms.domain.SmsReportDO;
import com.kivi.sms.enums.SmsClientType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmsClientManager {
    private static ConcurrentHashMap<String, SmsClient> smsClients = new ConcurrentHashMap<>();

    private SmsClientManager() {

    }

    public static SmsClientManager me() {

        return SmsClientManagerSingletonHolder.instance;
    }

    public SmsClient getSmsClient( SmsClientType type, SmsCallback<SmsReportDO> callback ) {
        return getSmsClient(type.name(), callback);
    }

    public SmsClient getSmsClient( String type, SmsCallback<SmsReportDO> callback ) {
        SmsClient smsClient = smsClients.get(type);

        if (smsClient == null) {
            smsClient = new AliyunSmsClientImpl(callback);
            smsClients.putIfAbsent(type, smsClient);
        }

        log.trace("获取{}渠道的smsClient.", type);
        return smsClient;
    }

    private static class SmsClientManagerSingletonHolder {

        private static SmsClientManager instance = new SmsClientManager();
    }
}
