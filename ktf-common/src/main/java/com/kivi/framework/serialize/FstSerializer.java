package com.kivi.framework.serialize;

import org.nustaq.serialization.FSTConfiguration;

import com.kivi.framework.constant.KtfConstant;

public class FstSerializer {

	private static final ThreadLocal<FSTConfiguration>	conf		= new ThreadLocal<FSTConfiguration>() {
																		@Override
																		public FSTConfiguration initialValue() {
																			FSTConfiguration conf = FSTConfiguration
																					.createDefaultConfiguration();
																			conf.setForceSerializable(false);
																			return conf;
																		}
																	};

	private static final ThreadLocal<FSTConfiguration>	confJson	= new ThreadLocal<FSTConfiguration>() {
																		@Override
																		public FSTConfiguration initialValue() {
																			FSTConfiguration confjson = FSTConfiguration
																					.createJsonConfiguration(false,
																							false);
																			confjson.setForceSerializable(false);
																			return confjson;
																		}
																	};

	public static byte[] serialize(final Object o) {
		if (o == null)
			return null;

		return conf.get().asByteArray(o);
	}

	public static Object deserialize(final byte[] in) {
		if (in == null || in.length == 0)
			return null;

		return conf.get().asObject(in);
	}

	public static <T> T deserialize(final byte[] in, Class<T> clazz) {
		if (in == null || in.length == 0)
			return null;
		return clazz.cast(conf.get().asObject(in));
	}

	public static byte[] serializeJson(final Object o) {

		return confJson.get().asJsonString(o).getBytes(KtfConstant.DEFAULT_CHARSET);
	}

	public static Object deserializeJson(final byte[] in) {
		return confJson.get().asObject(in);
	}

	public static <T> T deserializeJson(final byte[] in, Class<T> clazz) {

		return clazz.cast(confJson.get().asObject(in));
	}

}
