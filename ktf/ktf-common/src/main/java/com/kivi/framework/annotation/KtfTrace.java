package com.kivi.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.logging.LogLevel;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface KtfTrace {
    /**
     * 业务的名称,例如:"修改菜单"
     */
    String value() default "";

    /**
     * 日志是否保存到数据库，默认：false
     * 
     * @return
     */
    boolean writeDb() default false;

    /**
     * TRACE日志的级别，默认：TRACE
     * 
     * @return
     */
    LogLevel logLevel() default LogLevel.DEBUG;

}
