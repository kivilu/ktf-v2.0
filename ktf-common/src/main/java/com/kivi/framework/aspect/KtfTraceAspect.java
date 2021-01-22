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

        // 获取参数
        Object[] params = point.getArgs();

        if (log.isDebugEnabled()) {
            log.debug("{}{}：{}.{} 参数：{}", annotation.writeDb() ? "!!" : "", annotation.value(), className, methodName,
                JSON.toJSONString(params, SerializerFeature.WriteClassName, SerializerFeature.PrettyFormat));
        }

        // 执行业务
        Object result = point.proceed();

        if (log.isDebugEnabled()) {
            log.debug("{}{}：{}.{}返回值：{}\n执行耗时：{}ms", annotation.writeDb() ? "!!" : "", annotation.value(), className,
                methodName, JSON.toJSONString(result, SerializerFeature.WriteClassName, SerializerFeature.PrettyFormat),
                ClockUtil.elapsedTime(start));
        }

        return result;
    }

}
