package com.kivi.framework.web.async;

import com.alibaba.fastjson.JSON;

public class KtfDeferredResult extends KtfAsyncResult<String> {

    public KtfDeferredResult( Long msgId ) {
        super(msgId);
    }

    public KtfDeferredResult( Long msgId, Long timeout ) {
        super(msgId, timeout);
    }

    @Override
    public boolean setResult( String result ) {
        return super.setResult(result);
    }

    public boolean setResultRawObject( Object result ) {
        String json = JSON.toJSONString(result);
        return super.setResult(json);
    }

}
