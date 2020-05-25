package com.kivi.framework.util.kit;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.kivi.framework.exception.KtfException;
import com.vip.vjtools.vjkit.collection.CollectionUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;

import lombok.Getter;

/**
 * 时间工具类
 */
public class DateTimeKit {

	public static final String						PATTERN_ISO_Z					= "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static final String						PATTERN_ISO_ON_SECOND_Z			= "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public final static String						PATTERN_ISO_ON_DATE_NS			= "yyyyMMdd";
	public final static String						PATTERN_TIME					= "HH:mm:ss";
	public final static String						PATTERN_DEFAULT_ON_MINUTE		= "yyyy-MM-dd HH:mm";
	public final static String						PATTERN_ISO_NS_Z				= "yyyyMMdd'T'HHmmssSSS'Z'";
	public final static String						PATTERN_ISO_ON_SECOND_NS_Z		= "yyyyMMdd'T'HHmmss'Z'";
	public final static String						PATTERN_DEFAULT_ON_SECOND_NS	= "yyyyMMddHHmmss";
	public final static String						PATTERN_HTTP_DATETIME			= "EEE, dd MMM yyyy HH:mm:ss z";

	public static Map<Integer, List<PatternEnum>>	PATTERS_MAP						= Arrays
			.asList(PatternEnum.values()).stream().collect(Collectors.toMap(PatternEnum::length, p -> {
																								List<PatternEnum> getPattersList = new ArrayList<>();
																								getPattersList.add(p);
																								return getPattersList;
																							},
					(List<PatternEnum> value1, List<PatternEnum> value2) -> {
						value1.addAll(value2);
						return value1;
					}));

	// -----------Date相关转换-----------
	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static Date now() {
		return DateTime.now().toDate();
	}

	public static String today(String... patterns) {
		return DateFormatUtil.formatDate(ObjectKit.isEmpty(patterns) ? PATTERN_ISO_ON_DATE_NS : patterns[0], now());
	}

	// ------------------unix time相关转换
	/**
	 * 当前unix时间戳
	 * 
	 * @return
	 */
	public static long currentUnix() {
		return OffsetDateTime.now().toEpochSecond();
	}

