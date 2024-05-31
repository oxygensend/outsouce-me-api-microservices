package com.oxygensened.userprofile.domain.entity.part;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum AccountType {
    DEVELOPER("ROLE_DEVELOPER", 0),
    PRINCIPLE("ROLE_PRINCIPLE", 1);
    private final String role;
    private final int numericMapping;

    AccountType(String role, int numericMapping) {
        this.role = role;
        this.numericMapping = numericMapping;
    }

    public static Optional<AccountType> fromNumericMapping(Integer numericMapping) {
        return Arrays.stream(values()).filter(type -> Objects.equals(type.numericMapping, numericMapping)).findFirst();
    }

    public String role() {
        return role;
    }

    public int numericMapping() {
        return numericMapping;
    }
}
