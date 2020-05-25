package com.kivi.dashboard.shiro.advice;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kivi.framework.constant.KtfError;

/**
 **/

@RestControllerAdvice
public class ShiroExceptionHandler {

	@ExceptionHandler(value = { UnauthorizedException.class })
	public Map<String, Object> unauthorizedExceptionHandler(HttpServletRequest request, Exception e) {
		return noPermissionResult();
	}

	private Map<String, Object> noPermissionResult() {
		Map<String, Object> result = new HashMap<>();
		result.put("code", KtfError.E_FORBIDDEN);
		result.put("msg", "暂无权限");
		return result;
	}

}
