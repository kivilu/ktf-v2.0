package com.kivi.framework.web.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CommonIntercepter implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

//		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));// 支持跨域请求
//		response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
//		response.setHeader("Access-Control-Allow-Credentials", "true");// 是否支持cookie跨域
//		response.setHeader("Access-Control-Allow-Headers",
//				"Authorization,Origin, X-Requested-With, Content-Type, Accept,x-access-token");// Origin,
//																								// X-Requested-With,
		// Content-Type,
		// Accept,Access-Token

		return true;
	}

	@Override
	public void postHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			ModelAndView modelAndView) throws Exception {
		request.setAttribute("ctx", request.getContextPath());
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
