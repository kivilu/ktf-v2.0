package com.kivi.framework.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kivi.framework.annotation.KtfTrace;
import com.vip.vjtools.vjkit.time.ClockUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志记录
 *
 */
@Aspect
@Component
@Slf4j
public class KtfTraceAspect {

    @Pointcut(value = "@annotation(com.kivi.framework.annotation.KtfTrace)")
    public void aopMethed() {}

    @Before(value = "aopMethed()")
    public void beforMethedProceed(JoinPoint joinPoint) throws Throwable {

    }

    @Around("aopMethed()")
    public Object methedProceed(ProceedingJoinPoint point) throws Throwable {

        // 获取方法对象
        long start = System.currentTimeMillis();

        Signature sig = point.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature)sig;
        Object target = point.getTarget();
        String className = target.getClass().getName();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        String methodName = currentMethod.getName();

        // 获取注解
        KtfTrace annotation = currentMethod.getAnnotation(KtfTrace.class);

        LogLevel logLevel = annotation.logLevel();

        // 获取参数
        Object[] params = point.getArgs();

        if (isLog(logLevel)) {
            log(logLevel, "{}{}：{}.{} 参数：{}", annotation.writeDb() ? "!!" : "", annotation.value(), className,
                methodName,
                JSON.toJSONString(params, SerializerFeature.WriteClassName, SerializerFeature.PrettyFormat));
        }

        // 执行业务
        Object result = point.proceed();

        if (isLog(logLevel)) {
            log(logLevel, "{}{}：{}.{}返回值：{}\n执行耗时：{}ms", annotation.writeDb() ? "!!" : "", annotation.value(),
                className, methodName,
                JSON.toJSONString(result, SerializerFeature.WriteClassName, SerializerFeature.PrettyFormat),
                ClockUtil.elapsedTime(start));
        }

        return result;
    }

    boolean isLog(LogLevel logLevel) {
        boolean ret = false;
        switch (logLevel) {
            case DEBUG:
                ret = log.isDebugEnabled() || logLevel.ordinal() >= LogLevel.DEBUG.ordinal();
                break;
            case ERROR:
                ret = log.isErrorEnabled() || logLevel.ordinal() >= LogLevel.ERROR.ordinal();
                break;
            case INFO:
                ret = log.isInfoEnabled() || logLevel.ordinal() >= LogLevel.INFO.ordinal();
                break;
            case TRACE:
                ret = log.isTraceEnabled() || logLevel.ordinal() >= LogLevel.TRACE.ordinal();
                break;
            case WARN:
                ret = log.isWarnEnabled() || logLevel.ordinal() >= LogLevel.WARN.ordinal();
                break;
            default:
                break;
        }

        return ret;
    }

    void log(LogLevel logLevel, String format, Object... objects) {
        switch (logLevel) {
            case DEBUG:
                log.debug(format, objects);
                break;
            case ERROR:
                log.error(format, objects);
                break;
            case INFO:
                log.info(format, objects);
                break;
            case TRACE:
                log.trace(format, objects);
                break;
            case WARN:
                log.warn(format, objects);
                break;
            default:
                break;
        }
    }

}
