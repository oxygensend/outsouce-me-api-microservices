package com.oxygensend.joboffer.infrastructure.jpa.repository;

import com.oxygensend.joboffer.domain.entity.User;
import com.oxygensend.joboffer.domain.repository.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
final class UserJpaFacadeRepository implements UserRepository {
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
