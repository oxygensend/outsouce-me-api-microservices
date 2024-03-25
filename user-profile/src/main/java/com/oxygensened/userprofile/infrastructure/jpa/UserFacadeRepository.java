package com.oxygensened.userprofile.infrastructure.jpa;

import com.oxygensened.userprofile.domain.User;
import com.oxygensened.userprofile.domain.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
class UserFacadeRepository implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    UserFacadeRepository(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }
}
