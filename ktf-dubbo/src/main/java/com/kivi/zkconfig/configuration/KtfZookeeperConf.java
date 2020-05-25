package com.kivi.zkconfig.configuration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.stream.Collectors;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.client.ConnectStringParser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kivi.zkconfig.properties.KtfZkConfigProperties;
import com.kivi.zkconfig.service.ZkConfigService;
import com.kivi.zkconfig.service.impl.ZkConfigServiceImpl;

import lombok.extern.slf4j.Slf4j;

@ConditionalOnProperty(prefix = KtfZkConfigProperties.PREFIX,
		name = { "enabled" },
		havingValue = "true",
		matchIfMissing = false)
@Configuration
@Slf4j
public class KtfZookeeperConf {

	private static final int			DEFAULT_CONNECTION_TIMEOUT_SECONDS	= 15;
	private static final Duration		duration							= Duration
			.ofSeconds(DEFAULT_CONNECTION_TIMEOUT_SECONDS);
	private static final RetryPolicy	retryPolicy							= new ExponentialBackoffRetry(1000, 3);

	@Bean
	public TreeCache getTreeCache(
			CuratorFramework client,
			KtfZkConfigProperties ktfZkConfigProperties,
			ZkConfigService zkConfigService) throws Exception {
		TreeCache cache = new TreeCache(client, ktfZkConfigProperties.root());
		cache.start();
		addListener(client, zkConfigService, cache, ktfZkConfigProperties.root());

		return cache;
	}

	public void addListener(CuratorFramework client, ZkConfigService zkConfigService, TreeCache cache, String root) {
		TreeCacheListener listener = new TreeCacheListener() {
			@Override
			public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
				String key = null;
				if (event.getData() != null)
					key = ZKPaths.getNodeFromPath(event.getData().getPath());
				switch (event.getType()) {
				case NODE_ADDED: {
					Object object = zkConfigService.sync(key);
					log.trace("Node added: path: {}, key:{}, value: {}", event.getData().getPath(), key, object);
					break;
				}
				case NODE_UPDATED: {
					Object object = zkConfigService.sync(key);
					log.trace("Node updated: {}, key:{}, value: {}", event.getData().getPath(), key, object);
					break;
				}
				case NODE_REMOVED: {
					Object object = zkConfigService.removeLocal(key);
					log.trace("Node removed: {}, key:{}, value: {}", event.getData().getPath(), key, object);
					break;
				}
				default: {
					log.trace("other event: {}", event.getType());
					break;
				}
				}
			}
		};
		cache.getListenable().addListener(listener);
	}

	@Bean
	public ZkConfigService zkConfigService(CuratorFramework client, KtfZkConfigProperties ktfZkConfigProperties) {
		return new ZkConfigServiceImpl(client, ktfZkConfigProperties.root());
	}

	@Bean
	public CuratorFramework getCuratorFramework(KtfZkConfigProperties ktfZkConfigProperties) {

		ConnectStringParser connectStringParser = new ConnectStringParser(ktfZkConfigProperties.getConnectStr());
		if (connectStringParser.getChrootPath() != null) {
			final String connectionStringForChrootCreation = connectStringParser.getServerAddresses().stream()
					.map(InetSocketAddress::toString).collect(Collectors.joining(","));
			try (final CuratorFramework clientForChrootCreation = newCuratorFrameworkClient(
					connectionStringForChrootCreation, retryPolicy, duration)) {
				startAndBlock(clientForChrootCreation);
				tryIt(() -> clientForChrootCreation.createContainers(connectStringParser.getChrootPath()));
			}
		}

		CuratorFramework client = newCuratorFrameworkClient(ktfZkConfigProperties.getConnectStr(), retryPolicy,
				duration);
		startAndBlock(client);
		return client;
	}

	private static CuratorFramework
			newCuratorFrameworkClient(String connectionString, RetryPolicy retryPolicy, Duration duration) {
		return CuratorFrameworkFactory.builder().connectString(connectionString).retryPolicy(retryPolicy)
				.connectionTimeoutMs((int) duration.toMillis()).build();
	}

	private static void startAndBlock(CuratorFramework c) {
		c.start();

		tryIt(() -> {
			if (!c.getZookeeperClient().blockUntilConnectedOrTimedOut()) {
				throw new IOException(
						"Did not connect in time: " + c.getZookeeperClient().getConnectionTimeoutMs() + " ms");
			}
		});
	}

	private static void tryIt(ThrowingRunner r) {
		try {
			r.run();
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@FunctionalInterface
	interface ThrowingRunner {
		void run() throws Exception;
	}

}
