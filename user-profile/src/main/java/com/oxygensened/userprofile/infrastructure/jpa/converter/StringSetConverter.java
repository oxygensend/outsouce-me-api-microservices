package com.oxygensened.userprofile.infrastructure.jpa.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.logging.log4j.util.Strings;

@Converter
public class StringSetConverter implements AttributeConverter<Set<String>, String> {
    private final String DELIMITER = ";";

    @Override
    public String convertToDatabaseColumn(Set<String> strings) {
        return String.join(DELIMITER, strings);
    }

    @Override
    public Set<String> convertToEntityAttribute(String s) {
        Set<String> set = new HashSet<>();
        if (s == null) {
            return set;
        }
        Arrays.stream(s.split(DELIMITER))
              .filter(Strings::isNotEmpty)
              .forEach(set::add);

        return set;
    }
}
