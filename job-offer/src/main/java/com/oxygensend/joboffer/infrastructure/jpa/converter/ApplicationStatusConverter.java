package com.oxygensend.joboffer.infrastructure.jpa.converter;

import com.oxygensend.joboffer.domain.entity.part.ApplicationStatus;
import com.oxygensend.joboffer.infrastructure.jpa.PersistenceException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ApplicationStatusConverter implements AttributeConverter<ApplicationStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ApplicationStatus attribute) {
        return attribute.numericMapping();
    }

    @Override
    public ApplicationStatus convertToEntityAttribute(Integer dbData) {
        return ApplicationStatus.fromNumericMapping(dbData)
                                .orElseThrow(() -> new PersistenceException("Cannot find numericMapping of %s".formatted(dbData)));
    }
}
