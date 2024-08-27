package com.oxygensened.userprofile.domain.repository;

import com.oxygensened.userprofile.domain.UserSearchResult;
import com.oxygensened.userprofile.domain.entity.User;
import com.oxygensened.userprofile.domain.repository.filters.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void deleteById(Long userId);

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByExternalId(String externalId);

    Page<User> findAll(Pageable pageable, UserFilter filters);

    long findTheNewestSlugVersion(String slug);

    Optional<String> getThumbnail(Long userId);

    Page<UserSearchResult> search(String query, Pageable pageable);

    List<User> findAll();

    List<User> findAllDevelopers();

    void saveAll(List<User> users);

    long count();

    void addRedirect(Long id);
}
