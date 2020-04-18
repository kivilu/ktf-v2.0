package com.kivi.framework.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.annotation.Configuration;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.kivi.framework.properties.IKtfProperties;
import com.kivi.framework.properties.KtfApolloProperties;

import lombok.extern.slf4j.Slf4j;

@ConditionalOnProperty(
                        prefix = KtfApolloProperties.PREFIX,
                        name = "enable-apollo",
                        havingValue = "true",
                        matchIfMissing = false )
@EnableApolloConfig
@Configuration
// @Component
@Slf4j
public class KtfApolloConfiguration {

    @Autowired
    private RefreshScope     refreshScope;

    @Autowired
    private IKtfProperties[] ktfConfigs;

    @ApolloConfigChangeListener
    public void onChange( ConfigChangeEvent changeEvent ) {
        for (String changedKey : changeEvent.changedKeys()) {
            for (IKtfProperties ktfConfig : ktfConfigs) {
                if (changedKey.startsWith(ktfConfig.prefix())) {
                    log.info("Properties[{}], before refresh {}", ktfConfig.prefix(), ktfConfig.toString());
                    refreshScope.refresh(ktfConfig.beanName());
                    log.info("Properties[{}], after refresh {}", ktfConfig.prefix(), ktfConfig.toString());
                    break;
                }
            }

        }

    }

}
