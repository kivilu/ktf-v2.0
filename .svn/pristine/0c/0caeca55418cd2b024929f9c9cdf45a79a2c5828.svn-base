package com.kivi.framework.cache.redis.serializer;

import java.nio.charset.Charset;

import org.springframework.data.redis.serializer.StringRedisSerializer;

public class KtfStringRedisSerializer extends StringRedisSerializer {

    public KtfStringRedisSerializer() {
    }

    public KtfStringRedisSerializer( Charset charset ) {
        super(charset);
    }

    @Override
    public String deserialize( byte[] bytes ) {
        // TODO Auto-generated method stub
        return super.deserialize(bytes);
    }

    @Override
    public byte[] serialize( String string ) {
        // TODO Auto-generated method stub
        return super.serialize(string);
    }

    public byte[] serialize( Long key ) {
        return super.serialize(key.toString());
    }

    public byte[] serialize( long key ) {
        return super.serialize(String.valueOf(key));
    }

}
