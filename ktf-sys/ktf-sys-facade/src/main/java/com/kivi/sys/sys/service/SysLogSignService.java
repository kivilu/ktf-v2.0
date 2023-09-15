package com.kivi.sys.sys.service;

import com.kivi.framework.model.ResultMap;
import com.kivi.sys.sys.dto.SysLogDTO;

public interface SysLogSignService {

    /**
     * 日志签名
     * 
     * @param sysLog
     * @return
     */
    String sign(SysLogDTO syslog, String... datas);

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
    ResultMap verify(SysLogDTO syslog);
}
