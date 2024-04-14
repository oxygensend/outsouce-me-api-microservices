package com.oxygensened.userprofile.infrastructure.jpa;

import com.oxygensened.userprofile.context.profile.UserFilters;
import com.oxygensened.userprofile.domain.User;
import com.oxygensened.userprofile.domain.UserRepository;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
class UserJpaFacadeRepository implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final EntityManager entityManager;

    UserJpaFacadeRepository(UserJpaRepository userJpaRepository, EntityManager entityManager) {
        this.userJpaRepository = userJpaRepository;
        this.entityManager = entityManager;
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

    @Override
    public Optional<User> findByExternalId(String externalId) {
        return userJpaRepository.findByExternalId(externalId);
    }

    @Override
    public Page<User> findAll(Pageable pageable, UserFilters filters) {
        var specification = UserSpecification.read(filters);
        return JpaUtils.findPageable(entityManager, pageable, User.class, specification);
    }

    @Override
    public long findTheNewestSlugVersion(String slug) {
        return userJpaRepository.findTheNewestSlugVersion(slug);
    }


}
