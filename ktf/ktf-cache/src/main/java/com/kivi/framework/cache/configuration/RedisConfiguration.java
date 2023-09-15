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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import com.alibaba.fastjson2.support.spring.data.redis.FastJsonRedisSerializer;
import com.alibaba.fastjson2.support.spring.data.redis.GenericFastJsonRedisSerializer;
import com.kivi.framework.cache.properties.KtfCacheProperties;
import com.kivi.framework.cache.properties.KtfRedisProperties;
import com.kivi.framework.constant.KtfCache;

@Configuration
@ConditionalOnProperty(name = { "spring.cache.type" }, havingValue = "redis", matchIfMissing = false)
@EnableCaching
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
//		ParserConfig.getGlobalInstance().addAccept("com.kivi.,com.ins.");
//		ParserConfig.getGlobalInstance().setAutoTypeSupport(true);

		// 创建自定义序列化器
//		FastJson2JsonRedisSerializer<?>	fastJson2JsonRedisSerializer	= new FastJson2JsonRedisSerializer<>(
//				Object.class);
		FastJsonRedisSerializer<?>	fastJsonRedisSerializer	= new FastJsonRedisSerializer<>(Object.class);

		// 包装成SerializationPair类型
		SerializationPair<?>		serializationPair		= SerializationPair.fromSerializer(fastJsonRedisSerializer);

		// redis默认配置文件
		RedisCacheConfiguration		redisCacheConfiguration	= RedisCacheConfiguration.defaultCacheConfig()
				// 设置序列化器
				.serializeValuesWith(serializationPair)
				// 设置过期时间为 1 天
				.entryTtl(Duration.ofSeconds(ktfCacheProperties.getTtl()))
				.prefixCacheNameWith(ktfCacheProperties.getPrefixKey()).disableCachingNullValues();

		// 设置一个初始化的缓存空间set集合
		Set<String>					cacheNames				= new HashSet<>();
		cacheNames.addAll(Arrays.asList(KtfCache.KTF_CACHE_NAMES));

		// 对每个缓存空间应用不同的配置
		Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
		// configMap.put(KtfCache.KTF_TOKEN,
		// redisCacheConfiguration.entryTtl(Duration.ofHours(ktfCacheProperties.getTtlToken())));

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
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);

		// GenericFastJsonRedisSerializer use jsonb
		GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer(true);

		redisTemplate.setDefaultSerializer(fastJsonRedisSerializer);// 设置默认的Serialize，包含 keySerializer & valueSerializer

//		RedisSerializer<String>	stringSerializer		= new StringRedisSerializer();
//		redisTemplate.setKeySerializer(stringSerializer);
//		redisTemplate.setValueSerializer(fastJsonRedisSerializer);
//		redisTemplate.setHashKeySerializer(stringSerializer);
//		redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
		return redisTemplate;
	}

	@ConditionalOnProperty(
			prefix = KtfRedisProperties.PREFIX,
			name = { "client-type" },
			havingValue = "lettuce",
			matchIfMissing = false)
	@Bean(name = "fastJsonRedisTemplate")
	public RedisTemplate<String, Object> fastJsonRedisTemplate(RedisConnectionFactory factory) {
		// 配置redisTemplate
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);

		RedisSerializerKit.setFastJson2JsonRedisSerializer(redisTemplate);

		redisTemplate.afterPropertiesSet();

		return redisTemplate;
	}

}
