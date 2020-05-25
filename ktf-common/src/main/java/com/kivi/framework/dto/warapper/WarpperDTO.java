package com.kivi.framework.dto.warapper;

import java.io.Serializable;

import com.kivi.framework.dto.JwtUserDTO;

import lombok.Data;

/**
 * DTO包装类
 * 
 * @author Eric
 *
 */
@Data
public class WarpperDTO<T, K> implements Serializable {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * 客户端IP
	 */
	private String				clientIp;

	/**
	 * 调用发起应用名称
	 */
	private String				fromAppName;

	/**
	 * 交易代码
	 */
	private String				tranCode;

	/**
	 * 异步响应对象
	 */
	private K					deferred;

	/**
	 * 参数
	 */
	private Object[]			params;

	/**
	 * DTO
	 */
	private T					dto;

	private JwtUserDTO			jwtUser;

	public WarpperDTO(T t, K k) {
		this.dto		= t;
		this.deferred	= k;
	}

	public WarpperDTO() {
		this(null, null);
	}

}
