package com.kivi.framework.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.kivi.framework.constant.KtfError;
import com.kivi.framework.exception.KtfException;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel(value = "KtfBaseRsp", description = "接口响应基础Bean")
public class KtfBaseRsp<T> implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(
			position = 1,
			value = "接口版本 ",
			required = true,
			dataType = "String",
			notes = "接口版本 ",
			example = "1.0.0")
	@NotEmpty
	@Size(max = 8, message = "版本号长度最大为8")
	private String				version				= "1.0.0";

	@ApiModelProperty(
			position = 2,
			value = "业务请求流水号 ",
			required = true,
			dataType = "String",
			notes = "业务请求流水号 ",
			example = "")
	private String				oriBizSeqId;

	@ApiModelProperty(
			position = 3,
			value = "请求响应码，200：成功；其他：失败",
			required = true,
			dataType = "String",
			notes = "请求响应码，200：成功；其他：失败",
			example = "200")
	private int					code;

	@ApiModelProperty(
			position = 4,
			value = "请求响应码说明",
			required = true,
			dataType = "String",
			notes = "请求响应码说明",
			example = "成功")
	@NotEmpty
	@Size(max = 128, message = "请求响应码说明长度最大为128")
	private String				msg;

	@ApiModelProperty(
			position = 5,
			value = "响应数据体",
			required = false,
			dataType = "String",
			notes = "响应数据体",
			example = "")
	private T					data;

	public KtfBaseRsp() {
		this.code	= KtfError.SUCCESS;
		this.msg	= "success";
	}

	public static <T> KtfBaseRsp<T> ok() {
		return ok(null, null);
	}

	public static <T> KtfBaseRsp<T> ok(KtfBaseReq<?> req) {
		return ok(null, req);
	}

	public static <T> KtfBaseRsp<T> ok(String msg) {
		return ok(msg, null);
	}

	public static <T> KtfBaseRsp<T> ok(String msg, KtfBaseReq<?> req) {
		KtfBaseRsp<T> r = new KtfBaseRsp<>();

		if (msg != null)
			r.msg = msg;

		if (req != null) {
			r.version		= req.getVersion();
			r.oriBizSeqId	= req.getBizSeqId();
		}

		return r;
	}

	public static <T> KtfBaseRsp<T> error(int code, String msg, KtfBaseReq<?> req) {
		KtfBaseRsp<T> r = new KtfBaseRsp<>();
		r.code	= code;
		r.msg	= msg;
		if (req != null) {
			r.version		= req.getVersion();
			r.oriBizSeqId	= req.getBizSeqId();
		}

		return r;
	}

	public static <T> KtfBaseRsp<T> error(int code, String msg) {
		return error(code, msg, null);
	}

	public static <T> KtfBaseRsp<T> error(String msg) {
		return error(KtfError.E_UNDEFINED, msg, null);
	}

	public static <T> KtfBaseRsp<T> error(String msg, KtfBaseReq<?> req) {
		return error(KtfError.E_UNDEFINED, msg, req);
	}

	public static <T> KtfBaseRsp<T> error(KtfException e) {
		return error(e.getCode(), e.getTips(), null);
	}

	public static <T> KtfBaseRsp<T> error(KtfException e, KtfBaseReq<?> req) {
		return error(e.getCode(), e.getTips(), req);
	}

	public static <T> KtfBaseRsp<T> error() {
		return error(KtfError.E_UNDEFINED, "未知异常，请联系管理员", null);
	}

	public static <T> KtfBaseRsp<T> error(KtfBaseReq<?> req) {
		return error(KtfError.E_UNDEFINED, "未知异常，请联系管理员", req);
	}

}
