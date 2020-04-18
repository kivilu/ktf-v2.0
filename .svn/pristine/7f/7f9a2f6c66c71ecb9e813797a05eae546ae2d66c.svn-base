package com.kivi.framework.cache.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Sentinel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ReflectionUtils;

import com.kivi.framework.cache.properties.KtfRedisProperties;
import com.kivi.framework.cache.properties.KtfRedissonProperties;
import com.kivi.framework.cache.redis.redission.RedisBlockingQueueKit;
import com.kivi.framework.cache.redis.redission.RedisBucketKit;
import com.kivi.framework.cache.redis.redission.RedisCounterKit;
import com.kivi.framework.cache.redis.redission.RedisListKit;
import com.kivi.framework.cache.redis.redission.RedisLockKit;
import com.kivi.framework.cache.redis.redission.RedisMapKit;

@Configuration
@ConditionalOnProperty(prefix = KtfRedisProperties.PREFIX,
		name = { "client-type" },
		havingValue = "redisson",
		matchIfMissing = false)
@AutoConfigureBefore(RedisConfiguration.class)
//@EnableConfigurationProperties({ KtfRedisProperties.class, RedisProperties.class })
public class RedissonConfiguration {

	@Autowired
	private KtfRedissonProperties	ktfRedissonProperties;

	@Autowired
	private RedisProperties			redisProperties;

	@Autowired
	private ApplicationContext		ctx;

	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

	@Bean
	@ConditionalOnMissingBean(RedisConnectionFactory.class)
	public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
		return new RedissonConnectionFactory(redisson);
	}

	@SuppressWarnings("unchecked")
	@Bean(destroyMethod = "shutdown")
	@ConditionalOnMissingBean(RedissonClient.class)
	public RedissonClient redisson() throws IOException {
		Config	config			= null;
		Method	clusterMethod	= ReflectionUtils.findMethod(RedisProperties.class, "getCluster");
		Method	timeoutMethod	= ReflectionUtils.findMethod(RedisProperties.class, "getTimeout");
		Object	timeoutValue	= ReflectionUtils.invokeMethod(timeoutMethod, redisProperties);
		int		timeout;
		if (null == timeoutValue) {
			timeout = 0;
		} else if (!(timeoutValue instanceof Integer)) {
			Method millisMethod = ReflectionUtils.findMethod(timeoutValue.getClass(), "toMillis");
			timeout = ((Long) ReflectionUtils.invokeMethod(millisMethod, timeoutValue)).intValue();
		} else {
			timeout = (Integer) timeoutValue;
		}

		if (ktfRedissonProperties.getConfig() != null) {
			try {
				InputStream is = getConfigStream();
				config = Config.fromJSON(is);
			} catch (IOException e) {
				// trying next format
				try {
					InputStream is = getConfigStream();
					config = Config.fromYAML(is);
				} catch (IOException e1) {
					throw new IllegalArgumentException("Can't parse config", e1);
				}
			}
		} else if (redisProperties.getSentinel() != null) {
			Method		nodesMethod	= ReflectionUtils.findMethod(Sentinel.class, "getNodes");
			Object		nodesValue	= ReflectionUtils.invokeMethod(nodesMethod, redisProperties.getSentinel());

			String[]	nodes;
			if (nodesValue instanceof String) {
				nodes = convert(Arrays.asList(((String) nodesValue).split(",")));
			} else {
				nodes = convert((List<String>) nodesValue);
			}

			config = new Config();
			config.useSentinelServers().setMasterName(redisProperties.getSentinel().getMaster())
					.addSentinelAddress(nodes).setDatabase(redisProperties.getDatabase()).setConnectTimeout(timeout)
					.setPassword(redisProperties.getPassword());
		} else if (clusterMethod != null && ReflectionUtils.invokeMethod(clusterMethod, redisProperties) != null) {
			Object			clusterObject	= ReflectionUtils.invokeMethod(clusterMethod, redisProperties);
			Method			nodesMethod		= ReflectionUtils.findMethod(clusterObject.getClass(), "getNodes");
			List<String>	nodesObject		= (List<String>) ReflectionUtils.invokeMethod(nodesMethod, clusterObject);

			String[]		nodes			= convert(nodesObject);

			config = new Config();
			config.useClusterServers().addNodeAddress(nodes).setConnectTimeout(timeout)
					.setPassword(redisProperties.getPassword());
		} else {
			config = new Config();
			String	prefix	= "redis://";
			Method	method	= ReflectionUtils.findMethod(RedisProperties.class, "isSsl");
			if (method != null && (Boolean) ReflectionUtils.invokeMethod(method, redisProperties)) {
				prefix = "rediss://";
			}

			config.useSingleServer().setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
					.setConnectTimeout(timeout).setDatabase(redisProperties.getDatabase())
					.setPassword(redisProperties.getPassword());
		}

		return Redisson.create(config);
	}

	private String[] convert(List<String> nodesObject) {
		List<String> nodes = new ArrayList<String>(nodesObject.size());
		for (String node : nodesObject) {
			if (!node.startsWith("redis://") && !node.startsWith("rediss://")) {
				nodes.add("redis://" + node);
			} else {
				nodes.add(node);
			}
		}
		return nodes.toArray(new String[nodes.size()]);
	}

	private InputStream getConfigStream() throws IOException {
		Resource	resource	= ctx.getResource(ktfRedissonProperties.getConfig());
		InputStream	is			= resource.getInputStream();
		return is;
	}

	@Bean("redisBlockingQueueKit")
	RedisBlockingQueueKit redisBlockingQueue(RedissonClient redissonClient) {
		return new RedisBlockingQueueKit(redissonClient);
	}

	@Bean("redisCounterKit")
	RedisCounterKit redisCounter(RedissonClient redissonClient) {
		return new RedisCounterKit(redissonClient);
	}

	@Bean("redisListKit")
	RedisListKit redisList(RedissonClient redissonClient) {
		return new RedisListKit(redissonClient);
	}

	@Bean("redisLockKit")
	RedisLockKit redisLock(RedissonClient redissonClient) {
		return new RedisLockKit(redissonClient);
	}

	@Bean("redisMapKit")
	RedisMapKit redisMap(RedissonClient redissonClient) {
		return new RedisMapKit(redissonClient);
	}

	@Bean("redisBucketKit")
	RedisBucketKit redisBucketKit(RedissonClient redissonClient) {
		return new RedisBucketKit(redissonClient);
	}

	// TODO:定义其他工具kit
	// ...

}
