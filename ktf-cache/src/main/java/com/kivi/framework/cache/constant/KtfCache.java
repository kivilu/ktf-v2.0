package com.kivi.framework.cache.constant;

public interface KtfCache {

    // KTF TOKEN
    public final static String KTF_TOKEN = "ktf.token";

    /**
     * CifCustomer缓存
     */
    public final static String CifCustomer = "ktf.CifCustomer";

    /**
     * CifCerts缓存
     */
    public final static String CifCerts = "ktf.CifCerts";

    /**
     * CifCustomerAuths缓存
     */
    public final static String CifCustomerAuths = "ktf.CifCustomerAuths";

    /**
     * SysDic缓存
     */
    public final static String SysDic = "ktf.SysDic";

    /**
     * SysResource缓存
     */
    public final static String SysResource = "ktf.SysResource";

    /**
     * SysUser 缓存
     */
    public final static String SysUser = "ktf.SysUser";

    /**
     * SysRole 缓存
     */
    public final static String SysRole = "ktf.SysRole";

    /**
     * Shiro用户角色及权限信息缓存
     */
    public final static String ShiroAuthentication = "Authentication";

    /**
     * 缓存名数组
     */
    public final static String[] KTF_CACHE_NAMES = {KTF_TOKEN};

}
