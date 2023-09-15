package com.kivi.sys.sys.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kivi.cif.auth.CifAuthentication;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.util.kit.CollectionKit;
import com.kivi.sys.sys.dto.SysLogDTO;
import com.kivi.sys.sys.service.SysLogSignService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SysLogSignServiceImpl implements SysLogSignService {

    // @Autowired
    // KmsProperties kmsProperties;
    @Autowired
    CifAuthentication cifAuthentication;

    @Override
    public String sign(SysLogDTO sysLog, String... datas) {
        // 模拟代码，后续添加实际签名代码
        StringBuilder builder = new StringBuilder();
        builder.append(sysLog.getLoginName()).append(sysLog.getUserName()).append(sysLog.getUserType())
            .append(sysLog.getOperation()).append(sysLog.getGmtCreate());
        if (CollectionKit.isNotEmpty(datas))
            builder.append(StringUtils.join(datas));

        String sign = DigestUtils.md5Hex(builder.toString());
        log.trace("=====签名明文：{}，签名结果：{}", builder.toString(), sign);

        return sign;
    }

    @Override
    public ResultMap verify(SysLogDTO syslog) {
        String signInfo = syslog.getOperationSign();

        String plainData =
            syslog.getParams().substring(syslog.getParams().lastIndexOf(":") + 1, syslog.getParams().lastIndexOf("}"));

        try {
            cifAuthentication.verify(null, syslog.getLoginName(), plainData, signInfo);
        } catch (KtfException e) {
            log.error("审计验证签名异常", e);
            return ResultMap.error(e.getCode(), e.getTips());
        } catch (Exception e) {
            log.error("审计验证签名异常", e);
            return ResultMap.error("审计验证签名异常");
        }

        return ResultMap.ok("审计验证签名通过");
    }

    @Override
    public String sign(String... datas) {
        // TODO Auto-generated method stub
        return null;
    }

}
