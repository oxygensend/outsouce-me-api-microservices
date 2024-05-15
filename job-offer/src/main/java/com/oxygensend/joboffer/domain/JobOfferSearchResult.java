package com.oxygensend.joboffer.domain;

public record JobOfferSearchResult(Long id,
                                   String name,
                                   String slug,
                                   String description,
                                   String shortDescription,
                                   int numberOfApplications) {

    public JobOfferSearchResult(Long id, String name, String slug, String description, int numberOfApplications) {
        this(id, name, slug, description, shortDescription(description), numberOfApplications);
    }


    private static String shortDescription(String description) {
        var endIndex = Math.min(description.length(), 100);
        return description.substring(0, endIndex);
    }
}
