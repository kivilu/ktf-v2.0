package com.kivi.framework.cache.redis.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kivi.framework.constant.KtfConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * Redis fastjson Serializer
 * 
 * @author Eric
 *
 * @param <T>
 */
@Slf4j
public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {

	private Class<T> clazz;

	public FastJson2JsonRedisSerializer(Class<T> clazz) {
		super();
		//ParserConfig.getGlobalInstance().addAccept("com.kivi.,com.ins.");
		//ParserConfig.getGlobalInstance().setAutoTypeSupport(true);

		this.clazz = clazz;
	}

	@Override
	public byte[] serialize(T t) throws SerializationException {
		if (t == null) {
			return new byte[0];
		}
		byte[] bytes = JSON.toJSONBytes(t, SerializerFeature.WriteClassName);

		return bytes;
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null || bytes.length <= 0) {
			return null;
		}

		if (log.isTraceEnabled()) {
			String str = new String(bytes, KtfConstant.DEFAULT_CHARSET);
			log.trace("json:{}", str);
		}

		T obj = JSON.parseObject(bytes, clazz, Feature.SupportAutoType, Feature.IgnoreNotMatch);

		return obj;
	}

}
