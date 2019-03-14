package com.kivi.framework.constant;

/**
 * 公共错误码定义类，其他业务模块的错误码需继承此类后，添加各自模块的本身的错误码
 * 
 * @author Eric
 *
 */
public class KtfError {
    /** 请求处理中 */
    public static final int PROCESSING            = 102;

    /** 成功 */
    public static final int SUCCESS               = 200;

    /** 请求已受理 */
    public static final int ACCEPTED              = 202;

    /** 请求参数有误 */
    public static final int E_BAD_REQUEST         = 400;

    /** 请求需要用户验证 */
    public static final int E_UNAUTHORIZED        = 401;

    /** 权限不足 */
    public static final int E_FORBIDDEN           = 403;

    /** 记录找不到 */
    public static final int E_NOT_FOUND           = 404;

    /** 请求超时 */
    public static final int E_REQUEST_TIMEOUT     = 408;

    /** 记录重复 */
    public static final int E_CONFLICT            = 409;

    /** 记录不存在 */
    public static final int E_GONE                = 410;

    /** 超过限制大小 */
    public static final int E_TOO_LARGE           = 413;

    /** 资源被锁定 */
    public static final int E_LOCKED              = 423;

    /** 未知定义错误 */
    public static final int E_UNDEFINED           = 500;

    /** 未实现 */
    public static final int E_NOT_IMPLEMENT       = 501;

    /** 访问外部资源失败 */
    public static final int E_BAD_GATEWAY         = 502;

    /** 服务器不可用 */
    public static final int E_SERVICE_UNAVAILABLE = 503;

    /** 访问外部资源超时 */
    public static final int E_GATEWAY_TIMEOUT     = 504;

    /** 加密处理异常 */
    public static final int E_CRYPTO              = 505;

    /** NULL异常 */
    public static final int E_NULL                = 506;

    /** RPC调用异常 */
    public static final int E_RPC_FAIL            = 507;

}
