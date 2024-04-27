package com.oxygensend.joboffer.domain.repository.filter;

public enum ApplicationSort {
    CREATED_AT("createdAt"), STATUS("status");


    private final String dbField;

    ApplicationSort(String dbField) {
        this.dbField = dbField;
    }

    public String dbField(){
        return dbField;
    }
}
