package com.kivi.dashboard.shiro.jwt;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kivi.dashboard.shiro.ShiroUserKit;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.dto.JwtUserDTO;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.util.kit.StrKit;
import com.kivi.framework.web.constant.WebConst;
import com.kivi.framework.web.jwt.JwtKit;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description oauth2过滤器
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;

        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }

        return super.preHandle(request, response);
    }

    /**
     * 执行登录认证
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
                return true;
            } catch (Exception e) {
                throw (RuntimeException)e;
            }
        } else {
            // 如果请求头不存在 Authorization，则可能是执行不合规操作直接返回 false 进入onAccessDenied
            return false;
        }
    }

    /**
     * 处理未经验证的请求
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        throw new UnauthorizedException("用户尚未登录，请登录");
    }

    /**
     * 判断用户是否想要登入 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest)request;
        String authorization = getRequestToken(req);
        return StrKit.isNotBlank(authorization);
    }

    /**
    *
    */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String accessToken = getRequestToken(httpServletRequest);
        log.trace("请求token:{}", accessToken);

        // 获取 token中的JwtUser
        JwtUserDTO jwtUser = null;
        try {

            jwtUser = JwtKit.getJwtUser(accessToken);
            if (log.isTraceEnabled())
                log.trace("JwtToken中的JwtUser：{}", jwtUser);
            if (null == jwtUser) {
                throw new KtfException(KtfError.E_UNAUTHORIZED, "用户尚未登录，请登录");
            }

            ShiroUserKit.me().verifyAccessToken(jwtUser.getId().toString(), accessToken);

            httpServletRequest.setAttribute(WebConst.ATTR_USERACCOUNT, jwtUser);

        } catch (Exception j) {
            log.error("解析JWT异常", j);
            throw new KtfException(KtfError.E_UNAUTHORIZED, "用户尚未登录，请登录");
        }

        JwtToken jwtToken = new JwtToken(jwtUser.getId(), accessToken);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    // @Override
    // protected void cleanup(ServletRequest request, ServletResponse response, Exception existing)
    // throws ServletException, IOException {
    // if (null == existing) {
    // super.cleanup(request, response, existing);
    // } else {
    // response401(response, existing);
    // }
    // }

    @Override
    protected boolean isRememberMe(ServletRequest request) {
        return super.isRememberMe(request);
    }

    private void response401(ServletResponse response, Exception throwable) {
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        try { // 处理登录失败的异常
            log.error("登录失败的异常", throwable);
            String json = ResultMap.error(KtfError.E_UNAUTHORIZED, "用户尚未登录，请登录").toString();
            httpServletResponse.setStatus(KtfError.E_UNAUTHORIZED);
            httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
            httpServletResponse.getWriter().print(json);
        } catch (IOException e1) {
            log.error("响应客户端异常", e1);
        }
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

    /**
     * 为response设置header，实现跨域
     */
    private void setHeader(HttpServletRequest request, HttpServletResponse response) {
        // 跨域的header设置
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        // 防止乱码，适用于传输JSON数据
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
    }

}
