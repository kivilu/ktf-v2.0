package com.kivi.framework.crypto.enums;

import java.util.Arrays;
import java.util.Optional;

import lombok.Getter;

@Getter
public enum AlgSign {
    RSA_SHA1( "SHA1withRSA" ),
    RSA_SHA256( "SHA256withRSA" ),
    RSA_SM3( "sm3withRSA" ),
    SM2_SM3( "SM3WithSM2" ),
    SM2_SHA1( "sha1withSM2" ),
    SM2_SHA256( "sha256withSM2" ),
    SM2_NO( "NoWITHSM2" );

    private final String alg;

    private AlgSign( String alg ) {
        this.alg = alg;
    }

    public static AlgSign alg( String alg ) {
        Optional<AlgSign> op = Arrays.stream(AlgSign.values())
                .filter(s-> s.alg.equals(alg))
                .findFirst();

        return op.isPresent() ? op.get() : null;
    }
}
