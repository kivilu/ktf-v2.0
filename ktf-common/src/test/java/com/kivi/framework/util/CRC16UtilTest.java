package com.kivi.framework.util;

import org.junit.Test;

import com.kivi.framework.util.kit.HexKit;

public class CRC16UtilTest {

    @Test
    public void testCalcCrc16ByteArray() {
        String data = "04000000";
        String sskey = "D3B88A4B6B3D88557E7F9C5A846E9DA6";
        int crc = CRC16Util.calcCrc16(HexKit.hex2Byte(data + sskey));
        System.out.println("value:" + crc);
        System.out.println("hex:" + Integer.toHexString(crc));
    }

}
