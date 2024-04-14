package com.oxygensend.joboffer.context.user;

import com.oxygensend.joboffer.domain.entity.User;

public record UserView(String id,
                       String name,
                       String surname,
                       String email,
                       String thumbnail,
                       String activeJobPositon) {

    public static UserView from(User user) {
        return new UserView(
                user.id(),
                user.name(),
                user.surname(),
                user.email(),
                user.thumbnail(),
                user.activeJobPosition()
        );
    }
}
