package com.oxygensened.userprofile.infrastructure.jpa;

import com.oxygensened.userprofile.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
