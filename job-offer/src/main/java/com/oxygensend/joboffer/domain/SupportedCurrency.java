package com.oxygensend.joboffer.domain;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum SupportedCurrency {
    PLN("985"),
    EUR("978"),
    USD("840");

    private final String numericCode;

    SupportedCurrency(String numericCode) {
        this.numericCode = numericCode;
    }

    public static Optional<SupportedCurrency> fromNumericCode(String numericCode) {
        return Arrays.stream(values()).filter(supportedCurrency -> Objects.equals(supportedCurrency.numericCode, numericCode)).findFirst();
    }

    public String numericCode() {
        return numericCode;
    }
}
