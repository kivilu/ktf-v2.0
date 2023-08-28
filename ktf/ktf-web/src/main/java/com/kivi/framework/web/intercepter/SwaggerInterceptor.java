package com.kivi.framework.web.intercepter;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Swagger 拦截器
 * 
 * @description 用于禁用swagger，当swagger.disabled为true时重定向至${ktf.swagger.redirect-uri}
 * @author xueqi
 *
 */

@Deprecated
//@Slf4j
//@Component
public class SwaggerInterceptor implements HandlerInterceptor {
	/*
	 * @Autowired private KtfSwaggerProperties ktfSwaggerProperties;
	 * 
	 * @Override public boolean preHandle(HttpServletRequest request,
	 * HttpServletResponse response, Object handler) throws Exception { if
	 * (ktfSwaggerProperties.getEnabled()) return Boolean.TRUE; String uri =
	 * ktfSwaggerProperties.getRedirectUri(); if (uri == null || uri.trim().length()
	 * == 0) uri = "/"; try { response.sendRedirect(uri); } catch (IOException e) {
	 * log.error(String.
	 * format("Redirect to '%s' for swagger throw an exception : %s", uri,
	 * e.getMessage()), e); } return Boolean.FALSE; }
	 */

}
