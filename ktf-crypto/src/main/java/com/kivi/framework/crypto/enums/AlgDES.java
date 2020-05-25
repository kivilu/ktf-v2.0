package com.kivi.framework.crypto.enums;

import java.util.Arrays;
import java.util.Optional;

import lombok.Getter;

@Getter
public enum AlgDES {
    DES_CBC_NO( "DES/CBC/NoPadding" ),
    DES_ECB_NO( "DES/ECB/NoPadding" ),
    DES_CBC_ZERO( "DES/CBC/ZeroPadding" ),
    DES_ECB_ZERO( "DES/ECB/ZeroPadding" ),
    DES_CBC_PKCS5( "DES/CBC/PKCS5Padding" ),
    DES_ECB_PKCS5( "DES/ECB/PKCS5Padding" ),
    TRIPLE_DES_CBC_NO( "DESede/CBC/NoPadding" ),
    TRIPLE_DES_ECB_NO( "DESede/ECB/NoPadding" ),
    TRIPLE_DES_CBC_ZERO( "DESede/CBC/ZeroPadding" ),
    TRIPLE_DES_ECB_ZERO( "DESede/ECB/ZeroPadding" ),
    TRIPLE_DES_CBC_PKCS5( "DESede/CBC/PKCS5Padding" ),
    TRIPLE_DES_ECB_PKCS5( "DESede/ECB/PKCS5Padding" ),
    TRIPLE_DES_CBC_PKCS7( "DESede/CBC/PKCS7Padding" ),
    TRIPLE_DES_ECB_PKCS7( "DESede/ECB/PKCS7Padding" );

    private final String alg;

    private AlgDES( String alg ) {
        this.alg = alg;
    }

    public boolean zeroPadding() {
        return alg.endsWith("ZeroPadding");
    }

    /**
     * 判断算法是3des
     * 
     * @param alg
     * @return
     */
    public boolean triple() {
        return alg.startsWith("DESede");
    }

    public static AlgDES alg( String alg ) {
        Optional<AlgDES> op = Arrays.stream(AlgDES.values())
                .filter(s-> s.alg.equals(alg))
                .findFirst();

        return op.isPresent() ? op.get() : null;
    }
}
