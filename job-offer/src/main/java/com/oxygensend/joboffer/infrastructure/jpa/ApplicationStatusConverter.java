package com.oxygensend.joboffer.infrastructure.jpa;

import com.oxygensend.joboffer.domain.ApplicationStatus;
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
