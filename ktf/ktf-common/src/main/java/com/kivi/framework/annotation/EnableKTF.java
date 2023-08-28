package com.kivi.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import com.kivi.framework.constant.KtfFramework;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableAspectJAutoProxy
@EnableAsync
@ComponentScan(
		basePackages = { KtfFramework.BasePackages.KTF_COMPONENT_SCAN, KtfFramework.BasePackages.COMPONENT_SCAN })
public @interface EnableKTF {

}
