package com.kivi.framework.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.kivi.framework.util.kit.CollectionKit;
import com.kivi.framework.util.kit.DateTimeKit;
import com.kivi.framework.util.kit.ObjectKit;

import lombok.extern.slf4j.Slf4j;

/**
 * 高频方法集合类
 */
@Slf4j
@Deprecated
public class ToolUtil {

	/**
	 * 获取异常的具体信息
	 *
	 */
	public static String getExceptionMsg(Exception e) {
		StringWriter	sw		= new StringWriter();
		BufferedReader	reader	= null;
		StringBuilder	result	= new StringBuilder();
		try {
			e.printStackTrace(new PrintWriter(sw));

			reader = new BufferedReader(
					new InputStreamReader(new ByteArrayInputStream(sw.getBuffer().toString().getBytes("UTF-8"))));

			result.append(reader.readLine()).append("\r\n").append(reader.readLine());
		} catch (Exception e1) {
			log.error("获取异常信息失败", e1);
		} finally {
			try {
				sw.close();
				if (reader != null)
					reader.close();
			} catch (IOException e1) {
				log.error("关闭StringWriter异常", e1);
			}
		}

		return result.toString().replaceAll("\\$", "T");
	}

	/**
	 * @Description 主键id
	 */
	public static String getUid() {
		return getRandomNum();
	}

	/**
	 * @Description 随机数字
	 */
	public static String getRandomNum() {
		return Calendar.getInstance().getTimeInMillis() + generateCellPhoneValNum();
	}

	public static String getRandomNum(int size) {
		String[]		beforeShuffle	= new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };

		List<String>	list			= CollectionKit.newArrayList();
		for (int i = 0; i < size; i++)
			list.add(beforeShuffle[i % beforeShuffle.length]);
		Collections.shuffle(list);

		return CollectionKit.join(list, "");
	}

	/**
	 * @Description 获取电话号码
	 */
	public static String generateCellPhoneValNum() {
		String[]		beforeShuffle	= new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
		List<String>	list			= Arrays.asList(beforeShuffle);
		Collections.shuffle(list);
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			buffer.append(list.get(i));
		}
		String	afterShuffle	= buffer.toString();
		String	result			= afterShuffle.substring(3, 9);
		return result;
	}

	/**
	 * map的key转为小写
	 *
	 * @param map
	 * @return Map<String,Object>
	 */
	public static Map<String, Object> caseInsensitiveMap(Map<String, Object> map) {
		Map<String, Object> tempMap = new HashMap<>();
		for (String key : map.keySet()) {
			tempMap.put(key.toLowerCase(), map.get(key));
		}
		return tempMap;
	}

	/**
	 * 获取map中第一个数据值
	 *
	 * @param <K> Key的类型
	 * @param <V> Value的类型
	 * @param map 数据源
	 * @return 返回的值
	 */
	public static <K, V> V getFirstOrNull(Map<K, V> map) {
		V obj = null;
		for (Entry<K, V> entry : map.entrySet()) {
			obj = entry.getValue();
			if (obj != null) {
				break;
			}
		}
		return obj;
	}

	/**
	 * 判断是否是windows操作系统
	 *
	 */
	public static Boolean isWinOs() {
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否是Linux操作系统
	 *
	 */
	public static boolean isLinux() {
		String os = System.getProperty("os.name");
		return os.indexOf("linux") >= 0;
	}

	/**
	 * 获取临时目录
	 *
	 */
	public static String getTempPath() {
		return System.getProperty("java.io.tmpdir");
	}

	public static boolean isEmpty(Object o) {
		return ObjectKit.isEmpty(o);
	}

	/**
	 * 当前时间，格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 当前时间的标准形式字符串
	 */
	public static String currentTime() {
		return DateTimeKit.now();
	}

	public static String dateType(Object o) {
		if (o instanceof Date) {
			return DateTimeKit.formatDate((Date) o);
		} else {
			return o.toString();
		}
	}

}
