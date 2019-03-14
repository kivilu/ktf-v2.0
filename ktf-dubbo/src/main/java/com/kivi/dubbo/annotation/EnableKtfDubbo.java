package com.kivi.dubbo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@Inherited
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.TYPE } )
@EnableDubbo( scanBasePackages = { "com.kivi", "${dubbo.scan-base-packages}" } )
public @interface EnableKtfDubbo {

}
