package com.kivi.dashboard.advice;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.shiro.ShiroUser;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.service.KtfTokenService;

import lombok.extern.slf4j.Slf4j;

/**
 **/

//@RestControllerAdvice
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
	ResponseEntity<ResultMap> handleException(Exception e) {
		log.error("Controller未知异常处理", e);

		ResultMap result = null;

		if (e instanceof KtfException) {
			KtfException ke = (KtfException) e;
			result = ResultMap.error(ke.getCode(), ke.getTips());
		} else {
			result = ResultMap.error(KtfError.E_SERVICE_UNAVAILABLE, "操作异常");
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

	@ExceptionHandler(value = { UnauthorizedException.class })
	ResponseEntity<ResultMap> unauthorizedExceptionHandler(HttpServletRequest request, Exception e) {
		log.error("访问无权限异常处理", e);
		ShiroUser shiroUser = ShiroKit.getUser();
		if (shiroUser != null && shiroUser.getId() != null) {
			log.warn("返回客户端401错误，清除用户{}的登录token", shiroUser.getLoginName());
			ktfTokenService.evictJwt(shiroUser.getId().toString());
		}

		ResultMap	result		= ResultMap.error(KtfError.E_UNAUTHORIZED, "暂无权限");
		HttpStatus	httpStatus	= HttpStatus.resolve(result.code());
		return new ResponseEntity<>(result, httpStatus);
	}

}
