package com.kivi.framework.constant;

import java.nio.charset.Charset;

public class KtfConstant {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    /**
     * 接口文档的菜单名
     */
    public final static String API_MENU_NAME = "接口文档";

    /**
     * 属性：管理后台页面通知内容
     */
    public final static String ATTR_CONTENT = "content";

    /**
     * 属性：管理后台页面列表
     */
    public final static String ATTR_NOTICELIST = "noticeList";

    /**
     * 超级管理员ID
     */
    public static final long SUPER_ADMIN = 1L;

    /**
     * 默认应用ID
     */
    public static final long BZ_APPLICATION_ID = 1L;

    /**
     * 默认验证方式
     */
    public static final int DEFAUT_AUTH_TYPE = 9;

    /**
     * 默认最小页码
     */
    public static final long MIN_PAGE = 0;
    /**
     * 最大显示条数
     */
    public static final long MAX_LIMIT = 1000;
    /**
     * 默认页码
     */
    public static final long DEFAULT_PAGE = 1;
    /**
     * 默认显示条数
     */
    public static final long DEFAULT_LIMIT = 10;
    /**
     * 页码 KEY
     */
    public static final String PAGE_KEY = "page";
    /**
     * 显示条数 KEY
     */
    public static final String PAGE_LIMIT_KEY = "limit";
    /**
     * 排序字段 KEY
     */
    public static final String PAGE_SORT_KEY = "sort";
    /**
     * 排序方向 KEY
     */
    public static final String PAGE_ORDER_KEY = "order";

    public static final String URL_TIMESTAMP = "t";

    public static final String WEEKDAYS[] = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};

}
