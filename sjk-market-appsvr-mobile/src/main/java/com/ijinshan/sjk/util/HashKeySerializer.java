package com.ijinshan.sjk.util;

import java.nio.charset.Charset;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public enum HashKeySerializer implements RedisSerializer<String> {
    INSTANCE;
    private final Charset charset = Charset.forName("UTF8");

    @Override
    public byte[] serialize(String string) throws SerializationException {
        return (string == null ? null : string.getBytes(charset));
    }

    @Override
    public String deserialize(byte[] bytes) throws SerializationException {
        return (bytes == null ? null : DigestUtils.shaHex(new String(bytes, charset)));
    }

}
