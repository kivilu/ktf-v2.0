package com.kivi.framework.constant.enums;

/**
 * 数据库排序
 *
 */
public enum Order {

	ASC("asc"), DESC("desc");

	private String code;

	Order(String des) {
		this.setCode(des);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
}
