package com.oxygensend.messenger.application.user.dto.view;

import com.oxygensend.messenger.domain.User;

public record UserView(String id, String name, String surname, String email) {

    public static UserView from(User user){
        return new UserView(user.id(), user.name(), user.surname(), user.email());
    }
}
