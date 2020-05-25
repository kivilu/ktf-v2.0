package com.kivi.framework.util.kit;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.vip.vjtools.vjkit.time.DateFormatUtil;
import com.vip.vjtools.vjkit.time.DateUtil;

public class DateTimeKitTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void tetRegExp() {
		assertEquals(true,
				"2019-09-16T15:42:00.816+0800".matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}\\+\\d{4}"));
		assertEquals(true, "星期一, 16 九月 2019 16:04:56 CST"
				.matches("[星][期][\\u4e00-\\u9fa5], \\d{2} [\\u4e00-\\u9fa5]{1,2}[月] \\d{4} \\d{2}:\\d{2}:\\d{2} CST"));

		Arrays.asList(DateTimeKit.PatternEnum.values()).forEach(pattern -> {
			String	dateStr	= DateFormatUtil.formatDate(pattern.getPattern(), DateTimeKit.now());
			Boolean	regex	= dateStr.matches(pattern.getRegex());

			System.out.println(StringUtils.rightPad(pattern.getPattern(), 30) + ": " + StringUtils.rightPad(dateStr, 29)
					+ "\t\t" + regex + " " + dateStr.length());

			assertEquals(true, regex);
		});
	}

	@Test
	public void testParse() {
		System.out.println(DateTimeKit.PATTERS_MAP);

		String[] dateStrs = { "2019-09-16T16:31:12.532+08:00", "2019-09-16T16:31:12.591Z", "2019-09-16T16:31:12+08:00",
				"2019-09-16T16:31:12Z", "2019-09-16", "2019-09-16 16:31:12.592", "2019-09-16 16:31:12", "20190916",
				"16:31:12", "2019-09-16 16:31", "20190916T163112Z", "20190916163112", "星期一, 16 九月 2019 16:31:12 CST" };

		Arrays.asList(dateStrs).forEach(dateStr -> {
			System.out.println(dateStr + ":\t\t" + DateTimeKit.parse(dateStr));
		});

	}

	@Test
	public void testFormatDate() throws ParseException {

		System.out.println(DateTimeKit.PATTERS_MAP);

		Date	date	= DateTimeKit.now();

		String	sdate	= DateFormatUtil.formatDate("yyyy-MM-dd'T'HH:mm:ss.SSSZ", date);
		System.out.println(sdate);
		System.out.println(DateFormatUtil.parseDate("yyyy-MM-dd'T'HH:mm:ss.SSSZ", sdate));
		System.out.println(DateFormatUtil.parseDate("yyyy-MM-dd'T'HH:mm:ss.SSSZZ", "2019-09-16T15:25:06.686Z"));
		System.out.println(DateFormatUtil.formatDate(DateTimeKit.PATTERN_HTTP_DATETIME, date));
		System.out.println(DateFormatUtil.formatDate(DateTimeKit.PATTERN_ISO_ON_SECOND_NS_Z, date));
		System.out.println(DateFormatUtil.parseDate(DateTimeKit.PATTERN_ISO_ON_SECOND_NS_Z, "20190916T153340Z"));

	}

	@Test
	public void testCurrentUnix() {
		long unixTime = DateTimeKit.currentUnix();
		System.out.println(unixTime);
		String hexUnixTime = Long.toHexString(unixTime);
		System.out.println(hexUnixTime);

		int intTime = (int) unixTime;
		System.out.println(intTime);

		ByteBuffer buff = ByteBuffer.allocate(4);
		buff.putInt(intTime);
		System.out.println(ByteStringKit.toString(buff.array(), ByteStringKit.HEX));
		buff.rewind();
		long longTime = buff.getInt();
		System.out.println(longTime);

		String sdate = DateTimeKit.formatDateTime(DateTimeKit.fromUixTime(unixTime));
		System.out.println(sdate);
		String newdate = DateTimeKit.formatDateTime(DateTimeKit.fromUixTime(intTime));
		System.out.println(newdate);

		assertEquals(sdate, newdate);
	}

	@Test
	public void testUnixTime() {
		Date	dt			= DateUtil.addSeconds(DateTimeKit.now(), 30);
		long	unixtime	= DateTimeKit.toUnixTime(dt);

		System.out.println(unixtime);
	}

	@Test
	public void testLocalDateTime() {
		long	ms		= System.currentTimeMillis();

		Date	date	= new Date(ms);
		System.out.println(DateFormatUtil.formatDate(DateFormatUtil.PATTERN_ISO, date));

		LocalDateTime ldate = DateTimeKit.toLocalDateTime(date);

		System.out.println(ldate.format(DateTimeFormatter.ISO_DATE_TIME));

		System.out.println("ms:" + ms + "----" + DateTimeKit.toMilliseconds(ldate));

		assertEquals(ms, DateTimeKit.toMilliseconds(ldate));

	}

}
