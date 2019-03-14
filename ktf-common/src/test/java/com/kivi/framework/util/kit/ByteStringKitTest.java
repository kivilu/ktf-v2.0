package com.kivi.framework.util.kit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ByteStringKitTest {

    @Test
    public void testToStringByteArrayInt() {
        byte[] datas = ByteStringKit.toBytes("", ByteStringKit.HEX);
        assertEquals(new byte[0].length, datas.length);

        datas = ByteStringKit.toBytes(null, ByteStringKit.BASE64);
        assertEquals(new byte[0].length, datas.length);
    }

    @Test
    public void testToBytes() {
        String hex = ByteStringKit.toString(null, ByteStringKit.HEX);
        assertEquals(null, hex);
        String base64 = ByteStringKit.toString(null, ByteStringKit.BASE64);
        assertEquals(null, base64);

        hex = ByteStringKit.toString(new byte[0], ByteStringKit.HEX);
        assertEquals("", hex);
        base64 = ByteStringKit.toString(new byte[0], ByteStringKit.BASE64);
        assertEquals("", base64);
    }

}
