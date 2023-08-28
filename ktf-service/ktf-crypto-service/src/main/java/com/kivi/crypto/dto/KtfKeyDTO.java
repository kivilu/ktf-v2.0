package com.kivi.crypto.dto;

import java.io.Serializable;

import com.kivi.crypto.enums.KtfCryptoAlg;
import com.kivi.framework.crypto.enums.AlgSign;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@ToString
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
public class KtfKeyDTO<T> implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	KtfCryptoAlg				cryptoAlg;

	AlgSign						signAlg;

	T							key;

	byte[]						iv;

	public static <T> KtfKeyDTO<T> build(KtfCryptoAlg alg, T key) {
		return KtfKeyDTO.<T>builder().cryptoAlg(alg).key(key).build();
	}

	public static <T> KtfKeyDTO<T> build(AlgSign alg, T key) {
		return KtfKeyDTO.<T>builder().signAlg(alg).key(key).build();
	}

}
