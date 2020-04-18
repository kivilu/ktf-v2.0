package com.kivi.framework.web.intercepter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kivi.framework.properties.KtfSwaggerProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * Swagger 拦截器
 * 
 * @description 用于禁用swagger，当swagger.disabled为true时重定向至${ktf.swagger.redirect-uri}
 * @author xueqi
 *
 */
@Slf4j
@Component
public class SwaggerInterceptor implements HandlerInterceptor {
	@Autowired
	private KtfSwaggerProperties ktfSwaggerProperties;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (ktfSwaggerProperties.getEnabled())
			return Boolean.TRUE;
		String uri = ktfSwaggerProperties.getRedirectUri();
		if (uri == null || uri.trim().length() == 0)
			uri = "/";
		try {
			response.sendRedirect(uri);
		} catch (IOException e) {
			log.error(String.format("Redirect to '%s' for swagger throw an exception : %s", uri, e.getMessage()), e);
		}
		return Boolean.FALSE;
	}

}
