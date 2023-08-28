package com.kivi.framework.crypto.sm2;

import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.jce.interfaces.ECPrivateKey;

/**
 * sm2私钥
 * 
 * @author Eric
 *
 */
public interface Sm2PrivateKey extends ECPrivateKey {

	ECPrivateKeyParameters getPrivateKeyParameters();

	byte[] getWithId();

	void setWithId(byte[] withId);

	byte[] getData();
}
