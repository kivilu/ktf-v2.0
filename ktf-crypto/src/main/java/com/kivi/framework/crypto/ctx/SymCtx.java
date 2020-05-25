package com.kivi.framework.crypto.ctx;

import java.io.Serializable;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;

/**
 * 对称算法CTX
 * 
 * @author Eric
 *
 */
@Builder
@Getter
public class SymCtx implements Serializable {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Default
	private int					blockSize			= 16;

	/**
	 * CTX唯一标识
	 */
	String						uuid;

	/**
	 * 算法标识
	 */
	private String				alg;

	/**
	 * cbc标识
	 */
	private boolean				cbc;

	/**
	 * 密钥
	 */
	private byte[]				key;

	/**
	 * 初始向量
	 */
	private byte[]				iv;

	/**
	 * 3DES标识
	 */
	private boolean				triple;

	/**
	 * 0填充标识
	 */
	private boolean				zeroPadding;

	/**
	 * 密钥对象
	 */
	private Object				secretKey;

}
