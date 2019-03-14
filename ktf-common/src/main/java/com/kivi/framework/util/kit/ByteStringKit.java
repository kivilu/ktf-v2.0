package com.kivi.framework.util.kit;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.BinaryCodec;
import org.apache.commons.codec.binary.Hex;

import com.kivi.framework.exception.KtfException;

/**
 * 字符和byte数组 转换工具
 */
public class ByteStringKit {
    /*
     * 编码类型
     */
    public static final int BASE64 = 1;
    public static final int HEX    = 2;
    public static final int ASCII  = 3;

    public static String toString( byte[] data, int type ) {
        if (data == null)
            return null;
        switch (type) {
            case BASE64:
                return Base64.encodeBase64String(data);
            case HEX:
                return Hex.encodeHexString(data);
            case ASCII:
                return BinaryCodec.toAsciiString(data);
            default:
                return Base64.encodeBase64String(data);
        }
    }

    public static byte[] toBytes( String data, int type ) {
        if (StrKit.isBlank(data))
            return new byte[0];
        switch (type) {
            case BASE64:
                return Base64.decodeBase64(data);
            case HEX:
                try {
                    return Hex.decodeHex(data);
                }
                catch (DecoderException e) {
                    throw new KtfException(e);
                }
            case ASCII:
                return BinaryCodec.fromAscii(data.getBytes());
            default:
                return Base64.decodeBase64(data);
        }
    }

}
