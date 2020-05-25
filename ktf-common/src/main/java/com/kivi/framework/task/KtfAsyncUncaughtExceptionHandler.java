package com.kivi.framework.task;

import java.lang.reflect.Method;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KtfAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {


    @Override
    public void handleUncaughtException( Throwable ex, Method method, Object... params ) {
        log.info("-------------》》》捕获线程异常信息");
        log.info("Exception message: {}", ex.getMessage(), ex);
        log.info("Method name: {} ", method.getName());
        for (Object param : params) {
            log.info("Parameter value: {}", param);
        }
    }

}
