package com.kivi.framework.component;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Spring的ApplicationContext的持有者,可以用静态方法的方式获取spring容器中的bean
 *
 */
@Component
@Order( value = Ordered.HIGHEST_PRECEDENCE )
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        assertApplicationContext();
        return applicationContext;
    }

    @SuppressWarnings( "unchecked" )
    public static <T> T getBean( String beanName ) {
        assertApplicationContext();
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean( Class<T> requiredType ) {
        assertApplicationContext();
        return applicationContext.getBean(requiredType);
    }

    public static <T> T getBeanNoAssert( Class<T> requiredType ) {
        if (applicationContext == null)
            return null;
        return applicationContext.getBean(requiredType);
    }

    public static boolean containsBean( String beanName ) {
        assertApplicationContext();
        return applicationContext.containsBean(beanName);
    }

    private static void assertApplicationContext() {
        if (SpringContextHolder.applicationContext == null) {
            throw new RuntimeException("applicaitonContext属性为null,请检查是否注入了SpringContextHolder!");
        }
    }

}
