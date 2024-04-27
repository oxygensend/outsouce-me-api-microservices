package com.oxygensend.joboffer.infrastructure.jpa;

import com.oxygensend.joboffer.domain.WorkType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Converter
public class WorkTypesConverter implements AttributeConverter<Set<WorkType>, String> {
    private final String DELIMITER = ";";

    @Override
    public String convertToDatabaseColumn(Set<WorkType> attribute) {
        return Stream.ofNullable(attribute)
                     .flatMap(Set::stream)
                     .map(WorkType::displayName)
                     .reduce((a, b) -> a + DELIMITER + b)
                     .orElse(null);

    }

    @Override
    public Set<WorkType> convertToEntityAttribute(String dbData) {
        return Arrays.stream(Optional.ofNullable(dbData)
                                     .map(data -> data.split(DELIMITER))
                                     .orElse(new String[0]))
                     .map(WorkType::valueOfDisplayName)
                     .collect(Collectors.toSet());

    }
}
