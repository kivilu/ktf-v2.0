package com.kivi.zkconfig.service;

public interface ZkConfigServiceCallback {
	/**
	 * 名称
	 * 
	 * @return
	 */
	String name();

	/**
	 * 更新配置信息
	 * 
	 * @param key
	 * @param value
	 */
	void update(String key, Object value);

	/**
	 * 删除配置信息
	 * 
	 * @param key
	 */
	void evict(String key);
}
