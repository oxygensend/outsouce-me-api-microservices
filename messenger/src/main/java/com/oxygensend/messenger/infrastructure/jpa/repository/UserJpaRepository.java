package com.oxygensend.messenger.infrastructure.jpa.repository;

import com.oxygensend.messenger.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserJpaRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
}
