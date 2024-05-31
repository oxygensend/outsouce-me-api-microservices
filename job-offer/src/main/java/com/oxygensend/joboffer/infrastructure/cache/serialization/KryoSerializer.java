package com.oxygensend.joboffer.infrastructure.cache.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import org.objenesis.strategy.StdInstantiatorStrategy;

final class KryoSerializer<T> {

    private static final Kryo kryo;

    static {
        kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        ((DefaultInstantiatorStrategy) kryo.getInstantiatorStrategy()).setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());

        /*
         * Apparently reflections based serializer from kryo-serializers library doesn't work with JDK21
         * https://github.com/magro/kryo-serializers/issues/138 that is why unmodifiable list has to be transformed into mutable object
         */
        kryo.register(Collections.unmodifiableList(new ArrayList<>()).getClass(), new JdkImmutableListSerializer());
    }

    public static byte[] serialize(Object object) {
        try (var stream = new ByteArrayOutputStream()) {
            var output = new Output(stream);
            kryo.writeClassAndObject(output, object);
            output.close();
            return stream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T deserialize(byte[] bytes) {
        try (var input = new Input(bytes)) {
            return (T) kryo.readClassAndObject(input);
        }
    }
}
