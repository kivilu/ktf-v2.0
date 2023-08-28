package com.ktf.sys.aspect;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.kivi.cif.auth.CifAuthentication;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.constant.enums.BasicType;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.dto.JwtUserDTO;
import com.kivi.framework.dto.KtfItem;
import com.kivi.framework.form.LoginForm;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.util.kit.CollectionKit;
import com.kivi.framework.util.kit.StrKit;
import com.kivi.framework.web.jwt.JwtKit;
import com.kivi.framework.web.util.kit.HttpKit;
import com.kivi.sys.enums.DicEnum;
import com.kivi.sys.enums.SysLogType;
import com.kivi.sys.shiro.ShiroKit;
import com.kivi.sys.shiro.ShiroUser;
import com.kivi.sys.sys.dto.SysDicDTO;
import com.kivi.sys.sys.entity.SysLog;
import com.kivi.sys.sys.entity.SysLogEx;
import com.kivi.sys.sys.service.ISysLogService;
import com.kivi.sys.sys.service.SysDicService;
import com.vip.vjtools.vjkit.number.NumberUtil;
import com.vip.vjtools.vjkit.time.ClockUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @description AOP 日志
 *              AOP执行流程：@Around-->@Before-->@Around-->@After-->@AfterReturning
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {

	@Autowired
	private SysDicService			sysDicService;

	@Autowired
	private ISysLogService			logService;

	@Autowired
	private CifAuthentication		ktfAuthentication;

	// @Autowired(required = false)
	// private SysLogSignService sysLogSignService;

	private ThreadLocal<SysLogEx>	sysLogExs	= new ThreadLocal<>();

	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void cutController() {

	}

	@SuppressWarnings("unchecked")
	@Around("cutController()")
	public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
		HttpServletRequest	request		= HttpKit.getRequest();
		SysLogEx			sysLogEx	= new SysLogEx();
		sysLogExs.set(sysLogEx);

		String		methodName	= point.getSignature().getName();
		String		className	= point.getTarget().getClass().getName();
		Object[]	params		= point.getArgs();

		if (log.isTraceEnabled()) {
			log.trace("ip={}\r\nuri={}\r\nmethod={}\r\nclass_method={}\r\nargs={}",
					// ip
					HttpKit.getRemoteAddress(request),
					// URL
					request.getRequestURI(),
					// method
					methodName,
					// 类方法
					StringUtils.joinWith(".", point.getSignature().getDeclaringTypeName(),
							point.getSignature().getName()),
					// 参数
					params);
		}

		// 获取请求参数
		Map<String, Object>	reqPamras	= HttpKit.getRequestParameters();
		Object				t			= reqPamras.get("t");
		if (t != null) {
			reqPamras.put("t", NumberUtil.toLong((String) t));
		}
		// 获取方法参数
		if (params != null && params.length > 0) {
			int index = 0;
			for (Object obj : params) {
				if (obj == null)
					continue;
				if (BasicType.isBasicType(obj.getClass())) {
					reqPamras.put(StrKit.join("", "param", index++), obj);
				} else if (obj.getClass().isArray()) {
					// reqPamras.put(StrKit.join("", "param", index++), obj);
				} else if (obj instanceof HashMap) {
					reqPamras.putAll((Map<String, Object>) obj);
				} else {
					reqPamras.putAll(BeanConverter.beanToMap(obj));
				}
			}

		}
		log.trace("====================reqPamras:{}", reqPamras);
		String						sign			= (String) reqPamras.remove("sign");
		String						digest			= (String) reqPamras.remove("digest");
		List<Entry<String, Object>>	sortedParams	= reqPamras.entrySet().stream().map(entry -> {
														if (!StringUtils.equalsAny(entry.getKey(), "t", "devType",
																"keyIndex") && !(entry.getValue() instanceof String)) {
															entry.setValue(entry.getValue().toString());
														}
														return entry;
													})
				.sorted(Entry.comparingByKey()).collect(Collectors.toList());
		log.trace("====================sortedParams:{}", sortedParams);

		SysLog sysLog = sysLogEx.getSysLog();
		sysLog.setMethod(methodName);
		sysLog.setClassName(className);
		sysLog.setOperationSign(sign);
		sysLog.setUri(request != null ? request.getRequestURI() : null);
		sysLog.setParams(JSON.toJSONString(sortedParams) + StrKit.join("", ",{digest:", digest, "}"));
		sysLog.setClientIp(HttpKit.getRemoteAddress(request));
		if (StringUtils.equals("login", methodName) && CollectionKit.isNotEmpty(params)) {
			Object obj = params[0];
			if (obj instanceof LoginForm) {
				LoginForm loginForm = (LoginForm) params[0];
				sysLog.setLoginName(loginForm.getUserName());
			}
		}

		// 验证签名
		if (!StringUtils.isAnyBlank(sign, digest)) {
			ShiroUser shiroUser = ShiroKit.getUser();
			// ktfAuthentication.verifySign(shiroUser.getLoginName(), digest, sign);
		}

		return point.proceed();
	}

	/*
	 * @Before("cutController()") public void doBefore(JoinPoint joinPoint) { }
	 */

	@AfterThrowing(throwing = "ex", pointcut = "cutController()")
	public void doAfterThrowing(Throwable ex) {
		log.error("Controller执行异常", ex);
		writeLog(ResultMap.error("执行异常"), sysLogExs.get());
	}

	@AfterReturning(returning = "object", pointcut = "cutController()")
	public void doAfterReturning(JoinPoint joinPoint, Object object) {
		log.trace("response={}", object);

		writeLog(object, sysLogExs.get());

	}

	@After("cutController()")
	public void doAfter() {
		SysLogEx	sysLogEx	= sysLogExs.get();
		long		totalMillis	= ClockUtil.elapsedTime(sysLogEx.getStartTime());
		sysLogEx.setTotalMillis(totalMillis);
		log.info("----{}.{}执行时间：{}毫秒----", sysLogEx.getSysLog().getClassName(), sysLogEx.getSysLog().getMethod(),
				totalMillis);
	}

	private Boolean writeLog(Object object, SysLogEx sysLogEx) {
		SysLog sysLog = sysLogEx.getSysLog();
		sysLog.setType(logType(sysLog.getMethod()).getCode());

		if (isWriteLog(sysLog.getUri())) {
			try {
				ShiroUser shiroUser = ShiroKit.getUser();
				if (null != shiroUser) {
					String loginName = shiroUser.getLoginName();
					sysLog.setUserName(shiroUser.getName());
					sysLog.setLoginName(loginName);
					sysLog.setUserType(shiroUser.getUserType());
				}

				if (object != null && object instanceof ResultMap) {
					ResultMap resultMap = (ResultMap) object;
					sysLog.setResult(KtfError.SUCCESS == resultMap.code() ? "成功" : resultMap.msg());
					if ((KtfError.SUCCESS == resultMap.code() || KtfError.ACCEPTED == resultMap.code())
							&& StrKit.equals("login", sysLog.getMethod())) {
						String		token	= (String) resultMap.get("token");
						JwtUserDTO	jwtUser	= JwtKit.getJwtUser(token);
						sysLog.setUserName(jwtUser.getName());
						sysLog.setUserType(jwtUser.getUserType());
					}

					String recordIds = (String) resultMap.remove("record_ids");
					sysLog.setRecordIds(recordIds);
				}

				sysLog.setOperation(operation(sysLog.getClassName(), sysLog.getMethod()));
				sysLog.setGmtCreate(LocalDateTime.now());

//				SysDic dic = sysDicService.getByVarCode(sysLog.getClassName(), DicEnum.AUDIT_METHODS);
//				if (dic != null) {
//					List<String>		patterns	= sysDicService.listVarCode(dic.getVarCode(), dic.getParentId());
//					Optional<String>	op			= patterns.stream()
//							.filter(pattern -> StringUtils.contains(sysLog.getMethod(), pattern)).findAny();
//					if (op.isPresent()) {
//						sysLog.setType(SysLogType.Signed.getCode());
//					}
//				}

				sysLog.setTime(sysLogEx.getTotalMillis());

				logService.save(sysLog);

			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return false;
			}
		}
		return true;
	}

	private SysLogType logType(String method) {
		String[] pattern = { "login", "logout" };

		return StringUtils.containsAny(method, pattern) ? SysLogType.Login : SysLogType.Operate;
	}

	private boolean isWriteLog(String method) {
		SysDicDTO		dto		= sysDicService.getDto(DicEnum.METHOD_NAME.getCode(), DicEnum.LOG_OPERATIONS.getCode());
		List<KtfItem>	methods	= JSON.parseArray(dto.getVarValue(), KtfItem.class);

		return methods.stream().filter(pattern -> StringUtils.contains(method, pattern.getCode())).findAny()
				.isPresent();
	}

	String operation(String className, String methodName) {
		return "方法";
//		SysDic	dicModule	= sysDicService.getByVarCode(className, DicEnum.MODULE_NAME);
//		SysDic	dicMethod	= sysDicService.getByVarCode(methodName, DicEnum.METHOD_NAME);
//
//		return StrKit.join("", dicModule == null ? "" : dicModule.getVarName(),
//				dicMethod == null ? "" : dicMethod.getVarName());
	}

}
