
package com.kivi.framework.web.util.kit;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kivi.framework.util.kit.BeanKit;
import com.kivi.framework.util.kit.BeanKit.ValueProvider;
import com.kivi.framework.util.kit.ClassKit;
import com.kivi.framework.util.kit.StrKit;

public class HttpKit {

    public static String getIp() {
        HttpServletRequest request = HttpKit.getRequest();

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if ("0:0:0:0:0:0:0:1".equals(ip))
            ip = "127.0.0.1";

        return ip;
    }

    /**
     * 获取所有请求的值
     */
    public static Map<String, String> getRequestParameters() {
        HashMap<String, String> values = new HashMap<>();
        HttpServletRequest request = HttpKit.getRequest();
        Enumeration<String> enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String paramName = enums.nextElement();
            String paramValue = request.getParameter(paramName);
            values.put(paramName, paramValue);
        }
        return values;
    }

    /**
     * 获取 HttpServletRequest
     */
    public static HttpServletResponse getResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        return response;
    }

    /**
     * 获取 包装防Xss Sql注入的 HttpServletRequest
     * 
     * @return request
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return new WafRequestWrapper(request);
    }

    /**
     * ServletRequest 参数转Bean
     * 
     * @param request
     *            ServletRequest
     * @param beanClass
     *            Bean Class
     * @return Bean
     */
    public static <T> T requestParamToBean( javax.servlet.ServletRequest request, Class<T> beanClass ) {
        return fillBeanWithRequestParam(request, ClassKit.newInstance(beanClass));
    }

    /**
     * ServletRequest 参数转Bean
     * 
     * @param request
     *            ServletRequest
     * @param bean
     *            Bean
     * @return Bean
     */
    public static <T> T fillBeanWithRequestParam( final javax.servlet.ServletRequest request, T bean ) {
        final String beanName = StrKit.lowerFirst(bean.getClass().getSimpleName());
        return BeanKit.fillBean(bean, new ValueProvider() {
            @Override
            public Object value( String name ) {
                String value = request.getParameter(name);
                if (StrKit.isEmpty(value)) {
                    // 使用类名前缀尝试查找值
                    value = request.getParameter(beanName + StrKit.DOT + name);
                    if (StrKit.isEmpty(value)) {
                        // 此处取得的值为空时跳过，包括null和""
                        value = null;
                    }
                }
                return value;
            }
        });
    }

}
