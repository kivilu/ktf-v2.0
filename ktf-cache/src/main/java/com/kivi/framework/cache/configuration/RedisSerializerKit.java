package com.kivi.framework.cache.configuration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kivi.framework.cache.redis.serializer.FastJson2JsonRedisSerializer;
import com.kivi.framework.cache.redis.serializer.Fst2JsonRedisSerializer;
import com.kivi.framework.cache.redis.serializer.KtfStringRedisSerializer;

public class RedisSerializerKit {

	/**
	 * 设置序列化方法:Jackson
	 */
	public static void setJackson2JsonRedisSerializer(RedisTemplate<?, ?> template) {
		Jackson2JsonRedisSerializer<Object>	jackson2JsonRedisSerializer	= new Jackson2JsonRedisSerializer<>(
				Object.class);
		ObjectMapper						om							= new ObjectMapper();
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		om.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
		// 不适用默认的dateTime进行序列化,使用JSR310的LocalDateTimeSerializer
		om.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
		// 重点,这是序列化LocalDateTIme和LocalDate的必要配置,由Jackson-data-JSR310实现
		om.registerModule(new JavaTimeModule());

		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL,
				JsonTypeInfo.As.PROPERTY);
		GenericJackson2JsonRedisSerializer.registerNullValueSerializer(om, null);
		jackson2JsonRedisSerializer.setObjectMapper(om);

		KtfStringRedisSerializer keySerializer = new KtfStringRedisSerializer();

		// template.setKeySerializer(template.getKeySerializer());
		template.setKeySerializer(keySerializer);
		template.setValueSerializer(jackson2JsonRedisSerializer);

		template.setDefaultSerializer(jackson2JsonRedisSerializer);
		template.setHashKeySerializer(template.getKeySerializer());
		template.setHashValueSerializer(jackson2JsonRedisSerializer);
	}

	/**
	 * 设置序列化方法:FastJson
	 */

	public static void setFastJson2JsonRedisSerializer(RedisTemplate<?, ?> template) {
		FastJson2JsonRedisSerializer<Object>	fastJson2JsonRedisSerializer	= new FastJson2JsonRedisSerializer<>(
				Object.class);

		KtfStringRedisSerializer				keySerializer					= new KtfStringRedisSerializer();
		// template.setKeySerializer(template.getKeySerializer());
		template.setKeySerializer(keySerializer);
		template.setValueSerializer(fastJson2JsonRedisSerializer);

		template.setDefaultSerializer(fastJson2JsonRedisSerializer);
		template.setHashKeySerializer(template.getKeySerializer());
		template.setHashValueSerializer(fastJson2JsonRedisSerializer);
	}

	/**
	 * 设置序列化方法:Fst，不适用于使用DevTools自动重启开启的情况
	 */
	protected void setFst2JsonRedisSerializer(RedisTemplate<?, ?> template) {
		Fst2JsonRedisSerializer<Object>	fst2JsonRedisSerializer	= new Fst2JsonRedisSerializer<>(Object.class);

		KtfStringRedisSerializer		keySerializer			= new KtfStringRedisSerializer();
		// template.setKeySerializer(template.getKeySerializer());
		template.setKeySerializer(keySerializer);
		template.setValueSerializer(fst2JsonRedisSerializer);
		template.setDefaultSerializer(fst2JsonRedisSerializer);
		template.setHashKeySerializer(template.getKeySerializer());
		template.setHashValueSerializer(fst2JsonRedisSerializer);
	}
}
