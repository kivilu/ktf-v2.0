package com.kivi.sms.enums;

public enum SmsBizType {
    regist, // 验证码
    resetPwd, // 重置密码
    resetAuthPwd, // 重置授权密码
    unlockOTP, // 开锁口令
    unlockOnce, // 临时开锁口令
    grantLock // 锁授权
    ;
}
