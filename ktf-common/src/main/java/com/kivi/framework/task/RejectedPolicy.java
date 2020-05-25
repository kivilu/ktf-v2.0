package com.kivi.framework.task;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RejectedPolicy implements RejectedExecutionHandler {

    private static final Logger          log = LoggerFactory.getLogger(RejectedPolicy.class);

    private final IKtfThreadPoolCallback ktfCallback;

    public RejectedPolicy( IKtfThreadPoolCallback ktfCallback ) {
        this.ktfCallback = ktfCallback;
    }

    @Override
    public void rejectedExecution( Runnable r, ThreadPoolExecutor executor ) {
        log.info("线程池已满，任务[{}]被拒绝执行", r.toString());
        if (r instanceof KtfRunnable) {
            KtfRunnable<?,?> kr = (KtfRunnable<?,?>) r;
            if (ktfCallback != null) {
                log.info("将任务消息[{}]返回上层，继续处理", kr.getDto().getDeferred().toString());
                ktfCallback.rejectCallback(kr.getDto());
            }
        }
    }

}
