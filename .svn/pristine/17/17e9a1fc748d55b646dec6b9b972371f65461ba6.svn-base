package com.kivi.framework.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.kivi.framework.exception.KtfException;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel( value = "KtfBaseRsp", description = "接口响应基础Bean" )
public class KtfBaseRsp<T> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(
                       position = 1,
                       value = "接口版本 ",
                       required = true,
                       dataType = "String",
                       notes = "接口版本 ",
                       example = "1.0.0" )
    @NotEmpty
    @Size( max = 8, message = "版本号长度最大为8" )
    private String            version          = "1.0.0";

    @ApiModelProperty(
                       position = 2,
                       value = "服务器业务流水号 ",
                       required = false,
                       dataType = "String",
                       notes = "服务器业务流水号",
                       example = "" )
    @Size( max = 30, message = "服务器业务流水号" )
    private String            bizSeqId;

    @ApiModelProperty(
                       position = 3,
                       value = "请求响应码，200：成功；其他：失败",
                       required = true,
                       dataType = "String",
                       notes = "请求响应码，200：成功；其他：失败",
                       example = "200" )
    private int               rspCode;

    @ApiModelProperty(
                       position = 4,
                       value = "请求响应码说明",
                       required = true,
                       dataType = "String",
                       notes = "请求响应码说明",
                       example = "成功" )
    @NotEmpty
    @Size( max = 128, message = "请求响应码说明长度最大为128" )
    private String            rspDesc;

    @ApiModelProperty(
                       position = 5,
                       value = "响应数据体",
                       required = false,
                       dataType = "String",
                       notes = "响应数据体",
                       example = "" )
    private T                 rspBody;

    public void setException( KtfException e ) {
        this.rspCode = e.getCode();
        this.rspDesc = e.getTips();
    }

}
