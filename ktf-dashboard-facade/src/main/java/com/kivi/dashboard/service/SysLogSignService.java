package com.kivi.dashboard.service;

import com.kivi.dashboard.sys.entity.SysLog;
import com.kivi.framework.model.ResultMap;

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
