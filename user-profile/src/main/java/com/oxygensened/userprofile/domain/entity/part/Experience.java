package com.oxygensened.userprofile.domain.entity.part;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum Experience {
    SENIOR(5, "Senior"),
    JUNIOR(4, "Junior"),
    MID(3, "Mid"),
    EXPERT(2, "Expert"),
    TRAINEE(1, "Stażysta");

    private final Integer numericMapping;
    private final String polishName;

    Experience(Integer numericMapping, String polishName) {
        this.numericMapping = numericMapping;
        this.polishName = polishName;
    }

    public static Optional<Experience> fromNumericMapping(Integer numericMapping) {
        return Arrays.stream(values()).filter(experience -> Objects.equals(experience.numericMapping, numericMapping)).findFirst();
    }

    public Integer numericMapping() {
        return numericMapping;
    }

    public String polishName() {
        return polishName;
    }

}
