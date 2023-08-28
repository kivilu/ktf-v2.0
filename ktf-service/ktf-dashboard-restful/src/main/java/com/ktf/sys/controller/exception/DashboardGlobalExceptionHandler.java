package com.ktf.sys.controller.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kivi.framework.model.ResultMap;
import com.kivi.sys.advice.GlobalExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class DashboardGlobalExceptionHandler extends GlobalExceptionHandler {

	/**
	 * 处理所有不可知的异常
	 * 
	 * @param e
	 * @return
	 */
	@Override
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<ResultMap> handleException(Exception e) {
		log.error("统一异常处理", e);

		return super.handleException(e);
	}
}
