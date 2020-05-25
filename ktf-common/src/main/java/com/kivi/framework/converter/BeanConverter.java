/*
 * Copyright (c) 2019-2022 KIVI.
 *
 * Permission is Eric granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.kivi.framework.converter;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;

import com.google.common.collect.Maps;
import com.kivi.framework.converter.modelmapper.jdk8.Jdk8Module;
import com.kivi.framework.converter.modelmapper.jsr310.Jsr310Module;
import com.kivi.framework.converter.modelmapper.jsr310.Jsr310ModuleConfig;
import com.kivi.framework.util.kit.ClassKit;
import com.vip.vjtools.vjkit.collection.ListUtil;

/**
 * 转换工具类
 *
 * @author Eric
 */
public class BeanConverter {

	private static final ModelMapper			modelMapper;

	// 创建自定义转换规则
	public static Converter<Long, String>		long2String		= new AbstractConverter<Long, String>() {
																	@Override
																	protected String convert(Long source) {
																		return source == null ? null
																				: source.toString();
																	}
																};

	public static Converter<Integer, String>	integer2String	= new AbstractConverter<Integer, String>() {
																	@Override
																	protected String convert(Integer source) {
																		return source == null ? null
																				: source.toString();
																	}
																};

	static {
		modelMapper = new ModelMapper();
		Jsr310ModuleConfig config = Jsr310ModuleConfig.builder().dateTimePattern("yyyy-MM-dd HH:mm:ss") // default is
																										// yyyy-MM-dd
																										// HH:mm:ss
				.datePattern("yyyy-MM-dd") // default is yyyy-MM-dd
				.zoneId(ZoneOffset.UTC) // default is ZoneId.systemDefault()
				.build();
		modelMapper.registerModule(new Jsr310Module(config)).registerModule(new Jdk8Module());
		modelMapper.getConfiguration().setFullTypeMatchingRequired(true).setMatchingStrategy(MatchingStrategies.STRICT)
				.setPropertyCondition(Conditions.isNotNull());
	}

	static final Map<String, BeanCopier> BEAN_COPIERS = new HashMap<String, BeanCopier>();

	/**
	 * 属性拷贝
	 * 
	 * @param srcObj
	 * @param destObj
	 */
	public static void copyProperties(Object source, Object target) {
		BeanUtils.copyProperties(source, target);
	}

	/**
	 * 获取 modelMapper
	 *
	 * @return
	 */
	public static ModelMapper getModelMapper() {
		return modelMapper;
	}

	/**
	 * Bean转换为Map
	 *
	 * @param bean
	 * @param <T>
	 * @return
	 */
	public static <T> Map<String, Object> beanToMap(T bean) {
		Map<String, Object> map = Collections.emptyMap();
		if (null != bean) {
			BeanMap beanMap = BeanMap.create(bean);
			map = new HashMap<>(beanMap.keySet().size());
			for (Object key : beanMap.keySet()) {
				map.put(String.valueOf(key), beanMap.get(key));
			}
			map = Maps.filterValues(map, value -> value != null);
		}
		return map;
	}

	/**
	 * List<E>转换为List<Map<String, Object>>
	 *
	 * @param objList
	 * @param <T>
	 * @return
	 */
	public static <T> List<Map<String, Object>> beansToMap(List<T> objList) {
		List<Map<String, Object>> list = Collections.emptyList();
		if (ListUtil.isNotEmpty(objList)) {
			list = new ArrayList<>(objList.size());
			Map<String, Object>	map;
			T					bean;
			for (T anObjList : objList) {
				bean	= anObjList;
				map		= beanToMap(bean);
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * map转为bean
	 *
	 * @param <T>       the type parameter
	 * @param mapList   the map list
	 * @param beanClass the bean class
	 * @return t list
	 */
	public static <T> List<T> mapToBean(List<Map<String, Object>> mapList, Class<T> beanClass) {
		List<T> list = Collections.emptyList();
		if (ListUtil.isNotEmpty(mapList)) {
			list = new ArrayList<>(mapList.size());
			Map<String, Object>	map;
			T					bean;
			for (Map<String, Object> map1 : mapList) {
				map		= map1;
				bean	= mapToBean(map, beanClass);
				list.add(bean);
			}
		}
		return list;
	}

	/**
	 * map转为bean
	 *
	 * @param map       the map
	 * @param beanClass the bean class
	 * @return t
	 */
	public static <T> T mapToBean(Map<String, Object> map, Class<T> beanClass) {
		T		entity	= ClassKit.newInstance(beanClass);
		BeanMap	beanMap	= BeanMap.create(entity);
		beanMap.putAll(map);
		return entity;
	}

	/**
	 * 列表转换
	 *
	 * @param clazz the clazz
	 * @param list  the list
	 */
	public static <T> List<T> convert(Class<T> clazz, List<?> list) {
		return ListUtil.isEmpty(list) ? Collections.emptyList()
				: list.stream().map(e -> convert(clazz, e)).collect(Collectors.toList());
	}

	/**
	 * 单个对象转换
	 *
	 * @param targetClass 目标对象
	 * @param source      源对象
	 * @return 转换后的目标对象
	 */
	public static <T> T convert(Class<T> targetClass, Object source) {
		if (source == null)
			return null;
		return getModelMapper().map(source, targetClass);
	}

	/**
	 * 列表转换
	 *
	 * @param clazz the clazz
	 * @param list  the list
	 */
	public static <T> List<T> convert(Class<T> clazz, List<?> list, Converter<?, ?>... converters) {
		return ListUtil.isEmpty(list) ? Collections.emptyList()
				: list.stream().map(e -> convert(clazz, e, converters)).collect(Collectors.toList());
	}

	/**
	 * 单个对象转换
	 * 
	 * @param <S>
	 *
	 * @param targetClass 目标对象
	 * @param source      源对象
	 * @return 转换后的目标对象
	 */
	public static <T> T convert(Class<T> targetClass, Object source, Converter<?, ?>... converters) {
		if (source == null)
			return null;
		ModelMapper mapper = getModelMapper();
		if (converters != null) {
			for (Converter<?, ?> converter : converters) {
				mapper.addConverter(converter);
			}
		}

		return mapper.map(source, targetClass);
	}

}
