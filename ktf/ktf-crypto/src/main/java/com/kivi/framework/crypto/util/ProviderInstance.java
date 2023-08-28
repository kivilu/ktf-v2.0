package com.kivi.framework.crypto.util;

import java.security.Provider;
import java.security.Security;

/**
 * 提供者创建工具
 * 
 * @author Eric
 *
 */
public class ProviderInstance {
	static {
		if (Security.getProvider("BC") == null) {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		}
	}

	private static Provider BCProvider;

	public static Provider getBCProvider() {
		if (null == BCProvider) {
			BCProvider = Security.getProvider("BC");
		}
		return BCProvider;
	}

}
