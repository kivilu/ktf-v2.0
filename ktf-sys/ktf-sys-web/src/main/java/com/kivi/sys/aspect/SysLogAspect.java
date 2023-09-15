package com.kivi.sys.aspect;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.constant.enums.BasicType;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.dto.KtfItem;
import com.kivi.framework.form.LoginForm;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.util.kit.CollectionKit;
import com.kivi.framework.util.kit.StrKit;
import com.kivi.framework.web.util.kit.HttpKit;
import com.kivi.shiro.service.SysKit;
import com.kivi.sys.constant.SysWebConstant;
import com.kivi.sys.enums.SysLogType;
import com.kivi.sys.shiro.ShiroKit;
import com.kivi.sys.shiro.ShiroUser;
import com.kivi.sys.sys.dto.SysDicDTO;
import com.kivi.sys.sys.dto.SysLogDTO;
import com.vip.vjtools.vjkit.collection.type.ConcurrentHashSet;
import com.vip.vjtools.vjkit.number.NumberUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @description AOP 日志 AOP执行流程：@Around-->@Before-->@Around-->@After-->@AfterReturning
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {

    private ConcurrentHashMap<String, String> methodMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> moduleMap = new ConcurrentHashMap<>();
    private ConcurrentHashSet<String> auditSet = new ConcurrentHashSet<>();

    private ThreadLocal<SysLogDTO> sysLogExs = new ThreadLocal<>();

    // @Pointcut("within(@org.springframework.stereotype.Controller *)")
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void cutController() {

    }

    @SuppressWarnings("unchecked")
    @Around("cutController()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = HttpKit.getRequest();
        SysLogDTO sysLogEx = new SysLogDTO();
        sysLogExs.set(sysLogEx);

        String methodName = point.getSignature().getName();
        String className = point.getTarget().getClass().getName();
        Object[] params = point.getArgs();
        String uri = request.getRequestURI();

        if (log.isTraceEnabled()) {
            log.trace("ip={}\r\nuri={}\r\nmethod={}\r\nclass_method={}\r\nsessionId={}\r\nargs={}",
                // ip
                HttpKit.getRemoteAddress(request),
                // URL
                uri,
                // method
                methodName,
                // 类方法
                StringUtils.joinWith(".", point.getSignature().getDeclaringTypeName(), point.getSignature().getName()),
                HttpKit.getSessionId(request),
                // 参数
                params);
        }

        // 获取请求参数
        Map<String, Object> reqPamras = HttpKit.getRequestParameters();
        Object t = reqPamras.get("t");
        if (t != null) {
            reqPamras.put("t", NumberUtil.toLong((String)t));
        }
        // 获取方法参数
        if (params != null && params.length > 0) {
            int index = 0;
            for (Object obj : params) {
                if (obj == null || obj instanceof HttpSession)
                    continue;
                if (BasicType.isBasicType(obj.getClass())) {
                    reqPamras.put(StrKit.join("param", index++), obj);
                } else if (obj.getClass().isArray()) {
                    // reqPamras.put(StrKit.join("", "param", index++), obj);
                } else if (obj instanceof HashMap) {
                    reqPamras.putAll((Map<String, Object>)obj);
                } else {
                    reqPamras.putAll(BeanConverter.beanToMap(obj));
                }
            }

        }
        log.trace("====================reqPamras:{}", reqPamras);
        String sign = (String)reqPamras.remove("sign");
        String digest = (String)reqPamras.remove("digest");
        List<Entry<String, Object>> sortedParams = reqPamras.entrySet().stream().map(entry -> {
            if (!StringUtils.equalsAny(entry.getKey(), "t", "devType", "keyIndex")
                && !(entry.getValue() instanceof String)) {
                entry.setValue(entry.getValue().toString());
            }
            return entry;
        }).sorted(Entry.comparingByKey()).collect(Collectors.toList());
        log.trace("====================sortedParams:{}", sortedParams);

        sysLogEx.setUri(uri);
        sysLogEx.setMethod(methodName);
        sysLogEx.setClassName(className);
        sysLogEx.setOperationSign(sign);
        ShiroUser shiroUser = ShiroKit.getUser();
        if (null != shiroUser) {
            String loginName = shiroUser.getLoginName();
            sysLogEx.setUserName(loginName);
            sysLogEx.setLoginName(loginName);
            sysLogEx.setUserType(shiroUser.getUserType());
        }

        // sysLog.setUri(request != null ? request.getRequestURI() : null);
        sysLogEx.setParams(JSON.toJSONString(sortedParams) + StrKit.join(",{digest:", digest, "}"));
        sysLogEx.setClientIp(HttpKit.getRemoteAddress(request));
        if (StringUtils.equals("login", methodName) && CollectionKit.isNotEmpty(params)) {
            Object obj = params[0];
            if (obj instanceof LoginForm) {
                LoginForm loginForm = (LoginForm)params[0];
                sysLogEx.setLoginName(loginForm.getUserName());
            }
        }

        // 验证签名
        if (!StringUtils.isAnyBlank(sign, digest)) {
            SysKit.me().cifAuthentication().verify(shiroUser.getIpkDomain(), shiroUser.getLoginName(), digest, sign);
        }

        return point.proceed();
    }

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

    // @After("cutController()")
    // public void doAfter() {
    // SysLogEx sysLogEx = sysLogExs.get();
    // long totalMillis = ClockUtil.elapsedTime(sysLogEx.getStartTime());
    // sysLogEx.setTotalMillis(totalMillis);
    // log.info("----{}.{}执行时间：{}毫秒----", sysLogEx.getSysLog().getClassName(), sysLogEx.getSysLog().getMethod(),
    // totalMillis);
    // }

    private Boolean writeLog(Object object, SysLogDTO sysLog) {
        sysLog.setType(logType(sysLog.getMethod()).getCode());

        if (isWriteLog(sysLog.getMethod())) {
            try {

                if (object != null && object instanceof ResultMap) {
                    ResultMap resultMap = (ResultMap)object;
                    sysLog.setResult(KtfError.SUCCESS == resultMap.code() ? "成功" : resultMap.msg());
                    if ((KtfError.SUCCESS == resultMap.code() || KtfError.ACCEPTED == resultMap.code())
                        && StrKit.equals("login", sysLog.getMethod())) {
                        // String token = (String) resultMap.get("token");
                        // JwtUserDTO jwtUser = JwtKit.getJwtUser(token);
                        // sysLog.setUserName(jwtUser.getName());
                        // sysLog.setUserType(jwtUser.getUserType());
                        // return false;
                        /*login日记bug*/
                        sysLog.setUserName(sysLog.getLoginName());
                        sysLog.setUserType(sysLog.getType());
                    }

                    String recordIds = (String)resultMap.remove(SysWebConstant.RECORD_IDS);
                    sysLog.setRecordIds(recordIds);
                }

                sysLog.setOperation(operation(sysLog.getClassName(), sysLog.getMethod()));
                sysLog.setGmtCreate(LocalDateTime.now());

                if (auditSet.stream().anyMatch(uri -> StrKit.startWith(sysLog.getUri(), uri, false))) {
                    sysLog.setType(SysLogType.Audit.getCode());
                }

                sysLog.setTime(sysLog.getTotalMillis());

                SysKit.me().logService().save(sysLog);

            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return false;
            }
        }
        return true;
    }

    private SysLogType logType(String method) {
        String[] pattern = {"login", "logout"};

        return StringUtils.containsAny(method, pattern) ? SysLogType.Login : SysLogType.Operate;
    }

    private boolean isWriteLog(String method) {
        if (StrKit.equals("loginSettings", method))
            return false;

        if (this.methodMap.containsKey(method))
            return true;

        this.loadLogDic();

        return this.methodMap.containsKey(method);
    }

    String operation(String className, String methodName) {
        String moduleDesc = moduleMap.get(className);
        String methodDesc = methodMap.get(methodName);

        return StrKit.join(moduleDesc == null ? "" : moduleDesc, methodDesc == null ? "" : methodDesc);
    }

    void loadLogDic() {
        List<SysDicDTO> dics = SysKit.me().sysDicService().getChildren(SysWebConstant.LOG_OPERATIONS, false);

        dics.stream().forEach(dic -> {
            if (StrKit.equals(dic.getVarCode(), SysWebConstant.MODULE_NAME)) {
                List<KtfItem> list = JSON.parseArray(dic.getVarValue(), KtfItem.class);
                Map<String, String> map =
                    list.stream().collect(Collectors.toMap(KtfItem::getClazz, KtfItem::getName, (k1, k2) -> k1));
                moduleMap.clear();
                moduleMap.putAll(map);
            } else if (StrKit.equals(dic.getVarCode(), SysWebConstant.METHOD_NAME)) {
                List<KtfItem> list = JSON.parseArray(dic.getVarValue(), KtfItem.class);
                Map<String, String> map =
                    list.stream().collect(Collectors.toMap(KtfItem::getCode, KtfItem::getName, (k1, k2) -> k1));
                methodMap.clear();
                methodMap.putAll(map);
            } else if (StrKit.equals(dic.getVarCode(), SysWebConstant.AUDIT_URI)) {
                List<String> list = JSON.parseArray(dic.getVarValue(), String.class);
                auditSet.clear();
                auditSet.addAll(list);
            }

        });

    }

}
