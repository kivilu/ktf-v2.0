package com.kivi.framework.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.kivi.framework.component.KtfKit;
import com.kivi.framework.component.SpringContextHolder;
import com.kivi.framework.dto.JwtUserDTO;
import com.kivi.framework.service.ITimeoutService;
import com.kivi.framework.web.async.KtfAsyncResult;
import com.kivi.framework.web.async.KtfAsyncTimeoutRunnable;
import com.kivi.framework.web.async.KtfDeferredResult;
import com.kivi.framework.web.async.KtfTimeoutRunnable;
import com.kivi.framework.web.constant.WebConst;
import com.kivi.framework.web.properties.KtfWebProperties;
import com.kivi.framework.web.util.kit.HttpKit;
import com.kivi.framework.web.warpper.BaseControllerWarpper;
import com.vip.vjtools.vjkit.io.FileUtil;

@DependsOn(value = { SpringContextHolder.BEAN_NAME, KtfWebProperties.BEAN_NAME })
public class BaseController {

	protected static String	SUCCESS		= "SUCCESS";
	protected static String	ERROR		= "ERROR";

	protected static String	REDIRECT	= "redirect:";
	protected static String	FORWARD		= "forward:";

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		/**
		 * 自动转换日期类型的字段格式
		 */
		binder.registerCustomEditor(Date.class,
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH"), true));

	}

	/**
	 * redirect跳转
	 *
	 * @param url 目标url
	 */
	protected String redirect(String url) {
		return new StringBuilder(REDIRECT).append(url).toString();
	}

	protected HttpServletRequest getHttpServletRequest() {
		return HttpKit.getRequest();
	}

	protected HttpServletResponse getHttpServletResponse() {
		return HttpKit.getResponse();
	}

	protected HttpSession getSession() {
		return HttpKit.getRequest().getSession();
	}

	protected HttpSession getSession(Boolean flag) {
		return HttpKit.getRequest().getSession(flag);
	}

	protected String getPara(String name) {
		return HttpKit.getRequest().getParameter(name);
	}

	protected void setAttr(String name, Object value) {
		HttpKit.getRequest().setAttribute(name, value);
	}

	protected Object getAttr(String name) {
		return HttpKit.getRequest().getAttribute(name);
	}

	protected JwtUserDTO getJwtUser() {
		return (JwtUserDTO) HttpKit.getRequest().getAttribute(WebConst.ATTR_USERACCOUNT);
	}

	/**
	 * 包装一个list，让list增加额外属性
	 */
	protected Object warpObject(BaseControllerWarpper<?> warpper) {
		return warpper.warp();
	}

	/**
	 * 删除cookie
	 */
	protected void deleteCookieByName(String cookieName) {
		Cookie[] cookies = this.getHttpServletRequest().getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookieName)) {
				Cookie temp = new Cookie(cookie.getName(), "");
				temp.setMaxAge(0);
				this.getHttpServletResponse().addCookie(temp);
			}
		}
	}

	/**
	 * 返回前台文件流
	 * 
	 * @throws IOException
	 *
	 */
	protected ResponseEntity<byte[]> renderFile(String fileName, String filePath) throws IOException {
		byte[] bytes = FileUtil.toByteArray(new File(filePath));
		return renderFile(fileName, bytes);
	}

	/**
	 * 返回前台文件流
	 *
	 */
	protected ResponseEntity<byte[]> renderFile(String fileName, byte[] fileBytes) {
		String dfileName = null;
		try {
			dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", dfileName);
		return new ResponseEntity<>(fileBytes, headers, HttpStatus.CREATED);
	}

	protected Long msgId() {
		return KtfKit.me().nextId();
	}

	protected KtfDeferredResult newDeferredResult() {
		KtfWebProperties	ktfWebProperties	= SpringContextHolder.getBean(KtfWebProperties.class);

		ITimeoutService		timeoutService		= SpringContextHolder.getBean(ITimeoutService.class);

		KtfDeferredResult	result				= new KtfDeferredResult(msgId(),
				ktfWebProperties.getWebRequestTimeout());
		result.onTimeout(new KtfTimeoutRunnable<>(result, timeoutService));

		return result;
	}

	protected <T> KtfAsyncResult<T> newAsyncResultResult() {
		KtfWebProperties ktfWebProperties = SpringContextHolder.getBean(KtfWebProperties.class);

		return newAsyncResultResult(ktfWebProperties.getWebRequestTimeout());
	}

	protected <T> KtfAsyncResult<T> newAsyncResultResult(Long timeout) {
		ITimeoutService		timeoutService	= SpringContextHolder.getBean(ITimeoutService.class);

		KtfAsyncResult<T>	result			= new KtfAsyncResult<>(msgId(), timeout);
		result.onTimeout(new KtfAsyncTimeoutRunnable<>(result, timeoutService));

		return result;
	}

}
