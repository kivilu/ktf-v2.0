package com.kivi.sys.sys.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

@ApiModel(value = "SysLogDTO对象", description = "系统日志")
public class SysLogDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private final long startTime;

    public SysLogDTO() {
        this.startTime = System.currentTimeMillis();
    }

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String userName;;

    @ApiModelProperty(value = "登陆名")
    private String loginName;

    @ApiModelProperty(value = "用户类型")
    private Integer userType;

    @ApiModelProperty(value = "日志类型（0:系统日志，1：操作日志）")
    private Integer type;

    @ApiModelProperty(value = "用户操作")
    private String operation;

    @ApiModelProperty(value = "类名")
    private String className;

    @ApiModelProperty(value = "请求方法")
    private String method;

    @ApiModelProperty(value = "请求参数")
    private String params;

    @ApiModelProperty(value = "执行时长")
    private Long time;

    @ApiModelProperty(value = "客户端ip")
    private String clientIp;

    private String result;

    @ApiModelProperty(value = "操作签名")
    private String operationSign;

    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "企业")
    private Long enterpriseId;

    @ApiModelProperty(hidden = true)
    private long totalMillis;

    @ApiModelProperty(hidden = true)
    private String uri;

    @ApiModelProperty(hidden = true)
    private String recordIds;

    public static final String ID = "id";
    public static final String USER_NAME = "userName";
    public static final String LOGIN_NAME = "loginName";
    public static final String ROLE_NAME = "roleName";
    public static final String TYPE = "type";
    public static final String OPERATION = "operation";
    public static final String CLASS_NAME = "className";
    public static final String METHOD = "method";
    public static final String PARAMS = "params";
    public static final String TIME = "time";
    public static final String CLIENT_IP = "clientIp";
    public static final String RESULT = "result";
    public static final String OPERATION_SIGN = "operationSign";
    public static final String ENTERPRISE_ID = "enterpriseId";

}
