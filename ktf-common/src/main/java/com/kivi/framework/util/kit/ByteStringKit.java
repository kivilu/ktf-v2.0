package com.kivi.framework.util.kit;

import org.apache.commons.codec.binary.BinaryCodec;

import com.vip.vjtools.vjkit.text.EncodeUtil;

/**
 * 字符和byte数组 转换工具
 */
public class ByteStringKit {
	/*
	 * 编码类型
	 */
	public static final int	BASE64	= 1;
	public static final int	HEX		= 2;
	public static final int	ASCII	= 3;

	public static String toString(byte[] data, int type) {
		if (data == null)
			return null;
		switch (type) {
		case BASE64:
			return EncodeUtil.encodeBase64(data);
		case HEX:
			return EncodeUtil.encodeHex(data);
		case ASCII:
			return BinaryCodec.toAsciiString(data);
		default:
			return EncodeUtil.encodeBase64(data);
		}
	}

	public static byte[] toBytes(String data, int type) {
		if (StrKit.isBlank(data))
			return new byte[0];
		switch (type) {
		case BASE64:
			return EncodeUtil.decodeBase64(data);
		case HEX:
			return EncodeUtil.decodeHex(data.toUpperCase());
		case ASCII:
			return BinaryCodec.fromAscii(data.getBytes());
		default:
			return EncodeUtil.decodeBase64(data);
		}
	}

	/**
	 * byte数组转换为HEX字符串
	 * 
	 * @param data
	 * @return
	 */
	public static String toHex(byte[] data) {
		return toString(data, HEX);
	}

	public static byte[] fromHex(String data) {
		return toBytes(data, HEX);
	}

	public static String toBase64(byte[] data) {
		return toString(data, BASE64);
	}

	public static byte[] fromBase64(String data) {
		return toBytes(data, BASE64);
	}
}
