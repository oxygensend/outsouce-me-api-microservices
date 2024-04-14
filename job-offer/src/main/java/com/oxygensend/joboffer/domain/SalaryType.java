package com.oxygensend.joboffer.domain;

public enum SalaryType {
    BRUTTO("Brutto"), NETTO("netto");

    private final String displayName;

    SalaryType(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}
