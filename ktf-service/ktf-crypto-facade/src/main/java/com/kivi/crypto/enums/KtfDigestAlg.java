package com.kivi.crypto.enums;

import java.util.Arrays;
import java.util.Optional;

public enum KtfDigestAlg {
    SM3, SHA1, MD5;

    public static KtfDigestAlg valueof( int value ) {
        Optional<KtfDigestAlg> op = Arrays.stream(KtfDigestAlg.values())
                .filter(s-> s.ordinal() == value)
                .findFirst();

        return op.isPresent() ? op.get() : null;
    }

    public static KtfDigestAlg valueof( Byte value ) {
        return valueof(value.intValue());
    }
}
