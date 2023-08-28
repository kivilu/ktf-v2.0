package com.kivi.sys.service;

import com.kivi.framework.model.ResultMap;
import com.kivi.sys.sys.entity.SysLog;

public interface SysLogSignService {

	/**
	 * 日志签名
	 * 
	 * @param sysLog
	 * @return
	 */
	String sign(SysLog syslog, String... datas);

	/**
	 * 签名
	 * 
	 * @param datas
	 * @return
	 */
	String sign(String... datas);

	/**
	 * 验证日志签名
	 * 
	 * @param syslog
	 * @return
	 */
	ResultMap verify(SysLog syslog);
}
