package com.kivi.dashboard.aspect;

import java.time.LocalDateTime;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.shiro.ShiroUser;
import com.kivi.dashboard.sys.entity.SysLog;
import com.kivi.dashboard.sys.service.ISysLogService;
import com.vip.vjtools.vjkit.time.ClockUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @description AOP 日志
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {
	private long			startTime		= 0;
	private String			strMethodName	= "";
	private String			strClassName	= "";
	private String			args			= "";
	private String			clientIp		= "127.0.0.1";

	@Autowired
	private ISysLogService	logService;

	// @Pointcut("within(@org.springframework.stereotype.Controller *)")
	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void cutController() {
	}

	@Before("cutController()")
	public void doBefore(JoinPoint joinPoint) {
		startTime = System.currentTimeMillis();

		ServletRequestAttributes	attributes	= (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest			request		= attributes.getRequest();

		log.info("ip={}\r\nurl={}\r\nmethod={}\r\nclass_method={}\r\nargs={}",
				// ip
				request.getRemoteAddr(),
				// URL
				request.getRequestURL(),
				// method
				request.getMethod(),
				// 类方法
				StringUtils.joinWith(".", joinPoint.getSignature().getDeclaringTypeName(),
						joinPoint.getSignature().getName()),
				// 参数
				joinPoint.getArgs());
	}

	@Around("cutController()")
	public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
		strMethodName	= point.getSignature().getName();
		strClassName	= point.getTarget().getClass().getName();
		Object[]			params		= point.getArgs();
		StringBuffer		bfParams	= new StringBuffer();
		Enumeration<String>	paraNames	= null;
		HttpServletRequest	request		= null;
		if (params != null && params.length > 0) {
			request		= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			paraNames	= request.getParameterNames();
			String	key;
			String	value;
			while (paraNames.hasMoreElements()) {
				key		= paraNames.nextElement();
				value	= request.getParameter(key);
				bfParams.append(key).append("=").append(value).append("&");
			}
			if (StringUtils.isBlank(bfParams)) {
				bfParams.append(request.getQueryString());
			}
			args		= bfParams.toString();
			clientIp	= request.getRemoteAddr();
		}

//        String strMessage = String.format("[类名]:%s,[方法]:%s,[参数]:%s", strClassName, strMethodName, bfParams.toString());
//        log.info(strMessage);

		return point.proceed();
	}

	@AfterReturning(returning = "object", pointcut = "cutController()")
	public void doAfterReturning(Object object) {
		if (object != null) {
			log.info("response={}", object.toString());
		} else {
			log.info("response=");
		}

	}

	@After("cutController()")
	public void doAfter() {
		long totalMillis = ClockUtil.elapsedTime(startTime);
		if (isWriteLog(strMethodName)) {
			try {
				ShiroUser shiroUser = ShiroKit.getUser();
				if (null != shiroUser) {
					String	loginName	= shiroUser.getLoginName();
					SysLog	sysLog		= new SysLog();
					sysLog.setLoginName(loginName);
					sysLog.setRoleName(shiroUser.getRoles().get(0));
					sysLog.setClassName(strClassName);
					sysLog.setMethod(strMethodName);
					if (StringUtils.isNotBlank(args) && !args.equals("null")) {
						sysLog.setParams(args);
					}
					sysLog.setTime(totalMillis);
					sysLog.setGmtCreate(LocalDateTime.now());
					sysLog.setClientIp(clientIp);
					logService.save(sysLog);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		log.info("----执行时间：{}毫秒----", totalMillis);
	}

	private boolean isWriteLog(String method) {
		String[] pattern = { "login", "logout", "save", "update", "delete", "list", "page", "edit", "grant" };
		/*
		 * for (String s : pattern) { if (method.indexOf(s) > -1) { return true; } }
		 */

		return StringUtils.containsAny(method, pattern);
	}
}
