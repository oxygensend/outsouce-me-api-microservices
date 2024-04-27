package com.oxygensened.userprofile.infrastructure.jpa;

import com.oxygensened.userprofile.domain.AccountType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.persistence.PersistenceException;

@Converter
public class AccountTypeConverter implements AttributeConverter<AccountType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AccountType attribute) {
        return attribute.numericMapping();
    }

    @Override
    public AccountType convertToEntityAttribute(Integer dbData) {
        return AccountType.fromNumericMapping(dbData)
                          .orElseThrow(() -> new PersistenceException("Cannot find numericMapping of %s".formatted(dbData)));
    }
}
