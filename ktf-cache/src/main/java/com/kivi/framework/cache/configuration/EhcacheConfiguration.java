package com.kivi.framework.cache.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty( name = { "spring.cache.type" }, havingValue = "ehcache", matchIfMissing = false )
// @ImportResource( "classpath*:ehcache*.xml" )
@EnableCaching
public class EhcacheConfiguration extends CachingConfigurerSupport {
    public EhcacheConfiguration() {

    }
}
