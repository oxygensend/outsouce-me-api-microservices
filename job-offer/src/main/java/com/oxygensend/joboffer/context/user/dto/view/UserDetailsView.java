package com.oxygensend.joboffer.context.user.dto.view;

import com.oxygensend.joboffer.domain.entity.User;

public class UserDetailsView extends UserView {
    public final String email;

    public UserDetailsView(String id, String fullName, String thumbnailPath, String activeJobPosition, String email) {
        super(id, fullName, thumbnailPath, activeJobPosition);
        this.email = email;
    }

    public static UserDetailsView from(User user) {
        return new UserDetailsView(
                user.id(),
                user.fullName(),
                user.email(),
                user.thumbnail(),
                user.activeJobPosition()
        );

    }
}