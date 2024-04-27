package com.oxygensend.joboffer.domain.repository;

import com.oxygensend.joboffer.domain.entity.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(String id);

    Optional<User> findByEmail(String email);

    User save(User user);
}
