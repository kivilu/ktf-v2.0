package com.kivi.framework.crypto.enums;

import java.util.Arrays;
import java.util.Optional;

import lombok.Getter;

/**
 * 类型，00：根证书，01：CRL，02：签名证书，03：验签证书，04：加密证书，05：解密证书，06：服务器证书
 * 
 * @author Eric
 *
 */
@Getter
public enum KtfCertType {
	ROOT("00", "根证书"),
		CRL("01", "CRL"),
		SIGN("02", "签名证书"),
		VERIFY("03", "验签证书"),
		ENCRYPT("04", "加密证书"),
		DECRYPT("05", "解密证书"),
		SERVER("06", "解密证书");

	public final String					code;
	public final String					desc;

	private final static KtfCertType	PUBLIC_KEYS[]	= { ROOT, VERIFY, DECRYPT };

	private KtfCertType(String code, String desc) {
		this.code	= code;
		this.desc	= desc;
	}

	public static KtfCertType valueof(String code) {
		Optional<KtfCertType> op = Arrays.stream(KtfCertType.values()).filter(s -> s.code.equals(code)).findFirst();

		return op.isPresent() ? op.get() : null;
	}

	public boolean isPublickey() {
		return Arrays.stream(PUBLIC_KEYS).anyMatch(s -> s.code.equals(this.code));
	}

}
