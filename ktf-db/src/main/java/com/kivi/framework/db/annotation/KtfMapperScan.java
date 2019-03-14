package com.kivi.framework.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mybatis.spring.annotation.MapperScan;




@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@MapperScan(basePackages={"com.kivi.**.persist"})
public @interface KtfMapperScan {

}
