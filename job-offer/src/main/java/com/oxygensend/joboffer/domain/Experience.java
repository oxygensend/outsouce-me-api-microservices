package com.oxygensend.joboffer.domain;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum Experience {
    SENIOR(5, "Senior"),
    JUNIOR(4, "Junior"),
    MID(3, "Mid"),
    EXPERT(2, "Expert"),
    TRAINEE(1, "Stażysta");

    public final Integer numericMapping;
    public final String polishName;

    Experience(Integer numericMapping, String polishName) {
        this.numericMapping = numericMapping;
        this.polishName = polishName;
    }

    public static Optional<Experience> fromNumericMapping(Integer numericMapping) {
        return Arrays.stream(values()).filter(experience -> Objects.equals(experience.numericMapping, numericMapping)).findFirst();
    }

    public Integer dbValue() {
        return numericMapping;
    }
}
