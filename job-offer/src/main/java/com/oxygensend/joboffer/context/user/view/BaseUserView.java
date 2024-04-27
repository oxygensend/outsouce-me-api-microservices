package com.oxygensend.joboffer.context.user.view;

public class BaseUserView {
    public final String id;
    public final String fullName;
    public final String thumbnailPath;

    public BaseUserView(String id, String fullName, String thumbnailPath) {
        this.id = id;
        this.fullName = fullName;
        this.thumbnailPath = thumbnailPath;
    }

}
