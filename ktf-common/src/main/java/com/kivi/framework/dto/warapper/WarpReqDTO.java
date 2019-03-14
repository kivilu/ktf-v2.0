package com.kivi.framework.dto.warapper;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 请求包装类
 * 
 * @author Eric
 *
 */
@Setter
@Getter
@ApiModel( value = "WarpperReqDTO", description = "DTO wapper" )
public class WarpReqDTO<T> implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public WarpReqDTO() {
        this(null, null);
    }

    public WarpReqDTO( Long tranUniqueId ) {
        this(tranUniqueId, null);
    }

    public WarpReqDTO( Long tranUniqueId, T reqObject ) {
        this.tranUniqueId = tranUniqueId;
        this.reqObject = reqObject;
    }

    /**
     * 客户端IP
     */
    @ApiModelProperty(
                       position = 1,
                       value = "客户端IP",
                       dataType = "String",
                       example = "127.0.0.1" )
    private String clientIp;

    /**
     * 调用发起应用名称
     */
    @ApiModelProperty(
                       position = 2,
                       value = "调用发起应用名称",
                       dataType = "String",
                       example = "" )
    private String fromAppName;

    /**
     * 交易流水号
     */
    @ApiModelProperty(
                       position = 3,
                       value = "交易流水号",
                       dataType = "String",
                       example = "" )
    private String tranSeqId;

    /**
     * 交易流水号
     */
    @ApiModelProperty( hidden = true )
    private Long   tranUniqueId;

    /**
     * 请求内容
     */
    @ApiModelProperty(
                       position = 4,
                       value = "请求内容",
                       dataType = "Object" )
    private T      reqObject;

}
