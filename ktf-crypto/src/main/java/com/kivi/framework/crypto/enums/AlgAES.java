package com.kivi.framework.crypto.enums;

import java.util.Arrays;
import java.util.Optional;

import lombok.Getter;

@Getter
public enum AlgAES {
    AES_CBC_NO( "AES/CBC/NoPadding" ),
    AES_ECB_NO( "AES/ECB/NoPadding" ),
    AES_CBC_ZERO( "AES/CBC/ZeroPadding" ),
    AES_ECB_ZERO( "AES/ECB/ZeroPadding" ),
    AES_CBC_PKCS5( "AES/CBC/PKCS5Padding" ),
    AES_ECB_PKCS5( "AES/ECB/PKCS5Padding" ),
    AES_CBC_PKCS7( "AES/CBC/PKCS7Padding" ),
    AES_ECB_PKCS7( "AES/ECB/PKCS7Padding" );

    private final String alg;

    private AlgAES( String alg ) {
        this.alg = alg;
    }

    public boolean zeroPadding() {
        return alg.endsWith("ZeroPadding");
    }

    public static AlgAES alg( String alg ) {
        Optional<AlgAES> op = Arrays.stream(AlgAES.values())
                .filter(s-> s.alg.equals(alg))
                .findFirst();

        return op.isPresent() ? op.get() : null;
    }
}
