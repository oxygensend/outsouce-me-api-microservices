package com.oxygensend.joboffer.domain.entity.part;

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

    public static WorkType valueOfDisplayName(String displayName) {
        for (WorkType workType : values()) {
            if (workType.displayName.equals(displayName)) {
                return workType;
            }
        }
        throw new IllegalArgumentException("No work type with display name: " + displayName);
    }

    public String displayName() {
        return displayName;
    }
}
