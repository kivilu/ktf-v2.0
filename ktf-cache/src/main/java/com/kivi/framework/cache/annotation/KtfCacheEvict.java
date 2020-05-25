package com.kivi.framework.cache.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
public @interface KtfCacheEvict {

	/**
	 * 需要清除的大类 例如SysDic所有缓存
	 *
	 * @return
	 */
	String[] cacheNames() default "";

	/**
	 * 需要清除的具体的额类型
	 *
	 * @return
	 */
	String[] key() default {};

}
