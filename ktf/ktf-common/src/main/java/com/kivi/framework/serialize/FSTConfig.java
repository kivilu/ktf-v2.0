package com.kivi.framework.serialize;

import java.util.HashSet;
import java.util.Set;

import org.nustaq.serialization.FSTConfiguration;

public class FSTConfig {
	final FSTConfiguration conf;
	Set<String> clazzSet;

	public FSTConfig(FSTConfiguration conf) {
		this.conf = conf;
		clazzSet = new HashSet<>();
	}

	Object asObject(final byte[] in) {
		return conf.asObject(in);
	}

	byte[] asByteArray(final Object o) {

		if (!clazzSet.contains(o.getClass().getName())) {
			conf.registerClass(o.getClass());
		}

		return conf.asByteArray(o);
	}

	String asJsonString(final Object o) {
		if (!clazzSet.contains(o.getClass().getName())) {
			conf.registerClass(o.getClass());
		}
		return conf.asJsonString(o);
	}
}
