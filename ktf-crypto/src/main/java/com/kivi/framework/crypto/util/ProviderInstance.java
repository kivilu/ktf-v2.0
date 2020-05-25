package com.kivi.framework.crypto.util;

import java.security.Provider;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * 提供者创建工具
 * 
 * @author Eric
 *
 */
public class ProviderInstance {
    private static Provider BCProvider;

    public static Provider getBCProvider() {
        if (null == BCProvider) {
            BCProvider = new BouncyCastleProvider();
        }
        return BCProvider;
    }

}
