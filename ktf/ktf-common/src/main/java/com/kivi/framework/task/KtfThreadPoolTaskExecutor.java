package com.kivi.framework.task;

import java.util.concurrent.LinkedTransferQueue;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class KtfThreadPoolTaskExecutor extends ThreadPoolTaskExecutor implements IKtfThreadPoolCallback {

    /**
     * 
     */
    private static final long           serialVersionUID = 1L;

    private LinkedTransferQueue<Object> rejectQueue      = new LinkedTransferQueue<>();

    public KtfThreadPoolTaskExecutor() {
        super();

    }

    @Override
    public void rejectCallback( Object dto ) {
        rejectQueue.put(dto);

    }

    public Object getRejectTaskDto() {

        return rejectQueue.poll();
    }

}
