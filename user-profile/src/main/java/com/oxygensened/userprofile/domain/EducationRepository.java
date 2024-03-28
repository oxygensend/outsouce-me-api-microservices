package com.oxygensened.userprofile.domain;

import java.util.List;
import java.util.Optional;

public interface EducationRepository {
    Optional<Education> findByIdAndUserId(Long id, Long userId);

    List<Education> findAllByUserId(Long userId);

    Education save(Education education);

    void delete(Education education);

}
