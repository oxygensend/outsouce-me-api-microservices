package com.oxygensend.joboffer.infrastructure.jpa.converter;

import com.oxygensend.joboffer.domain.entity.part.Experience;
import com.oxygensend.joboffer.infrastructure.jpa.PersistenceException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ExperienceConverter implements AttributeConverter<Experience, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Experience attribute) {
        return attribute != null ? attribute.numericMapping() : null;
    }

    @Override
    public Experience convertToEntityAttribute(Integer dbData) {

        return dbData == null ? null : Experience.fromNumericMapping(dbData)
                                                 .orElseThrow(() -> new PersistenceException("Cannot find numericMapping of %s".formatted(dbData)));
    }
}
