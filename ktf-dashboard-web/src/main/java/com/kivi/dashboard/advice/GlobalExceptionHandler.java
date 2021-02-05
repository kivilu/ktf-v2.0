package com.kivi.dashboard.advice;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.shiro.ShiroUser;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.exception.KtfMockException;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.service.KtfTokenService;
import com.kivi.framework.util.kit.StrKit;

import lombok.extern.slf4j.Slf4j;

/**
 **/

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    private KtfTokenService ktfTokenService;

    /**
     * 处理所有不可知的异常
     * 
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResponseEntity<ResultMap> handleException(Exception e) {
        log.error("统一异常处理，{}", e.getMessage());

        ResultMap result = null;

        if (e instanceof KtfException) {
            KtfException ke = (KtfException)e;
            result = ke.getCode() == null ? ResultMap.error(ke.getTips()) : ResultMap.error(ke.getCode(), ke.getTips());
        }
        if (e instanceof KtfMockException) {
            KtfMockException ke = (KtfMockException)e;
            result = ke.getCode() == null ? ResultMap.error(ke.getTips()) : ResultMap.error(ke.getCode(), ke.getTips());
        } else if (e instanceof UnauthorizedException || e instanceof AuthorizationException) {
            result = ResultMap.error(KtfError.E_FORBIDDEN, "没有权限访问");
        } else if (e instanceof UnauthenticatedException) {
            result = ResultMap.error(KtfError.E_UNAUTHORIZED, "尚未登录，请登录");
        } else if (e instanceof AuthenticationException) {
            result = ResultMap.error(KtfError.E_UNAUTHORIZED, StrKit.join("登录失败:", e.getMessage()));
        } else {
            result = ResultMap.error(KtfError.E_SERVICE_UNAVAILABLE, e.getMessage());
        }

        if (result.code() == KtfError.E_UNAUTHORIZED) {
            ShiroUser shiroUser = ShiroKit.getUser();
            if (shiroUser != null && shiroUser.getId() != null) {
                log.warn("返回客户端401错误，清除用户{}的登录token", shiroUser.getLoginName());
                ktfTokenService.evictJwt(shiroUser.getId().toString());
            }
        }

        HttpStatus httpStatus = HttpStatus.resolve(result.code());
        if (httpStatus == null)
            httpStatus = HttpStatus.SERVICE_UNAVAILABLE;

        return new ResponseEntity<>(result, httpStatus);
    }

    /*@ExceptionHandler(value = {UnauthorizedException.class})
    ResponseEntity<ResultMap> unauthorizedExceptionHandler(HttpServletRequest request, Exception e) {
        log.error("访问无权限异常处理", e);
        ShiroUser shiroUser = ShiroKit.getUser();
        if (shiroUser != null && shiroUser.getId() != null) {
            log.warn("返回客户端401错误，清除用户{}的登录token", shiroUser.getLoginName());
            ktfTokenService.evictJwt(shiroUser.getId().toString());
        }
    
        ResultMap result = ResultMap.error(KtfError.E_UNAUTHORIZED, "暂无权限");
        HttpStatus httpStatus = HttpStatus.resolve(result.code());
        return new ResponseEntity<>(result, httpStatus);
    }*/

}
