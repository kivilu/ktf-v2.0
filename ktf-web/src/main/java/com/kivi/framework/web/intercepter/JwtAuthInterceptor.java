package com.kivi.framework.web.intercepter;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.kivi.framework.component.SpringContextHolder;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.dto.JwtUserDTO;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.service.KtfTokenService;
import com.kivi.framework.web.annotation.LoginToken;
import com.kivi.framework.web.annotation.PassToken;
import com.kivi.framework.web.constant.WebConst;
import com.kivi.framework.web.jwt.IJwtUserServie;
import com.kivi.framework.web.jwt.JwtKit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean
			preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object)
					throws Exception {

		// 从http请求头中取出x-access-token
		String jwtToken = httpServletRequest.getHeader(WebConst.HTTP_AUTHORIZATION);
		// 如果不是映射到方法直接通过
		if (!(object instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod	handlerMethod	= (HandlerMethod) object;
		Method			method			= handlerMethod.getMethod();
		// 检查是否有passtoken注释，有则跳过认证
		if (method.isAnnotationPresent(PassToken.class)) {
			PassToken passToken = method.getAnnotation(PassToken.class);
			if (passToken.required()) {
				return true;
			}
		}
		// 检查有没有需要用户权限的注解
		if (method.isAnnotationPresent(LoginToken.class)) {
			LoginToken LoginToken = method.getAnnotation(LoginToken.class);
			if (LoginToken.required()) {
				// 执行认证
				if (jwtToken == null) {
					throw new KtfException(KtfError.E_UNAUTHORIZED, "用户尚未登录，请重新登录");
				}
				// 获取 token中的JwtUser
				JwtUserDTO jwtUser = null;
				try {
					jwtUser = JwtKit.getJwtUser(jwtToken);
					httpServletRequest.setAttribute(WebConst.ATTR_USERACCOUNT, jwtUser);
				} catch (JWTDecodeException j) {
					log.error("解析JWT异常", j);
					throw new KtfException(KtfError.E_UNAUTHORIZED, "用户尚未登录，请重新登录");
				}

				KtfTokenService	tokenService	= SpringContextHolder.getBean(KtfTokenService.class);
				String			token			= tokenService.cache(jwtUser.getId().toString());
				if (token == null) {
					IJwtUserServie jwtUserService = SpringContextHolder.getBeanNoAssert(IJwtUserServie.class);
					if (jwtUserService != null) {
						jwtUserService.logouted(jwtUser);
					}
					throw new KtfException(KtfError.E_UNAUTHORIZED, "登录状态已过期，请重新登录");
				}
				// 验证 token
				return JwtKit.verify(jwtToken, token);

			}
		}
		return true;
	}

	@Override
	public void postHandle(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			Object o,
			ModelAndView modelAndView) throws Exception {
		log.trace("---postHandle---");
	}

	@Override
	public void afterCompletion(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			Object o,
			Exception e) throws Exception {
		log.trace("---afterCompletion---");
	}
}
