package com.oxygensend.joboffer.infrastructure.cache.serialization;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

final class KryoSerializer<T> {

    public static byte[] serialize(Object object) {
        try (var stream = new ByteArrayOutputStream()) {
            var output = new Output(stream);
            KryoContext.get().writeClassAndObject(output, object);
            output.close();
            return stream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T deserialize(byte[] bytes) {
        try (var input = new Input(bytes)) {
            return (T) KryoContext.get().readClassAndObject(input);
        }
    }
}
