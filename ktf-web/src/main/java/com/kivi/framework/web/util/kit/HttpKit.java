
package com.kivi.framework.web.util.kit;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpKit {

	public static String getRemoteAddress() {
		HttpServletRequest request = HttpKit.getRequest();
		return getRemoteAddress(request);
	}

	public static String getRemoteAddress(HttpServletRequest request) {
		if (request == null)
			return null;

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

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ip != null && ip.length() > 0) {
			String[] ips = ip.split(",");
			if (ips.length > 0) {
				ip = ips[0];
			}
		}

		if ("0:0:0:0:0:0:0:1".equals(ip))
			ip = "127.0.0.1";

		return ip;
	}

	/**
	 * 获取所有请求的值
	 */
	public static Map<String, Object> getRequestParameters() {
		HashMap<String, Object>	values	= new HashMap<>();
		HttpServletRequest		request	= HttpKit.getRequest();
		Enumeration<String>		enums	= request.getParameterNames();
		while (enums.hasMoreElements()) {
			String	paramName	= enums.nextElement();
			String	paramValue	= request.getParameter(paramName);
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
	 * url 编码
	 *
	 * @param str 内容 param charsets 编码
	 * @return
	 */
	public static String encodeURL(String str, String charsets) {
		String result = "";
		if (null == str) {
			return "";
		}
		try {
			result = java.net.URLEncoder.encode(str, charsets);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * url解码
	 *
	 * @param str 内容
	 * @return
	 * @Param charsets 编码
	 */
	public static String decodeURL(String str, String charsets) {
		String result = "";
		if (null == str) {
			return "";
		}
		try {
			result = java.net.URLDecoder.decode(str, charsets);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

}
