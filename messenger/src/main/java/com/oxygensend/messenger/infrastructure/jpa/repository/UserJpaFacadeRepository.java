package com.oxygensend.messenger.infrastructure.jpa.repository;

import com.oxygensend.messenger.domain.User;
import com.oxygensend.messenger.domain.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
class UserJpaFacadeRepository implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    UserJpaFacadeRepository(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<User> findById(String id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }
}
