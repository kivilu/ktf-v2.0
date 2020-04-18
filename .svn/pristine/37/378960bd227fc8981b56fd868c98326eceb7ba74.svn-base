package com.kivi.framework.cache.redis.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.serialize.FstSerializer;
import com.kivi.framework.util.kit.StrKit;

import lombok.extern.slf4j.Slf4j;

/**
 * Redis fst Serializer 本Serializer不适用于使用DevTools自动重启开启的情况。 DevTools已知的限制：
 * 如果对象是使用ObjectInputStream进行反序列化，则自动重启将不可用。如果你需要反序列化对象，
 * 则你需要使用spring的ConfigurableObjectInputStream并配合Thread.currentThread().
 * getContextClassLoader()进行反序列化。
 * 
 * @author Eric
 *
 * @param <T>
 */
@Slf4j
public class Fst2JsonRedisSerializer<T> implements RedisSerializer<T> {
    private Class<T>            clazz;

    public Fst2JsonRedisSerializer( Class<T> clazz ) {
        super();
        this.clazz = clazz;
    }

    @Override
    public T deserialize( byte[] bytes ) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }

        if (log.isTraceEnabled())
            log.trace("bytes:{}", StrKit.str(bytes, KtfConstant.DEFAULT_CHARSET));

        T obj = FstSerializer.deserializeJson(bytes, clazz);
        return obj;
    }

    @Override
    public byte[] serialize( Object t ) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }

        byte[] bytes = FstSerializer.serializeJson(t);
        if (log.isTraceEnabled())
            log.trace("bytes:{}", StrKit.str(bytes, KtfConstant.DEFAULT_CHARSET));
        return bytes;
    }

}
