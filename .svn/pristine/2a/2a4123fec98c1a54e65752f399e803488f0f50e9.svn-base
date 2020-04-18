package com.kivi.framework.crypto.enums;

import java.util.Arrays;
import java.util.Optional;

import com.kivi.framework.util.kit.StrKit;

import lombok.Getter;

@Getter
public enum AlgSM4 {
    SM4_CBC_NoPadding( "SM4/CBC/NoPadding" ),
    SM4_ECB_NoPadding( "SM4/ECB/NoPadding" ),
    SM4_CBC_ZeroPadding( "SM4/CBC/ZeroPadding" ),
    SM4_ECB_ZeroPadding( "SM4/ECB/ZeroPadding" ),
    SM4_CBC_PKCS7( "SM4/CBC/PKCS7Padding" ),
    SM4_ECB_PKCS7( "SM4/ECB/PKCS7Padding" );

    private final String alg;

    private AlgSM4( String alg ) {
        this.alg = alg;
    }

    public boolean zeroPadding() {
        return alg.endsWith("ZeroPadding");
    }

    public Padding padding() {
        String[] items = StrKit.split(this.alg, StrKit.SLASH);

        return Padding.valueOf(items[2]);
    }

    public static AlgSM4 alg( String alg ) {
        Optional<AlgSM4> op = Arrays.stream(AlgSM4.values())
                .filter(s-> s.alg.equals(alg))
                .findFirst();

        return op.isPresent() ? op.get() : null;
    }
}
