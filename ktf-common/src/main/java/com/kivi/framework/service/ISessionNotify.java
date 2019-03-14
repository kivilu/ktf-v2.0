package com.kivi.framework.service;

public interface ISessionNotify {

    /**
     * session销毁通知
     * 
     * @param id
     */
    void onDestroyed( String id );
}
