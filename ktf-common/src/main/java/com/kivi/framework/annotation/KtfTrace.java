package com.kivi.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.METHOD } )
public @interface KtfTrace {
    /**
     * 业务的名称,例如:"修改菜单"
     */
    String value() default "";

}
