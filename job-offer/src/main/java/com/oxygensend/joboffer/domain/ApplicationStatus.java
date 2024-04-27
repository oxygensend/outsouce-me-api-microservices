package com.oxygensend.joboffer.domain;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum ApplicationStatus {
    PENDING(1),
    ACCEPTED(0),
    REJECTED(-1);

    private final int numericMapping;

    ApplicationStatus(int numericMapping) {
        this.numericMapping = numericMapping;
    }

    public static Optional<ApplicationStatus> fromNumericMapping(Integer numericMapping) {
        return Arrays.stream(values()).filter(experience -> Objects.equals(experience.numericMapping, numericMapping)).findFirst();
    }

    public int numericMapping() {
        return numericMapping;
    }

}
