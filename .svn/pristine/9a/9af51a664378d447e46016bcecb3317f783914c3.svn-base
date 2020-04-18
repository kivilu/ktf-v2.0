package com.kivi.framework.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class JwtUserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	// 用户iD
	@Builder.Default
	protected Long				id					= 0L;

	// 用户标识
	@Builder.Default
	protected String			identifier			= "";

	// 用户姓名
	@Builder.Default
	protected String			name				= "---";

	// 用户类型
	protected Integer			userType;

}
