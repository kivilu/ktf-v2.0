package com.kivi.framework.task;

public interface IKtfThreadPoolCallback {

    /**
     * 线程池Reject以后的回调
     * 
     * @param dto
     */
    void rejectCallback( Object dto );
}
