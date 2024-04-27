package com.oxygensend.joboffer.infrastructure.jpa;

import com.oxygensend.joboffer.domain.SupportedCurrency;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SupportedCurrencyConverter implements AttributeConverter<SupportedCurrency, String> {
    @Override
    public String convertToDatabaseColumn(SupportedCurrency attribute) {
        return attribute.numericCode();
    }

    @Override
    public SupportedCurrency convertToEntityAttribute(String dbData) {
        return SupportedCurrency.fromNumericCode(dbData)
                                .orElseThrow(() -> new PersistenceException("Cannot find value %s in SupportedCurrency".formatted(dbData)));
    }
}
