package com.kivi.framework.crypto.sm2;

import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jce.interfaces.ECPublicKey;

/**
 * sm2公钥
 * 
 * @author Eric
 *
 */
public interface Sm2PublicKey extends ECPublicKey {
    ECPublicKeyParameters getPublicKeyParameters();

    byte[] getWithId();

    void setWithId( byte[] withId );
}
