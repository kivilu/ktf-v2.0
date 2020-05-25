package com.kivi.framework.crypto.enums;

public enum Padding {
    NoPadding,
    ZeroPadding,
    PKCS5Padding,
    PKCS7Padding;

    public boolean isPadding() {
        return this.ordinal() > 0;
    }
}
