package com.kivi.framework.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class KtfItem implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private String				name;

	private String				code;

	private String				clazz;

}
