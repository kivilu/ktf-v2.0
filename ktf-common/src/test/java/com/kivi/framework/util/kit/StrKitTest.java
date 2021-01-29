package com.kivi.framework.util.kit;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Arrays;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class StrKitTest {

    @Test
    public void testSubString() {
        String fileUrl = "http://127.0.0.1/group/ktf/20190920/file.txt?param=1";

        String temp = StringUtils.substringBetween(fileUrl, "group", "?");

        assertEquals("/ktf/20190920/file.txt", temp);
    }

    @Test
    public void testSplit() {
        String[] arrays = StringUtils.split("AAAA", ",");
        System.out.println(JSON.toJSONString(arrays, true));

        arrays = StringUtils.split("AAAA,BBBB", ",");
        System.out.println(JSON.toJSONString(arrays, true));

        arrays = StringUtils.split("AAAA|BBBB|CCCCC", "|");
        System.out.println(JSON.toJSONString(arrays, true));

        String data = StrKit.joinWith("|", "AAAA", "BBB", "?", "?", "CCC");
        arrays = StringUtils.split(data, "|");
        System.out.println(JSON.toJSONString(arrays, true));
    }

    @Test
    public void testJoinList() {

        Long[] arrays = {1L, 2L};

        System.out.println(StrKit.join(",", Arrays.asList(arrays)));

    }

    @Test
    public void testJoin() {
        System.out.println(StrKit.join("Test1", "Test2", "Test3"));

        System.out.println(StrKit.joinWith("-", "Test1", "Test2", "Test3"));
    }

}
