package com.kivi.framework.web.intercepter;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kivi.framework.component.SpringContextHolder;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.web.properties.KtfWebProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * @Descriptin 上传文件类型拦截器
 */
@Slf4j
public class FileUploadTypeInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean flag = true;
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest	multipartRequest	= (MultipartHttpServletRequest) request;
			Map<String, MultipartFile>	files				= multipartRequest.getFileMap();
			Iterator<String>			iterator			= files.keySet().iterator();
			// 对多部件请求资源进行遍历
			while (iterator.hasNext()) {
				String			formKey			= iterator.next();
				MultipartFile	multipartFile	= multipartRequest.getFile(formKey);
				String			filename		= multipartFile.getOriginalFilename();
				// 判断是否为限制文件类型
				if (!checkFile(filename)) {
					log.error(
							"只允许上传gif,jpg,jpeg,bmp,png,jar,doc,docx,xls,xlsx,pdf,txt,rar,zip,pem,key,cer,p12,pkcs12,pfx,jks,pkm,vkd格式文件");
					new KtfException(
							"只允许上传gif,jpg,jpeg,bmp,png,jar,doc,docx,xls,xlsx,pdf,txt,rar,zip,pem,key,cer,p12,pkcs12,pfx,pkm,pfx,vkd格式文件");
					flag = false;
				}
			}
		}
		return flag;
	}

	/**
	 * 判断是否为允许的上传文件类型,true表示允许
	 */
	private boolean checkFile(String fileName) {
		// 设置允许上传文件类型
		String				suffixList	= "pkm,vkd,gif,jpg,jpeg,bmp,png,jar,doc,docx,xls,xlsx,pdf,txt,rar,zip,pem,key,cer,p12,pkcs12,pfx,jks,";

		KtfWebProperties	webProp		= SpringContextHolder.getBean(KtfWebProperties.class);
		if (webProp != null)
			suffixList = webProp.getAllowFileSuffix();

		// 获取文件后缀
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

		return StringUtils.containsIgnoreCase(suffixList, suffix);
	}

}
