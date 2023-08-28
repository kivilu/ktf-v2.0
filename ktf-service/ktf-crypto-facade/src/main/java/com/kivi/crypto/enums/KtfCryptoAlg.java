package com.kivi.crypto.enums;

import java.util.Arrays;
import java.util.Optional;

public enum KtfCryptoAlg {
    NONE, SM4, SM4_NoPadding, AES;

    public static KtfCryptoAlg valueof( int value ) {
        Optional<KtfCryptoAlg> op = Arrays.stream(KtfCryptoAlg.values())
                .filter(s-> s.ordinal() == value)
                .findFirst();

        return op.isPresent() ? op.get() : null;
    }

    public static KtfCryptoAlg valueof( Byte value ) {
        return valueof(value.intValue());
    }
}
