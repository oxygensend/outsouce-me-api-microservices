package com.oxygensend.joboffer.domain;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum AccountType {
    DEVELOPER(0), PRINCIPLE(1);
    private final int numericMapping;

    AccountType(int numericMapping) {
        this.numericMapping = numericMapping;
    }

    public static Optional<AccountType> fromNumericMapping(Integer numericMapping) {
        return Arrays.stream(values()).filter(type -> Objects.equals(type.numericMapping, numericMapping)).findFirst();
    }

    public int numericMapping() {
        return numericMapping;
    }
}
