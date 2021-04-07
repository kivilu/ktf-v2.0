package com.kivi.dashboard.shiro.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kivi.dashboard.shiro.ShiroUserKit;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.dto.JwtUserDTO;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.vo.UserVo;
import com.kivi.framework.web.constant.WebConst;
import com.kivi.framework.web.jwt.JwtKit;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description Jwt认证过滤器
 */
@Slf4j
public class JwtAuthFilter extends AuthenticatingFilter {

	public JwtAuthFilter() {
		this.setLoginUrl("/login");
	}

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
		if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) // 对于OPTION请求做拦截，不做token校验
			return false;

		return super.preHandle(request, response);
	}

	@Override
	protected void postHandle(ServletRequest request, ServletResponse response) {
		this.fillCorsHeader(WebUtils.toHttp(request), WebUtils.toHttp(response));
		request.setAttribute("jwtShiroFilter.FILTERED", true);
	}

	/**
	 * 父类会在请求进入拦截器后调用该方法，返回true则继续，返回false则会调用onAccessDenied()。这里在不通过时，还调用了isPermissive()方法，我们后面解释。
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (this.isLoginRequest(request, response))
			return true;

		Boolean afterFiltered = (Boolean) (request.getAttribute("jwtShiroFilter.FILTERED"));
		if (BooleanUtils.isTrue(afterFiltered))
			return true;

		boolean allowed = false;
		try {
			allowed = executeLogin(request, response);
		} catch (IllegalStateException e) { // not found any token
			log.error("请求Token无效", e);
		} catch (Exception e) {
			log.error("Error occurs when login", e);
		}
		return allowed || super.isPermissive(mappedValue);
	}

	/**
	 * 这里重写了父类的方法，使用我们自己定义的Token类，提交给shiro。这个方法返回null的话会直接抛出异常，进入isAccessAllowed（）的异常处理逻辑。
	 */
	@Override
	protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
		HttpServletRequest	httpServletRequest	= (HttpServletRequest) servletRequest;
		String				accessToken			= getRequestToken(httpServletRequest);
		log.trace("请求token:{}", accessToken);

		if (StringUtils.isNotBlank(accessToken) && !JwtKit.isExpired(accessToken)) {
			JwtUserDTO jwtUser = null;
			try {
				jwtUser = JwtKit.getJwtUser(accessToken);
			} catch (Exception e) {
				log.error("JWT token获取JwtUserDTO异常", e);
				return null;
			}
			httpServletRequest.setAttribute(WebConst.ATTR_USERACCOUNT, jwtUser);
			return new JwtToken(accessToken);
		}

		return null;
	}

	/**
	 * 如果这个Filter在之前isAccessAllowed（）方法中返回false,则会进入这个方法。我们这里直接返回错误的response
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletResponse	httpServletResponse	= WebUtils.toHttp(response);
		String				json				= ResultMap.error(KtfError.E_UNAUTHORIZED, "登录状态失效，请重新登录").toString();
		httpServletResponse.setStatus(HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION);
		httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
		httpServletResponse.getWriter().print(json);

		return false;
	}

	/**
	 * 如果Shiro Login认证成功，会进入该方法，等同于用户名密码登录成功，我们这里还判断了是否要刷新Token
	 */
	@Override
	protected boolean
			onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response)
					throws Exception {
		HttpServletResponse	httpServletResponse	= WebUtils.toHttp(response);
		String				newToken			= null;
		if (token instanceof JwtToken) {
			JwtToken	jwtToken		= (JwtToken) token;

			Object		principal		= subject.getPrincipal();
			UserVo		user			= BeanConverter.convert(UserVo.class, principal);
			boolean		shouldRefresh	= shouldTokenRefresh(JwtKit.getIssuedAt(jwtToken.getPrincipal()));
			if (shouldRefresh) {
				newToken = ShiroUserKit.me().generateJwtToken(user);
			}
		}
		if (StringUtils.isNotBlank(newToken))
			httpServletResponse.setHeader(WebConst.AUTH_TOKEN, newToken);

		return true;
	}

	/**
	 * 如果调用shiro的login认证失败，会回调这个方法，这里我们什么都不做，因为逻辑放到了onAccessDenied（）中。
	 */
	@Override
	protected boolean onLoginFailure(
			AuthenticationToken token,
			AuthenticationException e,
			ServletRequest request,
			ServletResponse response) {
		log.error("Validate token fail, token:{}, error:{}", token.toString(), e.getMessage());
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

	protected boolean shouldTokenRefresh(Date issueAt) {
		LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
		return LocalDateTime.now().minusSeconds(ShiroUserKit.me().getTokenRefreshInterval()).isAfter(issueTime);
	}

	protected void fillCorsHeader(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,HEAD");
		httpServletResponse.setHeader("Access-Control-Allow-Headers",
				httpServletRequest.getHeader("Access-Control-Request-Headers"));
	}

}
