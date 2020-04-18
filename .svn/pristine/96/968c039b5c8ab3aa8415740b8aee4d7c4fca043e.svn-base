package com.kivi.framework.web.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle( HttpServletRequest request,
            HttpServletResponse response, Object handler ) throws Exception {
        if (log.isTraceEnabled()) {
            log.trace("=====>http request parameter:{}", request.getParameterMap());
        }

        return true;
    }

    @Override
    public void postHandle( HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView ) throws Exception {
        request.setAttribute("ctx", request.getContextPath());
    }

    @Override
    public void afterCompletion( HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex )
            throws Exception {

    }

}
