package com.oxygensened.userprofile.infrastructure.jpa.converter;

import com.oxygensened.userprofile.domain.entity.part.AccountType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.persistence.PersistenceException;

import java.util.Optional;

@Converter
public class AccountTypeConverter implements AttributeConverter<AccountType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AccountType attribute) {
        return Optional.ofNullable(attribute).map(AccountType::numericMapping).orElse(null);
    }

    @Override
    public AccountType convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : AccountType.fromNumericMapping(dbData)
                          .orElseThrow(() -> new PersistenceException("Cannot find numericMapping of %s".formatted(dbData)));
    }
}
