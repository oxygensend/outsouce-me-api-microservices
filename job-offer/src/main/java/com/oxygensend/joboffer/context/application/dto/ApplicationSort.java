package com.oxygensend.joboffer.context.application.dto;

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
