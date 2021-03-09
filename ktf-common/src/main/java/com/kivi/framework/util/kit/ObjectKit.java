package com.kivi.framework.util.kit;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.vip.vjtools.vjkit.collection.CollectionUtil;
import com.vip.vjtools.vjkit.collection.ListUtil;
import com.vip.vjtools.vjkit.collection.MapUtil;

/**
 * Object通用的函数
 * 
 */
public class ObjectKit {

	/**
	 * 比较两个对象是否相等。<br>
	 * 相同的条件有两个，满足其一即可：<br>
	 * 1. obj1 == null && obj2 == null; 2. obj1.equals(obj2)
	 *
	 * @param obj1 对象1
	 * @param obj2 对象2
	 * @return 是否相等
	 */
	public static boolean equals(Object obj1, Object obj2) {
		return (obj1 != null) ? (obj1.equals(obj2)) : (obj2 == null);
	}

	public static boolean equalsNotNull(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null)
			return false;

		return obj1.equals(obj2);
	}

	/**
	 * 计算对象长度，如果是字符串调用其length函数，集合类调用其size函数，数组调用其length属性，其他可遍历对象遍历计算长度
	 *
	 * @param obj 被计算长度的对象
	 * @return 长度
	 */
	public static int length(Object obj) {
		if (obj == null) {
			return 0;
		}
		if (obj instanceof CharSequence) {
			return ((CharSequence) obj).length();
		}
		if (obj instanceof Collection) {
			return ((Collection<?>) obj).size();
		}
		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).size();
		}

		int count;
		if (obj instanceof Iterator) {
			Iterator<?> iter = (Iterator<?>) obj;
			count = 0;
			while (iter.hasNext()) {
				count++;
				iter.next();
			}
			return count;
		}
		if (obj instanceof Enumeration) {
			Enumeration<?> enumeration = (Enumeration<?>) obj;
			count = 0;
			while (enumeration.hasMoreElements()) {
				count++;
				enumeration.nextElement();
			}
			return count;
		}
		if (obj.getClass().isArray() == true) {
			return Array.getLength(obj);
		}
		return -1;
	}

	/**
	 * 对象中是否包含元素
	 *
	 * @param obj     对象
	 * @param element 元素
	 * @return 是否包含
	 */
	public static boolean contains(Object obj, Object element) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof String) {
			if (element == null) {
				return false;
			}
			return ((String) obj).contains(element.toString());
		}
		if (obj instanceof Collection) {
			return ((Collection<?>) obj).contains(element);
		}
		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).values().contains(element);
		}

		if (obj instanceof Iterator) {
			Iterator<?> iter = (Iterator<?>) obj;
			while (iter.hasNext()) {
				Object o = iter.next();
				if (equals(o, element)) {
					return true;
				}
			}
			return false;
		}
		if (obj instanceof Enumeration) {
			Enumeration<?> enumeration = (Enumeration<?>) obj;
			while (enumeration.hasMoreElements()) {
				Object o = enumeration.nextElement();
				if (equals(o, element)) {
					return true;
				}
			}
			return false;
		}
		if (obj.getClass().isArray() == true) {
			int len = Array.getLength(obj);
			for (int i = 0; i < len; i++) {
				Object o = Array.get(obj, i);
				if (equals(o, element)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 对象是否不为空(新增)
	 *
	 * @param obj String,List,Map,Object[],int[],long[]
	 * @return
	 */
	public static boolean isNotEmpty(Object o) {
		return !isEmpty(o);
	}

	/**
	 * 对象是否为空
	 *
	 * @param obj String,List,Map,Object[],int[],long[]
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		}
		if (o instanceof String) {
			return StringUtils.isBlank((String) o);
		} else if (o instanceof List) {
			return ListUtil.isEmpty((List) o);
		} else if (o instanceof Map) {
			return MapUtil.isEmpty((Map) o);
		} else if (o instanceof Set) {
			return CollectionUtil.isEmpty((Set) o);
		} else if (o instanceof Object[]) {
			if (((Object[]) o).length == 0) {
				return true;
			}
		} else if (o instanceof int[]) {
			if (((int[]) o).length == 0) {
				return true;
			}
		} else if (o instanceof long[]) {
			if (((long[]) o).length == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 对象组中是否存在 null Object
	 *
	 * @param os 对象组
	 * @return
	 */
	public static boolean isAnyNull(Object... os) {
		for (Object o : os) {
			if (o == null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 对象组中是否存在 Empty Object
	 *
	 * @param os 对象组
	 * @return
	 */
	public static boolean isOneEmpty(Object... os) {
		for (Object o : os) {
			if (isEmpty(o)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 对象组中是否全是 Empty Object
	 *
	 * @param os
	 * @return
	 */
	public static boolean isAllEmpty(Object... os) {
		for (Object o : os) {
			if (!isEmpty(o)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 如果为空, 则调用默认值
	 *
	 * @param str
	 * @return
	 */
	public static Object getValue(Object str, Object defaultValue) {
		if (isEmpty(str)) {
			return defaultValue;
		}
		return str;
	}

	/**
	 * 判断一个对象是否是一个数组
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isArray(Object obj) {
		if (obj == null) {
			return false;
		}

		return obj.getClass().isArray() || obj instanceof List;
	}

}
