package com.kivi.framework.db.annotation;

import java.lang.annotation.*;

/**
 * 
 * 多数据源标识
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface DataSource {

	String name() default "";
}
