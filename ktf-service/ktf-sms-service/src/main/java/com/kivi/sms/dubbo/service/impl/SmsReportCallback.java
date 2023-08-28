package com.kivi.sms.dubbo.service.impl;

import com.kivi.sms.client.SmsCallback;
import com.kivi.sms.domain.SmsReportDO;

public class SmsReportCallback implements SmsCallback<SmsReportDO> {

    @Override
    public void onNotify( SmsReportDO notify ) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onFailure( Throwable cause ) {
        // TODO Auto-generated method stub
        SmsCallback.super.onFailure(cause);
    }

}
