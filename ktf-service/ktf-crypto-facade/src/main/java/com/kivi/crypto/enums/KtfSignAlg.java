package com.kivi.crypto.enums;

import java.util.Arrays;
import java.util.Optional;

public enum KtfSignAlg {
    SM2, RSA;

    public static KtfSignAlg valueof( int value ) {
        Optional<KtfSignAlg> op = Arrays.stream(KtfSignAlg.values())
                .filter(s-> s.ordinal() == value)
                .findFirst();

        return op.isPresent() ? op.get() : null;
    }

    public static KtfSignAlg valueof( Byte value ) {
        return valueof(value.intValue());
    }
}
