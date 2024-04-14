package com.oxygensened.userprofile.domain;

import com.oxygensened.userprofile.context.profile.UserFilters;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByExternalId(String externalId);

    Page<User> findAll(Pageable pageable, UserFilters filters);

    long findTheNewestSlugVersion(String slug);

}
