package com.oxygensened.userprofile.infrastructure.jpa;

import com.oxygensened.userprofile.domain.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByExternalId(String externalId);

    Page<User> findAll(Specification<User> specification, Pageable pageable);

    @Query("SELECT coalesce( max(cast( substring_index(u.slug, '-', -1) as long)),-1) FROM User u WHERE u.slug like :slug%")
    long findTheNewestSlugVersion(@Param("slug") String slug);
}
