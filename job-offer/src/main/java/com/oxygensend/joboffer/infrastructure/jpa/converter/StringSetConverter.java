package com.oxygensend.joboffer.infrastructure.jpa.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Set;

@Converter
public class StringSetConverter implements AttributeConverter<Set<String>, String> {
    private final String DELIMITER = ";";

    @Override
    public String convertToDatabaseColumn(Set<String> strings) {
        return String.join(DELIMITER, strings);
    }

    @Override
    public Set<String> convertToEntityAttribute(String s) {
        return Set.of(s.split(DELIMITER));
    }
}
