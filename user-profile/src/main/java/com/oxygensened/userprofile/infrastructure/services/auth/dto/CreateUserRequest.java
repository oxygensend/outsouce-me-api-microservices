package com.oxygensened.userprofile.infrastructure.services.auth.dto;

import com.oxygensened.userprofile.domain.entity.User;
import java.util.Set;

public record CreateUserRequest(String id,
                                String identity,
                                Set<String> roles,
                                boolean verified,
                                String businessId,
                                String password) {
    public CreateUserRequest(String id, String identity, String role, String businessId, String password) {
        this(id, identity, Set.of(role), true, businessId, password);
    }

    public static CreateUserRequest fromUser(User user, String password) {
        return new CreateUserRequest(user.externalId(), user.email(), user.accountType().role(), user.id().toString(), password);
    }
}
