package com.kivi.framework.util.kit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;

public class StrKitTest {

	@Test
	public void testSubString() {
		String	fileUrl	= "http://127.0.0.1/group/ktf/20190920/file.txt?param=1";

		String	temp	= StringUtils.substringBetween(fileUrl, "group", "?");

		assertEquals("/ktf/20190920/file.txt", temp);
	}

	@Test
	public void testSplit() {
		String[] arrays = StringUtils.split("AAAA", ",");
		System.out.println(JSON.toJSONString(arrays, JSONWriter.Feature.PrettyFormat));

		arrays = StringUtils.split("AAAA,BBBB", ",");
		System.out.println(JSON.toJSONString(arrays, JSONWriter.Feature.PrettyFormat));

		arrays = StringUtils.split("AAAA|BBBB|CCCCC", "|");
		System.out.println(JSON.toJSONString(arrays, JSONWriter.Feature.PrettyFormat));

		String data = StrKit.joinWith("|", "AAAA", "BBB", "?", "?", "CCC");
		arrays = StringUtils.split(data, "|");
		System.out.println(JSON.toJSONString(arrays, JSONWriter.Feature.PrettyFormat));
	}

	@Test
	public void testJoinList() {

		Long[] arrays = { 1L, 2L };

		System.out.println(StrKit.join(",", Arrays.asList(arrays)));

	}

	@Test
	public void testJoin() {
		System.out.println(StrKit.join("Test1", "Test2", "Test3"));

		System.out.println(StrKit.joinWith("-", "Test1", "Test2", "Test3"));
	}

	@Test
	public void testCamel2Underline() {

		System.out.println(StrKit.camel2Underline("testCamel2Underline"));
		System.out.println(StrKit.camel2Underline("test_camel2_underline"));

		System.out.println(StrKit.underlineToCamel("test_camel2_underline"));
	}

}
