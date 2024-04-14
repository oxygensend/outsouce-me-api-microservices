package com.oxygensend.joboffer.domain;

public enum WorkType {
    REMOTE("remote"),
    OFFICE("office"),
    HYBRID("hybrid"),
    NEGOTIATIONS("negotations"),
    OTHER("other");

    private final String displayName;

    WorkType(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}
