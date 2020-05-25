package com.kivi.framework.web.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.kivi.framework.component.KtfKit;

import lombok.extern.slf4j.Slf4j;

@WebListener
@Slf4j
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {

	@Override
	public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
		log.trace("--attributeAdded--");
		log.trace("key----:{}", httpSessionBindingEvent.getName());
		log.trace("value---:{}", httpSessionBindingEvent.getValue());

	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
		log.trace("--attributeRemoved--");
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
		log.trace("--attributeReplaced--");
	}

	@Override
	public void sessionCreated(HttpSessionEvent event) {

		HttpSession session = event.getSession();
		log.trace("---sessionCreated----，sessionId:{}", session.getId());
		KtfKit.me().sessionCreated(session.getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) throws ClassCastException {

		HttpSession session = event.getSession();
		log.trace("---sessionDestroyed----，sessionId:{}", session.getId());
		KtfKit.me().sessionDestroyed(session.getId());
	}

}
