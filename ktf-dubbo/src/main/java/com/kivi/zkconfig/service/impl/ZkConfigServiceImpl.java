package com.kivi.zkconfig.service.impl;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.dubbo.common.utils.ConcurrentHashSet;

import com.kivi.framework.converter.BeanConverter;
import com.kivi.zkconfig.service.ZkConfigService;
import com.kivi.zkconfig.service.ZkConfigServiceCallback;
import com.kivi.zkconfig.util.ZkMap;
import com.vip.vjtools.vjkit.number.NumberUtil;

public class ZkConfigServiceImpl implements ZkConfigService {
	private final ZkMap											zkmap;
	private static ConcurrentMap<String, Object>				localMap		= new ConcurrentHashMap<>();

	private static ConcurrentHashSet<ZkConfigServiceCallback>	callbackHashSet	= new ConcurrentHashSet<>();

	public ZkConfigServiceImpl() {
		this.zkmap = ZkMap.newMap("127.0.0.1:2181", "/ktf/zkconfig");
	}

	public ZkConfigServiceImpl(String connectStr, String root) {
		this.zkmap = ZkMap.newMap(connectStr, root);
	}

	public ZkConfigServiceImpl(CuratorFramework client, String root) {
		this.zkmap = ZkMap.newMap(client, root);
	}

	@Override
	public Object get(String key) {
		if (key == null)
			return null;

		Object result = localMap.get(key);
		if (result == null) {
			result = zkmap.get(key);
			if (result != null)
				localMap.put(key, result);
		}
		return result;
	}

	@Override
	public Object put(String key, Object value) {
		localMap.put(key, value);
		return zkmap.put(key, value);
	}

	@Override
	public Object remove(String key) {
		removeLocal(key);
		return zkmap.remove(key);
	}

	@Override
	public Integer getInt(String key) {
		return NumberUtil.toInt(getStr(key));
	}

	@Override
	public String getStr(String key) {
		Object obj = get(key);
		if (obj == null)
			return null;
		return BeanConverter.convert(String.class, obj);
	}

	@Override
	public <T> T get(String key, Class<T> clazz) {
		Object obj = get(key);

		return BeanConverter.convert(clazz, obj);
	}

	@Override
	public Object sync(String key) {
		if (key == null)
			return null;
		Object value = zkmap.get(key);
		if (value == null) {
			return null;
		}
		localMap.put(key, value);

		callbackHashSet.stream().forEach(callback -> {
			callback.update(key, value);
		});

		return value;
	}

	@Override
	public Object removeLocal(String key) {
		callbackHashSet.stream().forEach(callback -> {
			callback.evict(key);
		});
		return localMap.remove(key);
	}

	@Override
	public List<String> keys() {
		return zkmap.keySet().stream().collect(Collectors.toList());
	}

	@Override
	public List<Object> values() {
		return zkmap.values().stream().collect(Collectors.toList());
	}

	@Override
	public void zkConfigServiceCallback(ZkConfigServiceCallback callback) {
		callbackHashSet.add(callback);
	}

}
