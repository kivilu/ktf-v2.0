package com.kivi.framework.crypto.util;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.kivi.framework.crypto.enums.AlgDigest;

class DigestUtilTest {

    @Test
    void testHashHexAlgDigestString() {
        String data =
            "{\"Provice ID\":\"北京\",\"City ID\":\"北京\",\"longitude\":\"东经：116°23'17\\\"\",\"latitude\":\"北纬：39°54'27\\\"\",\"Test1\":\"测试数据1\",\"Test2\":\"测试数据2\",\"Test3\":\"测试数据3\",\"Test4\":\"测试数据4\",\"Test5\":\"测试数据5\"}";
        String md5hex = DigestUtil.hashHex(AlgDigest.MD5, data);

        System.out.println("data:" + data);
        System.out.println("md5 hex: " + md5hex);
    }

    @Test
    void testHashHexAlgDigestByteArray() {
        fail("Not yet implemented");
    }

    @Test
    void testHashBase64AlgDigestString() {
        fail("Not yet implemented");
    }

    @Test
    void testHashBase64AlgDigestByteArray() {
        fail("Not yet implemented");
    }

    @Test
    void testDigestAlgDigestString() {
        String data = "11111111";
        String hashBase64 = DigestUtil.hashBase64(AlgDigest.SM3, data);

        System.out.println("data:" + data);
        System.out.println("hash hex: " + hashBase64);
    }

    @Test
    void testDigestAlgDigestByteArray() {
        fail("Not yet implemented");
    }

}
