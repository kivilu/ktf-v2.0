package com.kivi.zkconfig.service;

import java.util.List;

/**
 * zookeeper配置信息服务
 * 
 * @author Eric
 *
 */
public interface ZkConfigService {

	/**
	 * 获取配置信息
	 * 
	 * @param key
	 * @return
	 */
	Object get(String key);

	/**
	 * 获取Integer配置信息
	 * 
	 * @param key
	 * @return
	 */
	Integer getInt(String key);

	/**
	 * 获取String配置信息
	 * 
	 * @param key
	 * @return
	 */
	String getStr(String key);

	/**
	 * 获取指定类型的配置信息
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	<T> T get(String key, Class<T> clazz);

	/**
	 * 保存配置信息
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	Object put(String key, Object value);

	/**
	 * 同步配置信息
	 * 
	 * @param key
	 * @return
	 */
	Object sync(String key);

	/**
	 * 删除配置信息
	 * 
	 * @param key
	 * @return
	 */
	Object remove(String key);

	/**
	 * 删除本地配置信息
	 * 
	 * @param key
	 * @return
	 */
	Object removeLocal(String key);

	/**
	 * 列举Zookeeper保存的全部配置key
	 * 
	 * @return
	 */
	List<String> keys();

	/**
	 * 列举Zookeeper保存的全部配置Value
	 * 
	 * @return
	 */
	List<Object> values();

	/**
	 * 添加添加回调接口
	 * 
	 * @param callback
	 */
	void zkConfigServiceCallback(ZkConfigServiceCallback callback);

}
