package com.kivi.framework.cache.configuration;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import com.alibaba.fastjson.parser.ParserConfig;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kivi.framework.cache.constant.KtfCache;
import com.kivi.framework.cache.properties.KtfCacheProperties;
import com.kivi.framework.cache.properties.KtfRedisProperties;
import com.kivi.framework.cache.redis.serializer.FastJson2JsonRedisSerializer;
import com.kivi.framework.cache.redis.serializer.Fst2JsonRedisSerializer;
import com.kivi.framework.cache.redis.serializer.KtfStringRedisSerializer;

@Configuration
@ConditionalOnProperty(name = { "spring.cache.type" }, havingValue = "redis", matchIfMissing = false)
@EnableCaching(proxyTargetClass = true, mode = AdviceMode.PROXY)
@AutoConfigureAfter({ RedisAutoConfiguration.class })
public class RedisConfiguration extends CachingConfigurerSupport {
	@Autowired(required = false)
	private LettuceConnectionFactory	lettuceConnectionFactory;

	@Autowired(required = false)
	JedisConnectionFactory				jedisConnectionFactory;

	@Autowired(required = false)
	private RedissonConnectionFactory	redissonConnectionFactory;

	@Autowired
	private KtfCacheProperties			ktfCacheProperties;

	@Autowired
	private KtfRedisProperties			ktfRedisProperties;

	// 缓存管理器
	@Override
	@Bean
	public CacheManager cacheManager() {
		CacheManager cacheManager = null;
		switch (ktfRedisProperties.getClientType()) {
		case jedis:
			cacheManager = cacheManager(jedisConnectionFactory);
			break;
		case lettuce:
			cacheManager = cacheManager(lettuceConnectionFactory);
			break;
		case redisson:
			cacheManager = cacheManager(redissonConnectionFactory);
			break;
		default:
			cacheManager = cacheManager(lettuceConnectionFactory);
			break;

		}

		return cacheManager;
	}

	private CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		ParserConfig.getGlobalInstance().addAccept("com.kivi.,com.ins.");
		ParserConfig.getGlobalInstance().setAutoTypeSupport(true);

		// 创建自定义序列化器
		FastJson2JsonRedisSerializer<?>	fastJson2JsonRedisSerializer	= new FastJson2JsonRedisSerializer<>(
				Object.class);

		// 包装成SerializationPair类型
		SerializationPair<?>			serializationPair				= SerializationPair
				.fromSerializer(fastJson2JsonRedisSerializer);

		// redis默认配置文件
		RedisCacheConfiguration			redisCacheConfiguration			= RedisCacheConfiguration.defaultCacheConfig()
				// 设置序列化器
				.serializeValuesWith(serializationPair)
				// 缓存1天
				.entryTtl(Duration.ofHours(ktfCacheProperties.getExpireHour()))
				.prefixKeysWith(ktfCacheProperties.getPrefixKey())
				// 不缓存空值
				.disableCachingNullValues();

		// 设置一个初始化的缓存空间set集合
		Set<String>						cacheNames						= new HashSet<>();
		cacheNames.addAll(Arrays.asList(KtfCache.KTF_CACHE_NAMES));

		// 对每个缓存空间应用不同的配置
		Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
		configMap.put(KtfCache.KTF_TOKEN,
				redisCacheConfiguration.entryTtl(Duration.ofHours(ktfCacheProperties.getTtlToken())));

		// RedisCacheManager 生成器创建
		return RedisCacheManager.builder(RedisCacheWriter.lockingRedisCacheWriter(redisConnectionFactory))
				.cacheDefaults(redisCacheConfiguration).initialCacheNames(cacheNames)
				.withInitialCacheConfigurations(configMap).build();
	}

	/*
	 * private CacheManager cacheManager( RedissonClient redissonClient ) {
	 * RedissonSpringCacheManager cacheManager = new
	 * RedissonSpringCacheManager(redissonClient,
	 * this.jytCacheProperties.getRedissonConfig());
	 * 
	 * return cacheManager; }
	 */

	/**
	 * RedisTemplate配置
	 */
	@Bean(name = "redisTemplate")
	public RedisTemplate<String, Object> objectRedisTemplate(RedisConnectionFactory factory) {
		// 配置redisTemplate
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);

		setFastJson2JsonRedisSerializer(redisTemplate);

		redisTemplate.afterPropertiesSet();

		return redisTemplate;
	}

	/**
	 * 设置序列化方法:Jackson
	 */
	@SuppressWarnings("unused")
	private void setJackson2JsonRedisSerializer(RedisTemplate<?, ?> template) {
		Jackson2JsonRedisSerializer<Object>	jackson2JsonRedisSerializer	= new Jackson2JsonRedisSerializer<>(
				Object.class);
		ObjectMapper						om							= new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
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

	private void setFastJson2JsonRedisSerializer(RedisTemplate<?, ?> template) {
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
	@SuppressWarnings("unused")
	private void setFst2JsonRedisSerializer(RedisTemplate<?, ?> template) {
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
