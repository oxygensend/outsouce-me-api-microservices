package com.oxygensened.userprofile.context.profile.dto.view;

public record DeveloperView(String id,
                            String fullName,
                            String thumbnailPath,
                            String activeJobPosition,
                            String shortDescription,
                            String description) {

    public DeveloperView(String id, String fullName, String thumbnailPath, String activeJobPosition, String description) {
        this(id, fullName, thumbnailPath, activeJobPosition, shortDescription(description), description);
    }

    private static String shortDescription(String description) {
        if (description == null) {
            return null;
        }
        var endIndex = Math.min(description.length(), 100);
        return description.substring(0, endIndex);
    }
}
