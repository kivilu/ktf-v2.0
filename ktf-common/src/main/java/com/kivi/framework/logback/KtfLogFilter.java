package com.kivi.framework.logback;

import org.apache.commons.lang3.StringUtils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class KtfLogFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {
        String message = event.getFormattedMessage();
        if (message != null && StringUtils.startsWith(message, "!!")) {
            return FilterReply.ACCEPT;
        } else {
            return FilterReply.DENY;
        }
    }

}
