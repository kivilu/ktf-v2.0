
package com.kivi.db.model.convert;

import java.io.Serializable;

import com.kivi.framework.converter.BeanConverter;

/**
 * <p>
 * 普通实体父类
 * </p>
 *
 */
public class Convert implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 获取自动转换后的JavaBean对象
	 *
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public <T> T convert(Class<T> clazz) {
		return BeanConverter.convert(clazz, this);
	}
}
