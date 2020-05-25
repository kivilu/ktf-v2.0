package com.kivi.framework.util.kit;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.vip.vjtools.vjkit.number.NumberUtil;

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

	public static boolean equals(Integer val1, Integer val2) {
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

	public static Integer toInt(Object obj) {
		if (obj == null || StringUtils.isBlank(obj.toString()))
			return null;
		return NumberUtil.toInt(obj.toString(), -1);
	}

	public static Long toLong(Object obj) {
		if (obj == null || StringUtils.isBlank(obj.toString()))
			return null;
		return NumberUtil.toLong(obj.toString(), -1);
	}

}
