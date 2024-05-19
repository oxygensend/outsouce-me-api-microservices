package com.oxygensend.joboffer.domain;

public record JobOfferSearchResult(String id,
                                   String name,
                                   String slug,
                                   String description,
                                   String shortDescription,
                                   int numberOfApplications) {

    public JobOfferSearchResult(String id, String name, String slug, String description, int numberOfApplications) {
        this(id, name, slug, description, shortDescription(description), numberOfApplications);
    }


    private static String shortDescription(String description) {
        if (description == null) {
            return null;
        }
        var endIndex = Math.min(description.length(), 100);
        return description.substring(0, endIndex);
    }
}
