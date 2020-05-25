package com.kivi.framework.crypto.enums;

import java.util.Arrays;
import java.util.Optional;

import lombok.Getter;

@Getter
public enum AlgSM2 {
    SM2( "SM2" );

    private final String alg;

    private AlgSM2( String alg ) {
        this.alg = alg;
    }

    public static AlgSM2 alg( String alg ) {
        Optional<AlgSM2> op = Arrays.stream(AlgSM2.values())
                .filter(s-> s.alg.equals(alg))
                .findFirst();

        return op.isPresent() ? op.get() : null;
    }
}
