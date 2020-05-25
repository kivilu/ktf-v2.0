package com.kivi.framework.cache.aspect;

import java.lang.reflect.Method;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kivi.framework.cache.annotation.KtfCacheEvict;
import com.kivi.framework.cache.redis.IRedisService;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能描述:清除缓存切面类
 * 
 * @author xueqi
 *
 */
@Component
@Aspect
@Slf4j
public class KtfCacheEvictAspect {

	@Autowired
	private IRedisService redisService;

	// 截获标有@CacheRemove的方法
	@Pointcut(value = "(execution(* *.*(..)) && @annotation(com.kivi.framework.cache.annotation.KtfCacheEvict))")
	private void pointcut() {
	}

	/**
	 * 功能描述: 切面在截获方法返回值之后
	 *
	 * @return void
	 * @throws @author fuyuchao
	 * @date 2018/9/14 16:55
	 * @params [joinPoint]
	 */
	@AfterReturning(value = "pointcut()")
	private void process(JoinPoint joinPoint) {
		// 获取被代理的类
		Object			target		= joinPoint.getTarget();
		// 获取切入方法的数据
		MethodSignature	signature	= (MethodSignature) joinPoint.getSignature();
		// 获取切入方法
		Method			method		= signature.getMethod();
		// 获得注解
		KtfCacheEvict	cacheRemove	= method.getAnnotation(KtfCacheEvict.class);

		if (cacheRemove != null) {
			// 清除当前类的缓存
			cleanRedisCache("*" + target.getClass().toString() + "*");

			String[] cacheNames = cacheRemove.cacheNames();
			for (String cacheName : cacheNames) {
				// 缓存的项目所有redis业务部缓存
				cleanRedisCache("*" + cacheName + "*");
			}
			// 需要移除的正则key
			String[] keys = cacheRemove.key();
			for (String key : keys) {
				// 指定清除的key的缓存
				cleanRedisCache("*" + key + "*");
			}
		}
	}

	private void cleanRedisCache(String key) {
		if (key != null) {
			Set<String> stringSet = redisService.keys(key);
			redisService.delBatch(stringSet);// 删除缓存
			log.info("清除 {} 缓存", key);
		}
	}

}
