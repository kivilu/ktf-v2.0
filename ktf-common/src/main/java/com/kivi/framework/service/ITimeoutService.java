package com.kivi.framework.service;

public interface ITimeoutService {
    /**
     * 超时通知
     * 
     * @param msgId
     *            消息唯一ID
     */
    void onTimeout( Long msgId );
}
