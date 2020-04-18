package com.kivi.framework.util.kit;

import java.math.BigDecimal;

/**
 * 数字工具类
 *
 */
public class NumberKit {

	/**
	 * 比较两个null对象是否相等 equals(null, null) = false equals(null, 1L) = false equals(1L,
	 * 1L) = true equals(2L, 1L) = false
	 * 
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static boolean equals(Long val1, Long val2) {
		if (val1 == null || val2 == null)
			return false;

		return val1.compareTo(val2) == 0;
	}

	/**
	 * 判断对象是否为数字类型，Boolean认为是数字
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNumberic(Object obj) {
		if (obj == null)
			return false;

		if (obj instanceof Integer || obj instanceof Long || obj instanceof Boolean || obj instanceof BigDecimal
				|| obj instanceof Double) {
			return true;
		}

		return false;
	}

}
