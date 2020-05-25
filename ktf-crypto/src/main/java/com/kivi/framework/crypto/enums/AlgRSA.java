package com.kivi.framework.crypto.enums;

import java.util.Arrays;
import java.util.Optional;

import lombok.Getter;

@Getter
public enum AlgRSA {
    RSA_ECB_NO( "RSA/ECB/NoPadding" ),
    RSA_ECB_PKCS1( "RSA/ECB/PKCS1Padding" ),
    RSA_ECB_OAEP( "RSA/ECB/OAEPPadding" );

    private final String alg;

    private AlgRSA( String alg ) {
        this.alg = alg;
    }

    public int reserveBytes() {
        int bytes = 11;

        if (alg.equals(RSA_ECB_NO.alg))
            bytes = 0;
        else if (alg.equals(RSA_ECB_PKCS1.alg))
            bytes = 11;
        else if (alg.equals(RSA_ECB_OAEP.alg))
            bytes = 41;

        return bytes;
    }

    public static AlgRSA alg( String alg ) {
        Optional<AlgRSA> op = Arrays.stream(AlgRSA.values())
                .filter(s-> s.alg.equals(alg))
                .findFirst();

        return op.isPresent() ? op.get() : null;
    }
}
