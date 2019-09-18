package com.kivi.dashboard.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_sys_log")
public class SysLog extends Model<SysLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 登陆名
     */
    @TableField("login_name")
    private String loginName;
    /**
     * 角色名
     */
    @TableField("role_name")
    private String roleName;
    /**
     * 日志类型（0:系统日志，1：操作日志）
     */
    @TableField("type")
    private Integer type;
    /**
     * 用户操作
     */
    @TableField("operation")
    private String operation;
    /**
     * 类名
     */
    @TableField("class_name")
    private String className;
    /**
     * 请求方法
     */
    @TableField("method")
    private String method;
    /**
     * 请求参数
     */
    @TableField("params")
    private String params;
    /**
     * 执行时长
     */
    @TableField("time")
    private Long time;
    /**
     * 客户端ip
     */
    @TableField("client_ip")
    private String clientIp;
    /**
     * 创建时间
     */
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;


    public static final String DB_ID = "id";
	public static final String ID = "id";
    public static final String DB_LOGIN_NAME = "login_name";
	public static final String LOGIN_NAME = "loginName";
    public static final String DB_ROLE_NAME = "role_name";
	public static final String ROLE_NAME = "roleName";
    public static final String DB_TYPE = "type";
	public static final String TYPE = "type";
    public static final String DB_OPERATION = "operation";
	public static final String OPERATION = "operation";
    public static final String DB_CLASS_NAME = "class_name";
	public static final String CLASS_NAME = "className";
    public static final String DB_METHOD = "method";
	public static final String METHOD = "method";
    public static final String DB_PARAMS = "params";
	public static final String PARAMS = "params";
    public static final String DB_TIME = "time";
	public static final String TIME = "time";
    public static final String DB_CLIENT_IP = "client_ip";
	public static final String CLIENT_IP = "clientIp";
    public static final String DB_GMT_CREATE = "gmt_create";
	public static final String GMT_CREATE = "gmtCreate";
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
