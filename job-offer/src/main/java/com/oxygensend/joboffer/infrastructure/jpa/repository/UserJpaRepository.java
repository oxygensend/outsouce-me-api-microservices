package com.oxygensend.joboffer.infrastructure.jpa.repository;

import com.oxygensend.joboffer.domain.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface UserJpaRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.accountType = 1")
    List<User> findAllPrincipals();

    @Query("select u from User u where u.accountType = 0")
    List<User> findAllDevelopers();
}
