package com.kivi.sys.shiro.oauth2;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kivi.framework.constant.KtfError;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.web.constant.WebConst;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description oauth2过滤器
 */
@Slf4j
public class OAuth2Filter extends AuthenticatingFilter {

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		// 获取请求token
		String token = getRequestToken((HttpServletRequest) request);

		if (StringUtils.isBlank(token)) {
			return null;
		}

		return new OAuth2Token(token);
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
			return true;
		}

		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// 获取请求token，如果token不存在，直接返回401
		String token = getRequestToken((HttpServletRequest) request);
		log.trace("请求token:{}", token);
		if (StringUtils.isBlank(token)) {
			HttpServletRequest	httpRequest		= (HttpServletRequest) request;
			HttpServletResponse	httpResponse	= (HttpServletResponse) response;
			httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
			httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));

			String json = ResultMap.error(KtfError.E_UNAUTHORIZED, "invalid token").toString();

			httpResponse.getWriter().print(json);

			return false;
		}

		return executeLogin(request, response);
	}

	@Override
	protected boolean onLoginFailure(
			AuthenticationToken token,
			AuthenticationException e,
			ServletRequest request,
			ServletResponse response) {
		HttpServletResponse	httpResponse	= (HttpServletResponse) response;
		HttpServletRequest	httpRequest		= (HttpServletRequest) request;
		httpResponse.setContentType("application/json;charset=utf-8");
		httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
		try {
			// 处理登录失败的异常
			Throwable	throwable	= e.getCause() == null ? e : e.getCause();
			String		json		= ResultMap.error(KtfError.E_UNAUTHORIZED, throwable.getMessage()).toString();
			httpResponse.getWriter().print(json);
		} catch (IOException e1) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 获取请求的token
	 */
	private String getRequestToken(HttpServletRequest httpRequest) {
		// 从header中获取token
		String token = httpRequest.getHeader(WebConst.AUTH_TOKEN);

		// 如果header中不存在token，则从参数中获取token
		if (StringUtils.isBlank(token)) {
			token = httpRequest.getParameter(WebConst.AUTH_TOKEN);
		}

		return token;
	}

}
