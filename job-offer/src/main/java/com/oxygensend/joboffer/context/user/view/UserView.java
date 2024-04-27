package com.oxygensend.joboffer.context.user.view;


public class UserView extends BaseUserView {
    public final String activeJobPosition;

    public UserView(String id, String fullName, String thumbnailPath, String activeJobPosition) {
        super(id, fullName, thumbnailPath);
        this.activeJobPosition = activeJobPosition;
    }
}
