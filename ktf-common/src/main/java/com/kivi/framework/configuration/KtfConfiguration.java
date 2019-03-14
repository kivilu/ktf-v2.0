package com.kivi.framework.configuration;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import com.kivi.framework.properties.KtfTaskPoolProperties;
import com.kivi.framework.task.KtfAsyncUncaughtExceptionHandler;
import com.kivi.framework.task.KtfThreadPoolTaskExecutor;
import com.kivi.framework.task.RejectedPolicy;

@Configuration
@EnableAsync
public class KtfConfiguration implements AsyncConfigurer {

    @Autowired
    private KtfTaskPoolProperties config;

    @Bean( "ktfThreadPool" )
    @Override
    public Executor getAsyncExecutor() {
        return getPoolTaskExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new KtfAsyncUncaughtExceptionHandler();
    }

    public KtfThreadPoolTaskExecutor getPoolTaskExecutor() {
        KtfThreadPoolTaskExecutor executor = new KtfThreadPoolTaskExecutor();
        executor.setCorePoolSize(config.getCorePoolSize());
        executor.setMaxPoolSize(config.getMaxPoolSize());
        executor.setQueueCapacity(config.getQueueCapacity());
        executor.setWaitForTasksToCompleteOnShutdown(true);// 等待任务在关机时完成--表明等待所有线程执行完
        executor.setKeepAliveSeconds(config.getKeepAliveSeconds());
        executor.setAwaitTerminationSeconds(config.getAwaitTerminationSeconds());// 等待时间（默认为0，此时立即停止），并没等待xx秒后强制停止
        executor.setThreadNamePrefix("KtfExec-");
        executor.setRejectedExecutionHandler(new RejectedPolicy(executor));
        executor.initialize();
        return executor;
    }

}
