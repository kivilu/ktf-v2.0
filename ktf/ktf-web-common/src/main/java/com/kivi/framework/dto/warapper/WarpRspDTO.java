package com.kivi.framework.dto.warapper;

import java.io.Serializable;

import com.kivi.framework.dto.JwtUserDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WarpRspDTO<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WarpRspDTO() {
		this(null, null);
	}

	public WarpRspDTO(Long tranUniqueId) {
		this(tranUniqueId, null);
	}

	public WarpRspDTO(Long tranUniqueId, T rspObject) {
		this.tranUniqueId	= tranUniqueId;
		this.rspObject		= rspObject;
	}

	/**
	 * 调用响应应用名称
	 */
	private String		fromAppName;

	/**
	 * 交易流水号
	 */
	private String		tranSeqId;

	/**
	 * 交易唯一序列号
	 */
	private Long		tranUniqueId;

	/**
	 * 参数
	 */
	private Object[]	params;

	/**
	 * 响应内容
	 */
	private T			rspObject;

	@ApiModelProperty(hidden = true)
	JwtUserDTO			jwtUser;
}
