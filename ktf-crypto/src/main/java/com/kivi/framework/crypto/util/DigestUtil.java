package com.kivi.framework.crypto.util;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import com.kivi.framework.crypto.enums.AlgDigest;
import com.kivi.framework.crypto.sm3.SM3Kit;
import com.kivi.framework.util.kit.ByteStringKit;

public class DigestUtil {

	public static String hashHex(AlgDigest alg, String data) {
		byte[] hash = digest(alg, data);
		return ByteStringKit.toHex(hash);
	}

	public static String hashHex(AlgDigest alg, byte[] data) {
		byte[] hash = digest(alg, data);
		return ByteStringKit.toHex(hash);
	}

	public static String hashBase64(AlgDigest alg, String data) {
		byte[] hash = digest(alg, data);
		return ByteStringKit.toBase64(hash);
	}

	public static String hashBase64(AlgDigest alg, byte[] data) {
		byte[] hash = digest(alg, data);
		return ByteStringKit.toBase64(hash);
	}

	public static byte[] digest(AlgDigest alg, String data) {
		return digest(alg, StringUtils.getBytesUtf8(data));
	}

	public static byte[] digest(AlgDigest alg, byte[] data) {
		byte[] result = null;
		switch (alg) {
		case MD5:
			result = DigestUtils.md5(data);
			break;
		case SHA1:
			result = DigestUtils.sha1(data);
			break;
		case SHA256:
			result = DigestUtils.sha256(data);
			break;
		case SM3:
			result = SM3Kit.sm3(data);
			break;
		default:
			break;
		}

		return result;
	}
}
