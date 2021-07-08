package com.kivi.framework.cache.redis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.kivi.framework.cache.properties.KtfCacheProperties;

/**
 * @Description redis工具类实现类
 */
@ConditionalOnProperty(name = {"spring.cache.type"}, havingValue = "redis", matchIfMissing = false)
@Component
public class RedisServiceImpl implements IRedisService {

    @Resource(name = "fastJsonRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private KtfCacheProperties ktfCacheProperties;

    @Override
    public boolean exists(String key) {

        return redisTemplate.hasKey(key(key));
    }

    @Override
    public void del(String key) {
        redisTemplate.delete(key(key));
    }

    @Override
    public void delBatch(Set<String> keys) {
        redisTemplate.delete(keys);
    }

    @Override
    public boolean expire(String key, int second) {
        return redisTemplate.expire(key(key), second, TimeUnit.SECONDS);
    }

    @Override
    public boolean exireAt(String key, long unixTime) {
        return redisTemplate.expireAt(key(key), new Date(unixTime));
    }

    @Override
    public long ttl(String key) {
        return redisTemplate.getExpire(key(key), TimeUnit.SECONDS);
    }

    @Override
    public long incr(String key, long num) {
        if (!this.exists(key)){
            return redisTemplate.opsForValue().increment(key(key), num);
        }else {
            if (this.get(key).equals(9223372036854775807L)){
                this.set(key,0,-1);
                return 0;
            }else {
                return redisTemplate.opsForValue().increment(key(key), num);
            }
        }
    }

    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    @Override
    public String getType(String key) {
        return redisTemplate.type(key(key)).getClass().getName();
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key(key));
    }

    @Override
    public void set(String key, Object value, int seconds) {
        if (seconds == -1)
            redisTemplate.opsForValue().set(key(key), value);
        else
            redisTemplate.opsForValue().set(key(key), value, seconds, TimeUnit.SECONDS);
    }

    @Override
    public void set(String key, Object value) {
        this.set(key(key), value, ktfCacheProperties.getTtl());
    }

    @Override
    public <T> void ladd(String key, T values, int second) {
        redisTemplate.opsForList().leftPush(key(key), values);
        this.expire(key(key), second);
    }

    @Override
    public <T> void ladd(String key, T values) {
        this.ladd(key(key), values, ktfCacheProperties.getTtl());
    }

    @Override
    public <T> void ladd(String key, List<T> values, int second) {
        for (T t : values) {
            this.ladd(key(key), t, second);
        }
    }

    @Override
    public <T> void ladd(String key, List<T> values) {
        for (T t : values) {
            this.ladd(key(key), t, ktfCacheProperties.getTtl());
        }
    }

    @Override
    public void ltrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    @Override
    public Long lsize(String key) {
        return redisTemplate.opsForList().size(key(key));
    }

    @Override
    public <T> List<T> lget(String key) {
        return this.lget(key(key), 0l, -1l);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> lget(String key, Long start, Long end) {
        return (List<T>)redisTemplate.opsForList().range(key(key), start, end);
    }

    @Override
    public void sadd(String key, Object value, int second) {
        redisTemplate.opsForSet().add(key(key), value);
        this.expire(key(key), second);
    }

    @Override
    public void sadd(String key, Object value) {
        this.sadd(key(key), value, ktfCacheProperties.getTtl());
    }

    @Override
    public Set<Object> sget(String key) {
        return redisTemplate.opsForSet().members(key(key));
    }

    @Override
    public boolean sdel(String key, Object value) {
        long flag = redisTemplate.opsForSet().remove(key(key), value);
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void madd(String key, Map<String, Object> par, int second) {
        redisTemplate.opsForHash().putAll(key(key), par);
        if (second != -1){
            this.expire(key(key), second);
        }
    }

    @Override
    public void madd(String key, Map<String, Object> par) {
        this.madd(key(key), par, ktfCacheProperties.getTtl());
    }

    @Override
    public Map<String, Object> mget(String key) {
        Map<Object, Object> resultMap = redisTemplate.opsForHash().entries(key(key));
        Map<String, Object> map = new HashMap<String, Object>();
        for (Object obj : resultMap.keySet()) {
            map.put(obj.toString(), resultMap.get(obj));
        }
        return map;
    }

    @Override
    public Object mget(String key, String field) {
        Object value = redisTemplate.opsForHash().get(key(key), field);
        return value;
    }

    @Override
    public boolean mdel(String key, String field) {
        return redisTemplate.opsForHash().delete(key(key), field) == 1;
    }

    private String key(String key) {
        return StringUtils.trimToEmpty(key);
    }
}
