package com.kivi.framework.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class KtfDTO<T> implements Serializable {
	/**
	* 
	*/
	private static final long	serialVersionUID	= 1L;

	protected Long				ktfId;

	protected T					reqObject;

	public KtfDTO() {
		this(null, null);
	}

	public KtfDTO(Long id) {
		this(id, null);
	}

	public KtfDTO(Long id, T reqObject) {
		this.ktfId		= id;
		this.reqObject	= reqObject;
	}
}
