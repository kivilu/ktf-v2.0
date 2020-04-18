package com.kivi.framework.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration( KtfApolloProperties.BEAN_NAME )
@ConfigurationProperties( prefix = KtfApolloProperties.PREFIX )
public class KtfApolloProperties implements IKtfProperties{
	
	public static final String BEAN_NAME               = "ktfApolloProperties";
    public static final String PREFIX                  = "ktf.apollo";
    
    private Boolean            enableApollo            = true;

	@Override
	public String beanName() {
		return BEAN_NAME;
	}

	@Override
	public String prefix() {
		return PREFIX;
	}

}
