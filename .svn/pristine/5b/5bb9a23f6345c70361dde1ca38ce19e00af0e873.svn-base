/**
 * 
 */
package com.kivi.framework.crypto.sm3;

import java.nio.charset.Charset;

import org.bouncycastle.crypto.digests.SM3Digest;

import com.kivi.framework.util.kit.ByteStringKit;

/**
 * SM3哈希工具
 */
public class SM3Kit {

    /** SM3值的长度 */
    private static final int              SM3_LENGTH = 32;

    private static ThreadLocal<SM3Digest> INSTANCES  = new ThreadLocal<SM3Digest>() {

                                                         @Override
                                                         protected SM3Digest initialValue() {
                                                             return new SM3Digest();
                                                         }

                                                     };

    public static String sm3Hex( byte[] data ) {
        return ByteStringKit.toString(sm3(data), ByteStringKit.HEX);
    }

    public static String sm3Hex( String data ) {
        return ByteStringKit.toString(sm3(data), ByteStringKit.HEX);
    }

    public static String sm3Base64( byte[] data ) {
        return ByteStringKit.toString(sm3(data), ByteStringKit.BASE64);
    }

    public static String sm3Base64( String data ) {
        return ByteStringKit.toString(sm3(data), ByteStringKit.BASE64);
    }

    public static byte[] sm3( byte[] data ) {
        byte[] sm3 = new byte[SM3_LENGTH];
        SM3Digest sm3Digest = INSTANCES.get();
        sm3Digest.update(data, 0, data.length);
        sm3Digest.doFinal(sm3, 0);
        sm3Digest.reset();

        return sm3;
    }

    public static byte[] sm3( String data ) {
        return sm3(data.getBytes(Charset.forName("UTF-8")));
    }

}
