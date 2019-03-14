package com.kivi.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.kivi.framework.constant.KiviFramework;

@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
@PropertySource( value = { KiviFramework.FRAMWORK_PLACEHOLDER } )
@ComponentScan(
                basePackages = { KiviFramework.BasePackages.KTF_COMPONENT_SCAN, KiviFramework.BasePackages.COMPONENT_SCAN, "com.deepoove.swagger.dubbo.config", "com.deepoove.swagger.dubbo.web" } )
public @interface EnableKiviFramework {

}
