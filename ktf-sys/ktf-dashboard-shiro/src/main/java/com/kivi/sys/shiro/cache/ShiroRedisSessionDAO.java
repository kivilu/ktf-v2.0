package com.kivi.sys.shiro.cache;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

import com.kivi.framework.properties.KtfSysProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * @Descriptin redis实现共享session
 */
@Slf4j
public class ShiroRedisSessionDAO extends EnterpriseCacheSessionDAO {

	private final KtfSysProperties		ktfDashboardProperties;

	private final RedisTemplate<String, Object>	redisTemplate;

	public ShiroRedisSessionDAO(KtfSysProperties ktfDashboardProperties,
			RedisTemplate<String, Object> redisTemplate) {
		this.ktfDashboardProperties	= ktfDashboardProperties;
		this.redisTemplate			= redisTemplate;
	}

	// 创建session，保存到数据库
	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = super.doCreate(session);
		log.trace("创建session:{}", session.getId());
		redisTemplate.opsForValue().set(prefix() + sessionId.toString(), session);
		return sessionId;
	}

	// 获取session
	@Override
	protected Session doReadSession(Serializable sessionId) {
		log.trace("获取session:{}", sessionId);
		// 先从缓存中获取session，如果没有再去数据库中获取
		Session session = super.doReadSession(sessionId);
		if (session == null) {
			session = (Session) redisTemplate.opsForValue().get(prefix() + sessionId.toString());
		}
		return session;
	}

	// 更新session的最后一次访问时间
	@Override
	protected void doUpdate(Session session) {
		super.doUpdate(session);

		log.trace("获取session:{}", session.getId());

		if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
			// 如果会话过期/停止 没必要再更新了
			log.info("会话{}过期/停止 没必要再更新了", session.getId());
			return;
		}

		// if (session.getId() == null)
		// return;
		String key = prefix() + session.getId().toString();
		if (!redisTemplate.hasKey(key)) {
			redisTemplate.opsForValue().set(key, session);
		}
		redisTemplate.expire(key, ktfDashboardProperties.getSession().getExpireTime(), TimeUnit.SECONDS);
	}

	// 删除session
	@Override
	protected void doDelete(Session session) {
		log.trace("删除session:{}", session.getId());
		super.doDelete(session);
		redisTemplate.delete(prefix() + session.getId().toString());
	}

	private String prefix() {
		return ktfDashboardProperties.getSession().getCache();
	}
}