package com.ijinshan.sjk.util;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public enum IntegerSerializer implements RedisSerializer<Integer> {
    INSTANCE;

    @Override
    public byte[] serialize(Integer val) throws SerializationException {
        if (null != val) {
            return val.toString().getBytes();
        } else {
            return new byte[0];
        }
    }

    @Override
    public Integer deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        }
        if (bytes.length > 0) {
            return Integer.parseInt(new String(bytes));
        } else {
            return null;
        }
    }

}
