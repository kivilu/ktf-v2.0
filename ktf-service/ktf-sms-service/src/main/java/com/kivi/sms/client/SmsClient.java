package com.kivi.sms.client;

import com.kivi.sms.domain.SmsReqDO;
import com.kivi.sms.domain.SmsRspDO;

public interface SmsClient {

    /**
     * 发送短信
     * 
     * @param bizSeqId
     *            业务流水号
     * @param reqDto
     *            发送内容
     * @return
     */
    SmsRspDO sendSms( String bizSeqId, SmsReqDO reqDto );

    /**
     * 关闭客户端
     */
    void shutdown();

}
