package com.oxygensened.userprofile.infrastructure.jpa.converter;

import com.oxygensened.userprofile.domain.entity.part.Experience;
import com.oxygensened.userprofile.infrastructure.jpa.PersistenceException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Optional;

@Converter
public class ExperienceConverter implements AttributeConverter<Experience, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Experience attribute) {
        return Optional.ofNullable(attribute).map(Experience::numericMapping).orElse(null);
    }

    @Override
    public Experience convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : Experience.fromNumericMapping(dbData)
                         .orElseThrow(() -> new PersistenceException("Cannot find numericMapping of %s".formatted(dbData)));
    }
}
