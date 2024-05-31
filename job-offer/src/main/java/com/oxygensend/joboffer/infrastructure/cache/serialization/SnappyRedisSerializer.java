package com.oxygensend.joboffer.infrastructure.cache.serialization;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.xerial.snappy.Snappy;

public class SnappyRedisSerializer<T> implements RedisSerializer<T> {
    private final RedisSerializer<T> innerSerializer;

    public SnappyRedisSerializer(RedisSerializer<T> innerSerializer) {
        this.innerSerializer = innerSerializer;
    }

    @Override
    public byte[] serialize(T value) throws SerializationException {
        try {
            byte[] serializedData = innerSerializer.serialize(value);
            assert serializedData != null;
            return Snappy.compress(serializedData);
        } catch (Exception e) {
            throw new SerializationException(e.getMessage(), e);
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        try {
            byte[] uncompressedBytes = Snappy.uncompress(bytes);
            return innerSerializer.deserialize(uncompressedBytes);
        } catch (Exception e) {
            throw new SerializationException(e.getMessage(), e);
        }
    }
}
