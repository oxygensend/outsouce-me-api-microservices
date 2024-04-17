package com.oxygensend.joboffer.infrastructure.jpa;

import com.oxygensend.joboffer.domain.Experience;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ExperienceConverter implements AttributeConverter<Experience, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Experience attribute) {
        return attribute.numericMapping;
    }

    @Override
    public Experience convertToEntityAttribute(Integer dbData) {
        return Experience.fromNumericMapping(dbData)
                         .orElseThrow(() -> new PersistenceException("Cannot find numericMapping of %s".formatted(dbData)));
    }
}
