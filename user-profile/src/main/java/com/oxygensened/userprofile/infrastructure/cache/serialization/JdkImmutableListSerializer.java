package com.oxygensened.userprofile.infrastructure.cache.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.util.List;

/**
 * Dummy immutable list serializer based on kryo, implemented due to unmodifiable list serialization issue in JDK 21
 * https://github.com/magro/kryo-serializers/issues/138
 */
final class JdkImmutableListSerializer extends Serializer<List<Object>> {

    JdkImmutableListSerializer() {
        super(false, true);
    }


    @Override
    public void write(Kryo kryo, Output output, List<Object> object) {
        output.writeInt(object.size(), true);
        for (Object elm : object) {
            kryo.writeClassAndObject(output, elm);
        }
    }

    @Override
    public List<Object> read(Kryo kryo, Input input, Class<? extends List<Object>> type) {
        final int size = input.readInt(true);
        final Object[] list = new Object[size];
        for (int i = 0; i < size; ++i) {
            list[i] = kryo.readClassAndObject(input);
        }
        return List.of(list);
    }
}

