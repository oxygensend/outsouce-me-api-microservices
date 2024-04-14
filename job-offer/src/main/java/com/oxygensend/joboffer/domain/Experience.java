package com.oxygensend.joboffer.domain;

public enum Experience {
    SENIOR("Senior"),
    JUNIOR("Junior"),
    MID("Mid"),
    EXPERT("Expert"),
    TRAINEE("Stażysta");

    public final String polishName;

    Experience(String polishName) {
        this.polishName = polishName;
    }
}
