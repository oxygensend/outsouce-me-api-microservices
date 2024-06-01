package com.oxygensened.userprofile.infrastructure.cache.serialization;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class RedisKryoSerializer<T> implements RedisSerializer<T> {
    @Override
    public byte[] serialize(T value) throws SerializationException {
        return KryoSerializer.serialize(value);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        return KryoSerializer.deserialize(bytes);
    }
}