	/**
	 * unix时间戳--->Date
	 * 
	 * @param unixtime
	 * @return
	 */
	public static Date fromUixTime(long unixtime) {
		return Date.from(
				LocalDateTime.ofEpochSecond(unixtime, 0, ZoneOffset.UTC).atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * unix时间戳--->Date
	 * 
	 * @param unixtime
	 * @return
	 */
	public static long toUnixTime(Date date) {
		return toLocalDateTime(date).toEpochSecond(ZoneOffset.UTC);
	}

	// -----------LocalDateTime-----------
	/**
	 * 时间转换：Date ---> LocalDateTime
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDateTime toLocalDateTime(Date date) {
		if (date == null)
			return null;
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	/**
	 * 时间转换：LocalDateTime ---> Date
	 * 
	 * @param localDateTime
	 * @return
	 */
	public static Date toDate(LocalDateTime localDateTime) {
		if (localDateTime == null)
			return null;
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static long toMilliseconds(LocalDateTime ldate) {
		if (ldate == null)
			return 0;
		ZoneOffset zf = ldate.atZone(ZoneId.systemDefault()).getOffset();
		return ldate.toInstant(zf).toEpochMilli();
	}

	// -----------OffsetDateTime-----------
	/**
	 * 时间转换：Date ---> OffsetDateTime
	 * 
	 * @param date
	 * @return
	 */
	public static OffsetDateTime toOffsetDateTime(Date date) {
		return date.toInstant().atOffset(ZoneOffset.UTC);
	}

	// ----------format----------------------
	/**
	 * 格式化：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date) {
		return DateFormatUtil.formatDate(DateFormatUtil.PATTERN_DEFAULT_ON_SECOND, date);
	}

	/**
	 * 格式化： yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return DateFormatUtil.formatDate(DateFormatUtil.PATTERN_ISO_ON_DATE, date);
	}

	/**
	 * 格式化：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateTime(LocalDateTime date) {
		return date.format(DateTimeFormatter.ofPattern(DateFormatUtil.PATTERN_DEFAULT_ON_SECOND));
	}

	/**
	 * 格式化： yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(LocalDateTime date) {
		return date.format(DateTimeFormatter.ofPattern(DateFormatUtil.PATTERN_ISO_ON_DATE));
	}

	// ----------parse----------------------

	public static Date parse(final String dateStr) {
		if (StrKit.isEmpty(dateStr)) {
			return null;
		}

		String				sDate		= StringUtils.trim(dateStr);
		List<PatternEnum>	patterns	= PATTERS_MAP.get(sDate.length());
		if (CollectionUtil.isEmpty(patterns))
			throw new KtfException("日期格式不支持");

		Date date = null;

		try {
			if (patterns.size() == 1)
				date = DateFormatUtil.parseDate(patterns.get(0).getPattern(), dateStr);
			else {
				Optional<PatternEnum> op = patterns.stream().filter(pattern -> {
					return dateStr.matches(pattern.regex);
				}).findAny();

				if (!op.isPresent())
					throw new KtfException("日期格式不支持");

				date = DateFormatUtil.parseDate(op.get().getPattern(), dateStr);
			}
		} catch (ParseException e) {
			throw new KtfException("日期格式不支持", e);
		}
		return date;
	}

	@Getter
	public enum PatternEnum {
		// yyyy-MM-dd'T'HH:mm:ss.SSSZZ
		ISO(DateFormatUtil.PATTERN_ISO, "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}\\+\\d{2}:\\d{2}", 29),
			// yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
			ISO_Z(PATTERN_ISO_Z, "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}Z", 24),
			// yyyy-MM-dd'T'HH:mm:ssZZ
			ISO_ON_SECOND(DateFormatUtil.PATTERN_ISO_ON_SECOND,
					"\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\+\\d{2}:\\d{2}", 25),
			// yyyy-MM-dd'T'HH:mm:ss'Z'
			ISO_ON_SECOND_Z(PATTERN_ISO_ON_SECOND_Z, "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z", 20),
			// yyyy-MM-dd
			ISO_ON_DATE(DateFormatUtil.PATTERN_ISO_ON_DATE, "\\d{4}-\\d{2}-\\d{2}", 10),
			// yyyy-MM-dd HH:mm:ss.SSS
			DEFAULT(DateFormatUtil.PATTERN_DEFAULT, "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{3}", 23),
			// yyyy-MM-dd HH:mm:ss
			DEFAULT_ON_SECOND(DateFormatUtil.PATTERN_DEFAULT_ON_SECOND, "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}",
					19),
			// yyyyMMdd
			ISO_ON_DATE_NS(PATTERN_ISO_ON_DATE_NS, "\\d{8}", 8),
			// HH:mm:ss
			TIME(PATTERN_TIME, "\\d{2}:\\d{2}:\\d{2}", 8),
			// yyyy-MM-dd HH:mm
			DEFAULT_ON_MINUTE(PATTERN_DEFAULT_ON_MINUTE, "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}", 16),
			// yyyyMMdd'T'HHmmssZ
			ISO_ON_SECOND_NS_Z(PATTERN_ISO_ON_SECOND_NS_Z, "\\d{8}T\\d{6}Z", 16),
			// yyyyMMddHHmmss
			DEFAULT_ON_SECOND_NS(PATTERN_DEFAULT_ON_SECOND_NS, "\\d{14}", 14),
			// EEE, dd MMM yyyy HH:mm:ss z 示例：星期一, 16 九月 2019 16:04:56 CST
			HTTP_DATETIME(PATTERN_HTTP_DATETIME,
					"[星][期][\\u4e00-\\u9fa5], \\d{2} [\\u4e00-\\u9fa5]{1,2}[月] \\d{4} \\d{2}:\\d{2}:\\d{2} CST", 28);

		private final String	pattern;
		private final String	regex;
		private final int		length;

		private PatternEnum(String pattern, String regex, int length) {
			this.pattern	= pattern;
			this.regex		= regex;
			this.length		= length;
		}

		public int length() {
			return this.length;
		}
	};

	/**
	 * 获取某天的结束时间, 23:59:59.000
	 * 
	 * @param date 日期
	 * @return 某天的结束时间
	 */
	public static Date getEndTimeOfDay(Date date) {
		if (date == null)
			return null;

		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(date);
		calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
		calendarEnd.set(Calendar.MINUTE, 59);
		calendarEnd.set(Calendar.SECOND, 59);
		calendarEnd.set(Calendar.MILLISECOND, 0);

		return calendarEnd.getTime();
	}
}
